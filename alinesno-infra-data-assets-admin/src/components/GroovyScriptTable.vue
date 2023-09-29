<template>
	<div>
        <el-table size="small" :data="tableData" style="width: 100%" height="600">
            <el-table-column label="组件名称" prop="name" width="250"></el-table-column>
            <el-table-column label="组件参数" prop="extendInfo"></el-table-column>
            <el-table-column label="组件代码" prop="content" width="200">
                <template slot-scope="scope">
                    <el-link icon="el-icon-view" type="primary" @click="queryCode(scope.row)" style="font-size:12px;">{{'查看组件代码...'}}</el-link>
                </template>
            </el-table-column>
            <el-table-column label="组件说明" prop="remarks"></el-table-column>
            <el-table-column label="排序" prop="orderNum" width="100"></el-table-column>
            <el-table-column label="执行事件" prop="event" width="160">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.event==='request'" size="small" effect="plain">REQUEST</el-tag>
                    <el-tag v-if="scope.row.event==='response'" size="small" type="success" effect="plain">RESPONSE</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="创建时间" prop="createTime" width="200"></el-table-column>
            <el-table-column label="状态" prop="status" width="120">
                <template slot-scope="scope">
                    <div v-if="scope.row.status==='0'"><i class="el-icon-success" style="color: #409EFF;"></i>&nbsp;<el-tag size="mini">{{'开启'}}</el-tag></div>
                    <div v-if="scope.row.status==='1'"><i class="el-icon-error" style="color: #f00000;"></i>&nbsp;<el-tag size="mini" type="danger">{{'关闭'}}</el-tag></div>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="80">
                <template slot-scope="scope">
                    <el-dropdown trigger="click" @command="handleCommandRegServer">			
                        <el-button size="mini" circle icon="el-icon-setting" title="设置" style="border: 0px;"></el-button>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item :command="{command:'edit', row: scope.row}"><i class="el-icon-edit"></i>编辑</el-dropdown-item>
                        <el-dropdown-item :command="{command:'start', row: scope.row}" divided><i class="el-icon-success" style="color: #409EFF;"></i>开启</el-dropdown-item>
                        <el-dropdown-item :command="{command:'stop', row: scope.row}"><i class="el-icon-error" style="color: red;"></i>关闭</el-dropdown-item>
                        <el-dropdown-item :command="{command:'up', row: scope.row}" divided><i class="el-icon-upload2"></i>上移</el-dropdown-item>
                        <el-dropdown-item :command="{command:'down', row: scope.row}"><i class="el-icon-download"></i>下移</el-dropdown-item>
                        <el-dropdown-item :command="{command:'delete', row: scope.row, index: scope.index}" divided><i class="el-icon-delete"></i>移除</el-dropdown-item>
                    </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
	
export default {
	data() {
		return {
            name:'groovyScriptTable',
        };
	},
    props:['tableData'],//父组件传参
    created: function() {
        this.init();
    },
	methods: {
        init() {
			console.log('tableData',this.tableData)
		},
        queryCode(row){	
            console.log(this.$parent)
            //调用父组件方法
			this.$emit('queryCode', row);
        },
        handleCommandRegServer(obj){
            this.$emit('handleCommandRegServer', obj);
        }
    }
}
</script>

<style>
</style>
