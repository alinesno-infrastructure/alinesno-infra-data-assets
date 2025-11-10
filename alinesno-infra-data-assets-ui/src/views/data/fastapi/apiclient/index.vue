<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="客户端名称" prop="clientName">
        <el-input
            v-model="queryParams.clientName"
            placeholder="请输入客户端名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户端ID" prop="clientId">
        <el-input
            v-model="queryParams.clientId"
            placeholder="请输入客户端ID"
            clearable
            @keyup.enter="handleQuery"
        />
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
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ApiClientList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" :align="'center'" />
      <!-- 业务字段-->
      <el-table-column label="客户端名称" align="left" key="clientName" prop="clientName" v-if="columns[0].visible">
        <template #default="scope">
            <div>
              {{ scope.row.clientName}}
            </div>
            <div style="font-size: 14px;color: #a5a5a5;cursor: pointer;" v-copyText="scope.row.secret">
              复制密钥 <i class="fa-solid fa-copy"></i>
            </div>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="left" key="remark" prop="remark" v-if="columns[2].visible" />
      <el-table-column label="Token过期时间" align="left" key="expiryTime" prop="expiryTime"  width="170" v-if="columns[4].visible">
        <template #default="scope">
          <div style="display: flex;flex-direction: column;">
            <span>
              {{ parseTime(scope.row.expiryTime) }}
            </span>
            <span style="font-size: 13px;color: #a5a5a5;cursor: pointer;" v-copyText="scope.row.promptId">
              {{ calculateRemainingTime(scope.row.expiryTime)  }}
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="调用次数" align="center" key="useCount" prop="useCount" width="80" v-if="columns[5].visible" />
      <el-table-column label="状态" align="center" width="100" key="hasStatus" v-if="columns[7].visible">
        <template #default="scope">
          <el-switch v-model="scope.row.hasStatus" :active-value="0" :inactive-value="1"
                     @change="handleChangStatusField('hasStatus', scope.row.hasStatus, scope.row.id)" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="addTime" width="170">
        <template #default="scope">
          <span>{{ parseTime(scope.row.addTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
              type="text"
              icon="Edit"
              @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
              v-if="scope.row.parentId != 0"
              type="text"
              icon="Delete"
              @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改分组对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="databaseRef" :model="form" :rules="rules" label-width="80px" size="large">
        <el-row>
          <el-col :span="24">
            <el-form-item label="名称" prop="clientName">
              <el-input v-model="form.clientName" placeholder="请输入客户端名称" maxlength="50" style="width: 720px;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="过期时间" prop="expiryTimeStr">
              <el-select v-model="form.expiryTimeStr" placeholder="设置过期时间">
                <el-option
                  v-for="option in expiryOptions"
                  :key="option.value"
                  :label="option.label"
                  :value="option.value"
                ></el-option>
              </el-select>
            </el-form-item> 
          </el-col>
          <el-col :span="12" v-if="form.expiryTimeStr === 'custom'">
            <el-form-item label="天数" prop="customDays">
              <el-input
                v-model.number="form.customDays"
                placeholder="输入自定义天数"
                type="number"
              ></el-input>
            </el-form-item> 
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" maxlength="128" style="width: 720px;"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm" size="large">确 定</el-button>
          <el-button @click="cancel" size="large">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="ApiClient">

import {
  listApiClient,
  delApiClient,
  getApiClient,
  updateApiClient,
  addApiClient,
  changStatusField
} from "@/api/data/fastapi/ApiClient";

import {v4 as uuidv4} from 'uuid' ;


const router = useRouter();
const { proxy } = getCurrentInstance();

// 定义变量
const ApiClientList = ref([]);
const open = ref(false);

const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const names = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);

const expiryOptions = ref([
  { label: '24小时', value: '1' },
  { label: '7天', value: '7' },
  { label: '30天', value: '30' },
  { label: '自定义', value: 'custom' },
]);


// 列显隐信息
const columns = ref([
  { key: 0, label: `客户端名称`, visible: true },
  { key: 1, label: `客户端ID`, visible: true },
  { key: 2, label: `密钥`, visible: true },
  { key: 3, label: `备注`, visible: true },
  { key: 4, label: `Token过期时间`, visible: true },
  { key: 5, label: `调用次数`, visible: true },
  { key: 6, label: `状态`, visible: true },
  { key: 7, label: `添加时间`, visible: true }
]);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    clientName: undefined,
    clientId: undefined,
    secret: undefined,
    remark: undefined
  },
  rules: {
    clientName: [{ required: true, message: "名称不能为空", trigger: "blur" }],
    clientId: [{ required: true, message: "路径不能为空", trigger: "blur" }],
    secret: [{ required: true, message: "服务目录不能为空", trigger: "blur" }],
    remark: [{ required: false, message: "内容类型不能为空", trigger: "blur" }],
    expiryTime: [{ required: false, message: "私有不能为空", trigger: "blur" }]
  }
});



const { queryParams, form, rules } = toRefs(data);

/** 查询指令列表 */
function getList() {
  loading.value = true;
  listApiClient(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
    loading.value = false;
    ApiClientList.value = res.rows;
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
  handleQuery();
};

/** 删除按钮操作 */
function handleDelete(row) {
  const ApiClientIds = row.id || ids.value;
  const clientNames = row.clientName || names.value;
  proxy.$modal.confirm('是否确认删除客户端名称为"' + clientNames + '"的数据项？').then(function () {
    return delApiClient(ApiClientIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
};

/** 计算剩余天数 */
function calculateRemainingTime(expiryTimeStr) {
  // 将传递的时间字符串转换为 Date 对象
  const expiryDate = new Date(expiryTimeStr);
  const now = new Date();

  // 计算时间差（以毫秒为单位）
  const diff = expiryDate.getTime() - now.getTime();

  // 如果已经过期，返回已过期
  if (diff <= 0) {
    return "已过期";
  }

  // 计算剩余的天数、小时数、分钟数
  const diffDays = Math.floor(diff / (1000 * 60 * 60 * 24));
  const diffHours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  const diffMinutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

  // 格式化输出
  let remainingTime = "";
  if (diffDays > 0) {
    remainingTime += `${diffDays}天 `;
  }
  if (diffHours > 0 || remainingTime !== "") {
    remainingTime += `${diffHours}小时 `;
  }
  if (diffMinutes > 0 || remainingTime !== "") {
    remainingTime += `${diffMinutes}分`;
  }

  return remainingTime.trim();
}

/** 选择条数  */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  names.value = selection.map(item => item.clientName);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
};

/** 重置操作表单 */
function reset() {
  form.value = {
    id: undefined,
    clientName: undefined,
    clientId: uuidv4().replace(/-/g,""),
    secret:  uuidv4().replace(/-/g,""),
    remark: undefined,
    expiryTime: undefined,
    useCount: undefined
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
  title.value = "添加客户端";
};

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const ApiClientId = row.id || ids.value;
  getApiClient(ApiClientId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改客户端";
  });
};

/** 提交按钮 */
function submitForm() {
  proxy.$refs["databaseRef"].validate(valid => {
    if (valid) {

      if(form.value.expiryTimeStr === 'custom'){
        form.value.expiryTimeStr = form.value.customDays;
      }

      if (form.value.id != undefined) {
        updateApiClient(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addApiClient(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};



const handleChangStatusField = async (field, value, id) => {
  // 判断tags值 这样就不会进页面时调用了
  const res = await changStatusField({
    field: field,
    value: value ? 1 : 0,
    id: id
  }).catch(() => { })
  if (res && res.code == 200) {
    // 刷新表格
    getList()
  }
}

getList();
</script>
