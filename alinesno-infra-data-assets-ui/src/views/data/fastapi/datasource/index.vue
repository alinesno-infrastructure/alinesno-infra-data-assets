<template>
   <div class="app-container">
      <el-row :gutter="20">
         <!--应用数据-->
         <el-col :span="24" :xs="24">
            <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
               <el-form-item label="描述" prop="dbName">
                  <el-input v-model="queryParams.dbName" placeholder="请输入描述" clearable style="width: 240px" @keyup.enter="handleQuery" />
               </el-form-item>
               <el-form-item label="数据库名称" prop="dbName">
                  <el-input v-model="queryParams['condition[dbName|like]']" placeholder="请输入数据库名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
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

            <el-table v-loading="loading" :data="DatasourceList" @selection-change="handleSelectionChange">
               <el-table-column type="selection" width="50" align="center" />
               <el-table-column label="描述" align="left" width="350" key="status" v-if="columns[5].visible">
                  <template #default="scope">
                     <div style="line-height:35px">
                        <img v-if="scope.row.dbType === 'MySQL'" 
                           style="width:35px;position: absolute; height:35px" 
                           src="http://data.linesno.com/icons/mysql.png" />
                        <img v-else 
                           style="width:35px;position: absolute; height:35px" 
                           src="http://data.linesno.com/icons/mysql.png" />
                        <span style="padding-left:45px">
                        {{ scope.row.dbDesc }}
                        </span>
                     </div>
                  </template>
               </el-table-column>

               <!-- 业务字段-->
               <el-table-column label="数据库" align="left"  prop="dbName" :show-overflow-tooltip="true" />
               <el-table-column label="类型" align="left" width="100" prop="dbType"/>
               <el-table-column label="状态" align="center" width="120" prop="hasStatus">
                  <template #default="scope">
                     <div>
                        <el-progress v-if="scope.row.hasStatus === 0" :text-inside="false" :stroke-width="20" status="success" :percentage="100"></el-progress>
                        <el-progress v-if="scope.row.hasStatus === 1" :text-inside="false" :stroke-width="20" status="exception" :percentage="100"></el-progress>
                     </div>
                  </template>
               </el-table-column>

               <!-- 
               <el-table-column label="应用名称" align="center" key="dbName" prop="dbName" v-if="columns[0].visible" />
               <el-table-column label="应用描述" align="center" key="dbDesc" prop="dbDesc" v-if="columns[1].visible" :show-overflow-tooltip="true" />
               <el-table-column label="表数据量" align="center" key="nickName" prop="nickName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
               <el-table-column label="类型" align="center" key="dbType" prop="dbType" v-if="columns[3].visible" :show-overflow-tooltip="true" />
               <el-table-column label="应用地址" align="center" key="jdbcUrl" prop="jdbcUrl" v-if="columns[4].visible" width="120" />
               <el-table-column label="状态" align="center" key="hasStatus" v-if="columns[5].visible" />

               <el-table-column label="添加时间" align="center" prop="addTime" v-if="columns[6].visible" width="160">
                  <template #default="scope">
                     <span>{{ parseTime(scope.row.addTime) }}</span>
                  </template>
               </el-table-column> 
               -->

               <!-- 操作字段  -->
               <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
                  <template #default="scope">
                     <el-tooltip content="修改" placement="top" v-if="scope.row.id !== 1">
                        <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                           v-hasPermi="['system:Datasource:edit']"></el-button>
                     </el-tooltip>
                     <el-tooltip content="删除" placement="top" v-if="scope.row.id !== 1">
                        <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                           v-hasPermi="['system:Datasource:remove']"></el-button>
                     </el-tooltip>
                  </template>

               </el-table-column>
            </el-table>
            <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
         </el-col>
      </el-row>

      <!-- 添加或修改应用配置对话框 -->
      <el-dialog :title="title" v-model="open" width="900px" append-to-body>
         <!-- <el-form :model="form" :rules="rules" ref="databaseRef" label-width="100px">
            <el-row>
               <el-col :span="24">
                  <el-form-item label="名称" prop="dbName">
                     <el-input v-model="form.dbName" placeholder="请输入应用名称" maxlength="50" />
                  </el-form-item>
               </el-col>
            </el-row>
            <el-row>
               <el-col :span="24">
                  <el-form-item label="连接" prop="jdbcUrl">
                     <el-input v-model="form.jdbcUrl" placeholder="请输入jdbcUrl连接地址" maxlength="128" />
                  </el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="类型" prop="dbType">
                     <el-input v-model="form.dbType" placeholder="请输入类型" maxlength="50" />
                  </el-form-item>
               </el-col>
            </el-row>
            <el-row>
               <el-col :span="24">
                  <el-form-item label="用户名" prop="dbUsername">
                     <el-input v-model="form.dbUsername" placeholder="请输入连接用户名" maxlength="30" />
                  </el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="密码" prop="dbPasswd">
                     <el-input v-model="form.dbPasswd" placeholder="请输入应用密码" type="password" maxlength="30" show-password />
                  </el-form-item>
               </el-col>
            </el-row>

            <el-row>
               <el-col :span="24">
                  <el-form-item label="备注" prop="dbDesc">
                     <el-input v-model="form.dbDesc" placeholder="请输入应用备注"></el-input>
                  </el-form-item>
               </el-col>
            </el-row>
         </el-form> -->

         <el-form ref="databaseRef" :model="form" :rules="rules" label-width="100px">

            <el-form-item label="描述" prop="dbDesc">
            <el-input v-model="form.dbDesc" placeholder="数据库描述"/>
            </el-form-item>

            <el-form-item label="类型" prop="dbType">
            <el-select v-model="form.dbType" placeholder="请选择数据库类型">
               <!-- <el-option label="MySQL" :value="form.dbType" /> -->
               <el-option label="MySQL" value="MySQL" />
               <el-option label="DaMeng" value="DaMeng" />
               <el-option label="Oracle" value="Oracle" />
            </el-select>
            </el-form-item>

            <el-form-item label="连接" prop="jdbcUrl">
            <el-input v-model="form.jdbcUrl" placeholder="jdbc:mysql://localhost:3306/dbName?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&useSSL=false&serverTimezone=GMT"/>
            </el-form-item>

            <el-form-item label="用户名" prop="dbUsername">
            <el-input v-model="form.dbUsername" auto-complete="new-password" placeholder="数据库用户名"/>
            </el-form-item>
            <el-form-item label="密码" prop="dbPasswd">
            <el-input type="password" auto-complete="new-password" v-model="form.dbPasswd" placeholder="数据库密码"/>
            </el-form-item>

            </el-form>
         <template #footer>
            <div class="dialog-footer">
               <!-- <el-button type="primary" @click="submitForm">确 定</el-button>
               <el-button @click="cancel">取 消</el-button> -->
               <el-button type="text" @click="validateDburl">请先点击验证数据库是否连通</el-button>
               <el-button type="primary" @click="submitForm" :disabled="!btnChangeEnable">确 定</el-button>
               <el-button @click="cancel">取 消</el-button>
            </div>
         </template>
      </el-dialog>

   </div>
</template>

<script setup name="Datasource">

import {
   listDatasource,
   delDatasource,
   getDatasource,
   checkDbConfig,
   updateDatasource,
   addDatasource
} from "@/api/data/fastapi/datasource";

const router = useRouter();
const { proxy } = getCurrentInstance();

// 定义变量
const DatasourceList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const dbDescs = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);
const btnChangeEnable = ref(false)
const postOptions = ref([]);
const roleOptions = ref([]);

// 列显隐信息
const columns = ref([
   { key: 0, label: `应用名称`, visible: true },
   { key: 1, label: `应用描述`, visible: true },
   { key: 2, label: `表数据量`, visible: true },
   { key: 3, label: `类型`, visible: true },
   { key: 4, label: `应用地址`, visible: true },
   { key: 5, label: `状态`, visible: true },
   { key: 6, label: `更新时间`, visible: true }
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
   listDatasource(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
      loading.value = false;
      DatasourceList.value = res.rows;
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
   const dbIds = row.id || ids.value;
   const dbDescTmp = row.id || dbDescs.value;
   proxy.$modal.confirm('是否确认删除应用编号为"' + dbDescTmp + '"的数据项？').then(function () {
      return delDatasource(dbIds);
   }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
   }).catch(() => { });
};

/** 选择条数  */
function handleSelectionChange(selection) {
   ids.value = selection.map(item => item.id);
   dbDescs.value = selection.map(item => item.dbDesc);
   single.value = selection.length != 1;
   multiple.value = !selection.length;
};

/** 重置操作表单 */
function reset() {
   form.value = {
      id: undefined,
      deptId: undefined,
      DatasourceName: undefined,
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
   title.value = "添加数据源";
};

/** 修改按钮操作 */
function handleUpdate(row) {
   reset();
   const id = row.id || ids.value;
   getDatasource(id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改数据源";
   });
};

/** 提交按钮 */
function validateDburl() {
   proxy.$refs["databaseRef"].validate(valid => {
      if (valid) {
         if(!form.dbType){
            form.value.dbType = 'MySQL' ;
         }

         checkDbConfig(form.value).then(resp=>{
            if (resp.data.accepted){
               proxy.$modal.msgSuccess("数据库校验成功");
               btnChangeEnable.value = true ;
            }else{
               proxy.$modal.msgSuccess(resp.msg);
            }
         })

      }
   });
};

/** 提交按钮 */
function submitForm() {
   proxy.$refs["databaseRef"].validate(valid => {
      if (valid) {
         if (form.value.id != undefined) {
            updateDatasource(form.value).then(response => {
               proxy.$modal.msgSuccess("修改成功");
               open.value = false;
               getList();
            });
         } else {
            addDatasource(form.value).then(response => {
               proxy.$modal.msgSuccess("新增成功");
               open.value = false;
               getList();
            });
         }
      }
   });
};

getList();

</script>
