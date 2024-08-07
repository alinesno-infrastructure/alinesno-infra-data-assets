<template>
   <div class="app-container">
      <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
         <el-form-item label="分组名称" prop="name">
            <el-input
               v-model="queryParams.name"
               placeholder="请输入分组名称"
               clearable
               @keyup.enter="handleQuery"
            />
         </el-form-item>
         <el-form-item label="状态" prop="hasStatus">
            <el-select v-model="queryParams.hasStatus" placeholder="分组状态" clearable>
               <el-option
                  v-for="item in hasStatusOptions"
                  :key="item.key"
                  :label="item.label"
                  :value="item.key"
               />
            </el-select>
         </el-form-item>
         <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
         </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
         <el-col :span="1.5">
            <el-button
               type="primary"
               plain
               icon="Plus"
               @click="handleAdd"
               v-hasPermi="['system:dept:add']"
            >新增</el-button>
         </el-col>
         <el-col :span="1.5">
            <el-button
               type="info"
               plain
               icon="Sort"
               @click="toggleExpandAll"
            >展开/折叠</el-button>
         </el-col>
         <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table
         v-if="refreshTable"
         v-loading="loading"
         :data="deptList"
         row-key="id"
         :default-expand-all="isExpandAll"
         :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">

         <el-table-column prop="name" label="分组名称">
            <template #default="scope">
               {{ scope.row.name }}
            </template>
         </el-table-column>
         <el-table-column prop="description" label="分组描述" ></el-table-column>
         <el-table-column prop="orderNum" label="排序" width="200"></el-table-column>
         <el-table-column prop="hasStatus" label="状态" width="100">
           <template  #default="scope">
             <el-switch
                 v-model="scope.row.hasStatus"
                 :active-value="0"
                 :inactive-value="1"
                 @change="handleStatusChange(scope.row)"
             ></el-switch>
           </template>
<!--            <template #default="scope">
               <dict-tag :options="sys_normal_disable" :value="scope.row.hasStatus" />
            </template>-->
         </el-table-column>
         <el-table-column label="创建时间" align="center" prop="createTime" width="200">
            <template #default="scope">
               <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
         </el-table-column>
         <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
            <template #default="scope">
               <el-button
                  type="text"
                  icon="Edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:dept:edit']"
               >修改</el-button>
               <el-button
                  type="text"
                  icon="Plus"
                  @click="handleAdd(scope.row)"
                  v-hasPermi="['system:dept:add']"
               >新增</el-button>
               <el-button
                  v-if="scope.row.parentId != 0"
                  type="text"
                  icon="Delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['system:dept:remove']"
               >删除</el-button>
            </template>
         </el-table-column>
      </el-table>

      <!-- 添加或修改分组对话框 -->
      <el-dialog :title="title" v-model="open" width="600px" append-to-body>
         <el-form ref="deptRef" :model="form" :rules="rules" label-width="80px">
            <el-row>
               <el-col :span="24" v-if="form.parentId !== 0" >
                  <el-form-item label="上级分组" prop="parentId">
                     <el-tree-select
                        v-model="form.parentId"
                        :data="deptOptions"
                        :props="{ value: 'id', label: 'name', children: 'children' }"
                        value-key="id"
                        placeholder="选择上级分组"
                        check-strictly
                        style="width:480px"
                     />
                  </el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="分组名称" prop="name">
                     <el-input v-model="form.name" placeholder="请输入分组名称" />
                  </el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="分组描述" prop="name">
                     <el-input v-model="form.description" placeholder="请输入分组描述" />
                  </el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="显示排序" prop="orderNum">
                     <el-input-number v-model="form.orderNum" controls-position="right" :min="0"  style="width:480px"/>
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

<script setup name="ApiGroup">
import {
  listApiGroup,
  getApiGroup,
  delApiGroup,
  addApiGroup,
  updateApiGroup,
  listApiGroupExcludeChild,
  changeGroupStatus
} from "@/api/data/fastapi/apiGroup";

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");

const deptList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const title = ref("");
const deptOptions = ref([]);
const isExpandAll = ref(true);
const refreshTable = ref(true);

const data = reactive({
  form: {},
  queryParams: {
    name: undefined,
    hasStatus: undefined
  },
  rules: {
    parentId: [{ required: false, message: "上级分组不能为空", trigger: "blur" }],
    name: [{ required: true, message: "分组名称不能为空", trigger: "blur" }],
    orderNum: [{ required: true, message: "显示排序不能为空", trigger: "blur" }]
  },
  hasStatusOptions: [
    {key: 0, label: "启用",cantSelect: true},
    {key: 1, label: "禁用",cantSelect: true}
  ]
});

const { queryParams, form, rules, hasStatusOptions } = toRefs(data);

/** 查询分组列表 */
function getList() {
  loading.value = true;
  listApiGroup(queryParams.value).then(response => {
    deptList.value = proxy.handleTree(response.data, "id");
    loading.value = false;
  });
}
/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}
/** 表单重置 */
function reset() {
  form.value = {
    id: undefined,
    parentId: undefined,
    name: undefined,
    orderNum: 0,
    leader: undefined,
    hasStatus: "0"
  };
  proxy.resetForm("deptRef");
}
/** 搜索按钮操作 */
function handleQuery() {
  getList();
}
/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}
/** 新增按钮操作 */
function handleAdd(row) {
  reset();
  listApiGroup().then(response => {
    deptOptions.value = proxy.handleTree(response.data, "id");
  });
  if (row != undefined) {
    form.value.parentId = row.id;
  }
  open.value = true;
  title.value = "添加分组";
}
/** 展开/折叠操作 */
function toggleExpandAll() {
  refreshTable.value = false;
  isExpandAll.value = !isExpandAll.value;
  nextTick(() => {
    refreshTable.value = true;
  });
}
/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  listApiGroupExcludeChild(row.id).then(response => {
    deptOptions.value = proxy.handleTree(response.data, "id");
  });
  getApiGroup(row.id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改分组";
  });
}
/** 提交按钮 */
function submitForm() {
  proxy.$refs["deptRef"].validate(valid => {
    if (valid) {
      if (form.value.id != undefined) {
        updateApiGroup(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addApiGroup(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}
/** 删除按钮操作 */
function handleDelete(row) {
  proxy.$modal.confirm('是否确认删除名称为"' + row.name + '"的数据项?').then(function() {
    return delApiGroup(row.id);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 状态修改 **/
function   handleStatusChange(row) {
  return changeGroupStatus(row.id, row.hasStatus).then(response=>{
    if(response.code == 200){
      proxy.$modal.msgSuccess("操作成功");
    }
  });
}

getList();
</script>
