<template>
    <div class="app-container">
        <div style="display: flex;justify-content: space-between;align-items: center;">
            <el-page-header @back="goBack" style="width:100%">
                <template #content>
                    <span class="text-large font-600 mr-3"> 模型 数据资源结构</span>
                </template>
            </el-page-header>
            <div style="display: flex;justify-content: center;align-items: center;flex-wrap: nowrap;">
                <el-button type="primary" @click="addField">新增字段</el-button>
                <el-button type="success" @click="handleUpdateManifestField">保存表结构</el-button>
            </div>
        </div>

        <div class="db-editor">
            <el-table :data="fields" style="width: 100%">
                <el-table-column prop="name" label="列名" width="280">
                    <template #default="scope">
                        <el-input v-model="scope.row.name" placeholder="请输入列名"></el-input>
                        <div v-if="errors[scope.$index] && errors[scope.$index].name" class="error">{{
                errors[scope.$index].name }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="type" label="数据类型" width="180">
                    <template #default="scope">
                        <el-select v-model="scope.row.type" placeholder="请选择数据类型">
                            <el-option label="字符串" value="string"></el-option>
                            <el-option label="整数" value="integer"></el-option>
                            <el-option label="浮点数" value="float"></el-option>
                            <el-option label="布尔值" value="boolean"></el-option>
                            <el-option label="日期" value="date"></el-option>
                        </el-select>
                        <div v-if="errors[scope.$index] && errors[scope.$index].type" class="error">{{
                errors[scope.$index].type }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="length" label="类型长度" width="180">
                    <template #default="scope">
                        <el-input v-model.number="scope.row.length" placeholder="请输入类型长度"
                            :disabled="scope.row.type !== 'string'"></el-input>
                        <div v-if="errors[scope.$index] && errors[scope.$index].length" class="error">{{ errors[scope.$index].length }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="isNullable" label="是否为空" width="100">
                    <template #default="scope">
                        <el-switch v-model="scope.row.isNullable"></el-switch>
                    </template>
                </el-table-column>
                <el-table-column prop="isPrimaryKey" label="是否为主键" width="100">
                    <template #default="scope">
                        <el-switch v-model="scope.row.isPrimaryKey"></el-switch>
                        <div v-if="errors[scope.$index] && errors[scope.$index].length" class="error">{{ errors[scope.$index].length }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="comment" label="注释信息">
                    <template #default="scope">
                        <el-input v-model="scope.row.comment" placeholder="请输入注释信息"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="100">
                    <template #default="scope">
                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

    </div>
</template>

<script setup>

import {
    getManifestFieldByMId,
    updateManifestFieldByMId
} from "@/api/data/asset/manifestField";
import { ElLoading } from 'element-plus'

const router = useRouter();
const { proxy } = getCurrentInstance();

const manifestId = ref("")

const errors = ref([]);
const fields = ref([]);

const addField = () => {
    fields.value.push({ name: '', type: 'string', length: null, isNullable: false, isPrimaryKey: false, comment: '' });
    errors.value.push({});
};

const goBack = () => {
    console.log('go back')
    router.push('/asset/data/asset/manifest/index')
}

const handleDelete = (index) => {
    fields.value.splice(index, 1);
    errors.value.splice(index, 1);
};

const validateFields = () => {
    let isValid = true;
    let primaryKeyCount = 0; // 用于计数主键的数量
    const sqlKeywords = ['select', 'from', 'where', 'and', 'or', 'not', 'in', 'like', 
            'join', 'on', 'group', 'by', 'having', 'order', 'asc', 'desc', 
            'limit', 'offset', 'insert', 'into', 'values', 'update', 'set', 'delete', 
            'create', 'table', 'drop', 'alter', 'add', 'column', 'constraint', 'foreign', 
            'key', 'references', 'unique', 'index', 'default', 'auto_increment', 'primary', 
            'key', 'null', 'not', 'null', 'check', 'as', 'exists', 'distinct', 'case', 
            'when', 'then', 'else', 'end', 'union', 'all', 'intersect', 'except', 
            'with', 'as', 'recursive', 'view', 'replace', 'truncate', 'rename', 'to', 
            'cascade', 'restrict', 'cascade', 'deferred', 'immediate', 'temp', 'temporary', 'if', 
            'exists', 'transaction', 'commit', 'rollback', 'savepoint', 'release', 
            'pragma', 'attach', 'database', 'detach', 'vacuum', 'reindex', 'analyze', 'pragma', 'show', 
            'tables', 'columns', 'databases', 'functions', 'variables', 'status', 
            'processlist', 'kill', 'quit', 'exit']; // 示例SQL关键字列表

    const invalidCharsRegex = /[^\w]/; // 不允许出现的特殊字符正则表达式
    const containsNumberRegex = /\d/; // 包含数字的正则表达式
    const seenNames = new Set(); // 用于记录已经出现过的字段名称

    errors.value = fields.value.map((field, index) => {
        const fieldErrors = {};

        // 验证列名
        if (!field.name) {
            fieldErrors.name = '列名不能为空';
            isValid = false;
        } else if (sqlKeywords.includes(field.name.toLowerCase())) {
            fieldErrors.name = '列名不能是SQL关键字';
            isValid = false;
        } else if (invalidCharsRegex.test(field.name)) {
            fieldErrors.name = '列名不能包含特殊字符';
            isValid = false;
        } else if (containsNumberRegex.test(field.name)) {
            fieldErrors.name = '列名不能包含数字';
            isValid = false;
        } else if (seenNames.has(field.name)) {
            fieldErrors.name = '列名不能重复';
            isValid = false;
        } else {
            seenNames.add(field.name); // 将新的列名添加到集合中
        }

        // 验证数据类型
        if (!field.type) {
            fieldErrors.type = '数据类型不能为空';
            isValid = false;
        }

        // 验证类型长度
        if (field.type === 'string') {
            if (field.length === null || field.length <= 0) {
                fieldErrors.length = '长度必须大于0';
                isValid = false;
            }
        } 
        // else if (field.length !== null) {
        //     fieldErrors.length = '不应设置长度';
        //     isValid = false;
        // }

        // 验证主键
        if (field.isPrimaryKey) {
            primaryKeyCount++;
            if (primaryKeyCount > 1) {
                fieldErrors.isPrimaryKey = '一个表中只能有一个主键';
                isValid = false;
            }
        }

        return fieldErrors;
    });

    // 如果存在多个主键，则所有字段都标记错误
    if (primaryKeyCount > 1) {
        fields.value.forEach((_, index) => {
            if (!errors.value[index].isPrimaryKey) {
                errors.value[index].isPrimaryKey = '一个表中只能有一个主键';
            }
        });
    }

    return isValid;
};

const handleUpdateManifestField = () => {

    if (!validateFields()) {
        return;
    }

    const loading = ElLoading.service({
        lock: true,
        text: '模型更新中，勿刷新页面，请稍等...',
        background: 'rgba(0, 0, 0, 0.7)',
    })

    try {

        updateManifestFieldByMId(manifestId.value, fields.value).then(res => {
            console.log('保存成功', fields.value);
            proxy.$modal.msgSuccess("更新成功");
            loading.close()
        })
    } catch (error) {
        loading.close()
        console.error('保存失败', error);
        proxy.$modal.msgError("更新失败");
    }
};

onMounted(() => {
    manifestId.value = router.currentRoute.value.query.manifestId
    getManifestFieldByMId(manifestId.value).then(res => {
        if (res.data) {
            fields.value = res.data
        }
    })
})

</script>

<style lang="scss" scoped>
.db-editor {
    padding-top: 20px;
}

.error {
    color: red;
    font-size: 12px;
    margin-top: 4px;
    position: absolute;
    top: 10px;
    right: 20px;
}
</style>