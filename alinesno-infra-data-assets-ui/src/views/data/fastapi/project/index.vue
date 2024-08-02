<template>
   <div class="app-container">
      <el-row :gutter="20">
         <!--应用数据-->
         <el-col :span="24" :xs="24">
            <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
               <el-form-item label="应用名称" prop="dbName">
                  <el-input v-model="queryParams.dbName" placeholder="请输入应用名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
               </el-form-item>
               <el-form-item label="应用名称" prop="dbName">
                  <el-input v-model="queryParams['condition[dbName|like]']" placeholder="请输入应用名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
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

            <el-table v-loading="loading" :data="ProjectList" @selection-change="handleSelectionChange">
               <el-table-column type="selection" width="50" align="center" />
               <el-table-column label="图标" align="center" width="70" key="icon" v-if="columns[5].visible">
                  <template #default="scope">
                     <span style="font-size:25px;color:#3b5998">
                        <i class="fa-solid fa-file-word" />
                     </span>
                  </template>
               </el-table-column>

               <!-- 业务字段-->
               <el-table-column label="应用名称" align="left" key="projectName" prop="projectName" v-if="columns[0].visible" />
               <el-table-column label="应用描述" align="left" key="projectDesc" prop="projectDesc" v-if="columns[1].visible" />
               <el-table-column label="应用代码" align="center" width="200" key="projectCode" prop="projectCode" v-if="columns[2].visible" :show-overflow-tooltip="true" />

               <el-table-column label="接口授权" align="center" width="200" key="documentType" prop="documentType" v-if="columns[1].visible" :show-overflow-tooltip="true" >
                  <template #default="scope">
                     <el-button type="primary" bg text @click="handleConfigType(scope.row.id , scope.row.documentType)"> <i class="fa-solid fa-link"></i> 接口授权 </el-button>
                  </template>
               </el-table-column>

               <el-table-column label="关闭" align="center" width="100" key="hasStatus" prop="hasStatus" v-if="columns[1].visible" :show-overflow-tooltip="true" >
                  <template #default="scope">
                     <el-switch
                        v-model="scope.row.hasStatus"
                        :active-value="1"
                        :inactive-value="0"
                        @change="handleChangStatusField('hasStatus' , scope.row.hasStatus, scope.row.id)"
                     />
                  </template>
               </el-table-column>

               <el-table-column label="添加时间" align="center" prop="addTime" v-if="columns[6].visible" width="160">
                  <template #default="scope">
                     <span>{{ parseTime(scope.row.addTime) }}</span>
                  </template>
               </el-table-column>

               <!-- 操作字段  -->
               <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
                  <template #default="scope">
                     <el-tooltip content="修改" placement="top" v-if="scope.row.ProjectId !== 1">
                        <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                           v-hasPermi="['system:Project:edit']"></el-button>
                     </el-tooltip>
                     <el-tooltip content="删除" placement="top" v-if="scope.row.ProjectId !== 1">
                        <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                           v-hasPermi="['system:Project:remove']"></el-button>
                     </el-tooltip>
                  </template>

               </el-table-column>
            </el-table>
            <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
         </el-col>
      </el-row>

      <!-- 添加或修改应用配置对话框 -->
      <el-dialog :title="title" v-model="open" width="900px" append-to-body>
         <el-form :model="form" :rules="rules" ref="databaseRef" label-width="100px">
            <el-row>
               <el-col :span="24">
                  <el-form-item label="应用图标" prop="logo">
                     <!-- <el-input v-model="form.logo" placeholder="请输入应用图标" maxlength="255" /> -->

                     <el-upload action="#" list-type="picture-card" :auto-upload="false">
                           <el-icon><Plus /></el-icon>

                           <template #file="{ file }">
                              <div>
                              <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
                              <span class="el-upload-list__item-actions">
                                 <span
                                    class="el-upload-list__item-preview"
                                    @click="handlePictureCardPreview(file)"
                                 >
                                    <el-icon><zoom-in /></el-icon>
                                 </span>
                                 <span
                                    v-if="!disabled"
                                    class="el-upload-list__item-delete"
                                    @click="handleDownload(file)"
                                 >
                                    <el-icon><Download /></el-icon>
                                 </span>
                                 <span
                                    v-if="!disabled"
                                    class="el-upload-list__item-delete"
                                    @click="handleRemove(file)"
                                 >
                                    <el-icon><Delete /></el-icon>
                                 </span>
                              </span>
                              </div>
                           </template>
                        </el-upload>

                  </el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="应用名称" prop="projectName">
                     <el-input v-model="form.projectName" placeholder="请输入应用名称" maxlength="50" />
                  </el-form-item>
               </el-col>
            </el-row>
            <el-row>
               <el-col :span="24">
                  <el-form-item label="应用介绍" prop="intro">
                     <el-input v-model="form.intro" type="textarea" placeholder="请输入应用介绍" maxlength="255" />
                  </el-form-item>
               </el-col>
            </el-row>
            <el-row>
               <el-col :span="24">
                  <el-form-item label="授权地址" prop="allowUrl">
                     <el-input v-model="form.allowUrl" placeholder="请输入授权地址" maxlength="255" />
                  </el-form-item>
               </el-col>

               <el-col :span="24">
                  <el-form-item label="应用状态" prop="status">
                     <el-radio-group v-model="form.status">
                        <el-radio
                           v-for="dict in sys_normal_disable"
                           :key="dict.value"
                           :label="dict.value"
                        >{{ dict.label }}</el-radio>
                     </el-radio-group>
                  </el-form-item>
               </el-col>

               <el-col :span="24">
                  <!-- <el-form-item label="是否公开" prop="isPublic">
                     <el-input v-model="form.isPublic" placeholder="请输入是否公开" maxlength="1" />
                  </el-form-item> -->

                  <el-form-item label="是否公开" prop="isPublic">
                     <el-radio-group v-model="form.isPublic">
                        <el-radio
                           v-for="dict in sys_normal_disable"
                           :key="dict.value"
                           :label="dict.value"
                        >{{ dict.label }}</el-radio>
                     </el-radio-group>
                  </el-form-item>
               </el-col>
            </el-row>

            <el-row>
               <el-col :span="24">
                  <el-form-item label="备注" prop="description">
                     <el-input v-model="form.description"  placeholder="请输入应用备注"></el-input>
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


      <!-- 文档列表 -->
      <el-dialog :title="title" v-model="openDocumentTypeDialog" width="1024px" append-to-body>

         <TypeList />

         <template #footer>
            <div class="dialog-footer">
               <el-button type="primary" @click="submitDocumentTypeForm">确 定</el-button>
               <el-button @click="openDocumentTypeDialog = false">取 消</el-button>
            </div>
         </template>
      </el-dialog>

   </div>
</template>

<script setup name="Project">

import {
   listProject,
   delProject,
   getProject,
   updateProject,
   addProject,
   changStatusField
} from "@/api/data/fastapi/project";

import TypeList from './typeList'

const router = useRouter();
const { proxy } = getCurrentInstance();

// 定义变量
const ProjectList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);

// 是否打开配置文档
const openDocumentTypeDialog = ref(false);

// 列显隐信息
const columns = ref([
   { key: 0, label: `应用名称`, visible: true },
   { key: 1, label: `应用描述`, visible: true },
   { key: 2, label: `授权地址`, visible: true },
   { key: 3, label: `类型`, visible: true },
   { key: 4, label: `是否公开`, visible: true },
   { key: 5, label: `状态`, visible: true },
   { key: 6, label: `添加时间`, visible: true }
]);

const data = reactive({
   form: {},
   queryParams: {
      pageNum: 1,
      pageSize: 10,
      dbName: undefined,
      dbDesc: undefined
   },
   rules: {
      dbName: [{ required: true, message: "名称不能为空", trigger: "blur" }] , 
      jdbcUrl: [{ required: true, message: "连接不能为空", trigger: "blur" }],
      dbType: [{ required: true, message: "类型不能为空", trigger: "blur" }] , 
      dbUsername: [{ required: true , message: "用户名不能为空", trigger: "blur"}],
      dbPasswd: [{ required: true, message: "密码不能为空", trigger: "blur" }] , 
      dbDesc: [{ required: true, message: "备注不能为空", trigger: "blur" }] 
   }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询应用列表 */
function getList() {
   loading.value = true;
   listProject(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
      loading.value = false;
      ProjectList.value = res.rows;
      total.value = res.total;
   });
};

/** 搜索按钮操作 */
function handleQuery() {
   queryParams.value.pageNum = 1;
   getList();
};

/** 重置按钮操作 */
function resetQuery() {
   dateRange.value = [];
   proxy.resetForm("queryRef");
   queryParams.value.deptId = undefined;
   proxy.$refs.deptTreeRef.setCurrentKey(null);
   handleQuery();
};
/** 删除按钮操作 */
function handleDelete(row) {
   const ProjectIds = row.id || ids.value;
   proxy.$modal.confirm('是否确认删除应用编号为"' + ProjectIds + '"的数据项？').then(function () {
      return delProject(ProjectIds);
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

/** 重置操作表单 */
function reset() {
   form.value = {
      id: undefined,
      deptId: undefined,
      ProjectName: undefined,
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
   reset();
};

/** 新增按钮操作 */
function handleAdd() {
   reset();
   open.value = true;
   title.value = "添加应用";
};

/** 修改按钮操作 */
function handleUpdate(row) {
   reset();
   const ProjectId = row.id || ids.value;
   getProject(ProjectId).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改应用";
   });
};

/** 提交按钮 */
function submitForm() {
   proxy.$refs["databaseRef"].validate(valid => {
      if (valid) {
         if (form.value.ProjectId != undefined) {
            updateProject(form.value).then(response => {
               proxy.$modal.msgSuccess("修改成功");
               open.value = false;
               getList();
            });
         } else {
            addProject(form.value).then(response => {
               proxy.$modal.msgSuccess("新增成功");
               open.value = false;
               getList();
            });
         }
      }
   });
};

/** 配置文档类型 */
function handleConfigType(id , documentType){
   openDocumentTypeDialog.value = true ; 
}

/** 修改状态 */
const handleChangStatusField = async(field , value , id) => {
    // 判断tags值 这样就不会进页面时调用了
      const res = await changStatusField({
         field: field,
         value: value?1:0,
         id: id
      }).catch(() => { })
      if (res && res.code == 200) {
         // 刷新表格
         getList()
      }
}

/** 提交配置文档类型 */
function submitDocumentTypeForm(){
   // TODO 待保存应用文档类型
}

getList();

</script>
