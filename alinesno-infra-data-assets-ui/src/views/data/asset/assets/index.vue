<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--类型数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
              v-model="deptName"
              placeholder="请输入类型名称"
              clearable
              prefix-icon="Search"
              style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
              :data="deptOptions"
              :props="{ label: 'label', children: 'children' }"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="deptTreeRef"
              node-key="id"
              highlight-current
              @node-click="handleNodeClick"
          />
              <!-- default-expand-all -->
        </div>
      </el-col>

      <!--指令数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
          <el-form-item label="指令名称" prop="promptName">
            <el-input v-model="queryParams.promptName" placeholder="请输入指令名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="指令名称" prop="promptName">
            <el-input v-model="queryParams['condition[promptName|like]']" placeholder="请输入指令名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">

          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete">删除</el-button>
          </el-col>

          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

         <el-table v-loading="loading" :data="AssetDataList" @selection-change="handleSelectionChange">

          <el-table-column type="selection" width="50" :align="'center'" />
          
          <!-- 动态生成表头 -->
          <el-table-column v-for="field in fields" 
            :key="field.fieldName" 
            :label="field.fieldComment" 
            :prop="field.fieldName"
            :formatter="formatDate " 
            :align="field.fieldType === 'string' ? 'left' : 'center'" />
          
          <!-- 操作字段  -->
          <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
            <template #default="scope">
              <el-tooltip content="修改" placement="top">
                <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"></el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top">
                <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"></el-button>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

      </el-col>
    </el-row>

    <!-- 添加或修改指令配置对话框 -->
    <el-dialog :title="title" v-model="open" width="900px" append-to-body>
      <el-form :model="form" :rules="rules" ref="databaseRef" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item style="width: 100%;" label="类型" prop="promptType">
              <el-tree-select
                  v-model="form.promptType"
                  :data="deptOptions"
                  :props="{ value: 'id', label: 'label', children: 'children' }"
                  value-key="id"
                  placeholder="请选择归属类型"
                  check-strictly
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="名称" prop="promptName">
              <el-input v-model="form.promptName" placeholder="请输入指令名称" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="数据来源" prop="dataSourceApi">
              <el-input v-model="form.dataSourceApi" placeholder="请输入dataSourceApi数据来源" maxlength="128" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="promptDesc">
              <el-input v-model="form.promptDesc" placeholder="请输入指令备注"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup name="AssetData">

import {
  listAssetData,
  delAssetData,
  getAssetData,
  updateAssetData,
  catalogTreeSelect,
  catalogManifestTreeSelect,
  addAssetData
} from "@/api/data/asset/dataAsset";

// import AssetDataEditor from "./editor.vue"

const router = useRouter();
const { proxy } = getCurrentInstance();

// 定义变量
const AssetDataList = ref([]);
const open = ref(false);

const promptTitle = ref("");
const currentPostId = ref("");
const currentAssetDataContent = ref([]);
const promptOpen = ref(false);

const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);
const deptOptions = ref(undefined);
const postOptions = ref([]);
const roleOptions = ref([]);
const fields = ref([]);
const formattedFields  = ref([]);

// 列显隐信息
const columns = ref([
  { key: 0, label: `指令名称`, visible: true },
  { key: 1, label: `指令描述`, visible: true },
  { key: 2, label: `表数据量`, visible: true },
  { key: 3, label: `类型`, visible: true },
  { key: 4, label: `指令地址`, visible: true },
  { key: 5, label: `状态`, visible: true },
  { key: 6, label: `更新时间`, visible: true }
]);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    promptName: undefined,
    promptDesc: undefined,
    manifestId: undefined
  },
  rules: {
    promptName: [{ required: true, message: "名称不能为空", trigger: "blur" }] ,
    dataSourceApi: [{ required: true, message: "连接不能为空", trigger: "blur" }],
    promptType: [{ required: true, message: "类型不能为空", trigger: "blur" }] ,
    promptDesc: [{ required: true, message: "备注不能为空", trigger: "blur" }]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询指令列表 */
function getList() {
  loading.value = true;
  listAssetData(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
    loading.value = false;
    AssetDataList.value = res.rows;
    total.value = res.total;

    fields.value = res.fields; // 动态设置表头
  }).finally(() => {
    loading.value = false;
  });
};

// 格式化日期的函数
const formatDate = (row, column,cellValue) => {
  console.log('row = ' + JSON.stringify(row) + 'column = ' + JSON.stringify(column)) 
  if(cellValue){ 
    return cellValue 
  }else if(cellValue === 0){ 
    return 0
  }else{
    return '--' 
  }
}

// 节点单击事件
function handleNodeClick(data) {
  queryParams.value.manifestId = data.id;
  console.log('data.id = ' + data.id)
  getList();
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
};

/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");

  queryParams.value.manifestId = undefined;

  proxy.$refs.deptTreeRef.setCurrentKey(null);
  handleQuery();
};
/** 删除按钮操作 */
function handleDelete(row) {
  const AssetDataIds = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除指令编号为"' + AssetDataIds + '"的数据项？').then(function () {
    return delAssetData(AssetDataIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
};

/** 选择条数  */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
};

/** 查询类型下拉树结构 */
function getDeptTree() {
  catalogManifestTreeSelect().then(response => {
    deptOptions.value = response.data;
  });
};

/** 配置AssetData */
function configAssetData(row){
  promptTitle.value = "配置角色AssetData";
  promptOpen.value = true ;
  currentPostId.value = row.id;

  if(row.promptContent){
    currentAssetDataContent.value = JSON.parse(row.promptContent);
  }
}

/** 重置操作表单 */
function reset() {
  form.value = {
    id: undefined,
    deptId: undefined,
    AssetDataName: undefined,
    nickName: undefined,
    password: undefined,
    phonenumber: undefined,
    status: "0",
    remark: undefined,
  };
  proxy.resetForm("databaseRef");
};
/** 取消按钮 */
function cancel() {
  open.value = false;
  promptOpen.value = false ;
  reset();
};

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加指令";
};

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const AssetDataId = row.id || ids.value;
  getAssetData(AssetDataId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改指令";
  });
};

/** 提交按钮 */
function submitForm() {
  proxy.$refs["databaseRef"].validate(valid => {
    if (valid) {
      if (form.value.id != undefined) {
        updateAssetData(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAssetData(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};

getDeptTree();
getList();

</script>
