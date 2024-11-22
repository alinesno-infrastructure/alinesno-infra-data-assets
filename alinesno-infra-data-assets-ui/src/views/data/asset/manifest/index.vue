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

      <!--清单数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
          <el-form-item label="清单名称" prop="tableName">
            <el-input v-model="queryParams.tableName" placeholder="请输入清单名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="清单名称" prop="tableName">
            <el-input v-model="queryParams['condition[tableName|like]']" placeholder="请输入清单名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
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

        <el-table v-loading="loading" :data="ManifestList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" :align="'center'" />

          <el-table-column label="图标" :align="'center'" width="70" key="status" v-if="columns[5].visible">
            <template #default="scope">
              <div class="role-icon">
                <img src="http://data.linesno.com/icons/entity-icon/table.png" />
              </div>
            </template>
          </el-table-column>

          <!-- 业务字段-->
          <el-table-column label="清单名称" align="left" key="tableName" prop="tableName" v-if="columns[0].visible" :show-overflow-tooltip="true" >
            <template #default="scope">
              <div>
                {{ scope.row.tableName }}
              </div>
              <div style="font-size: 13px;color: #a5a5a5;cursor: pointer;" v-copyText="scope.row.promptId">
                {{ scope.row.description }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="主题域" align="center" key="dataDomain" prop="dataDomain" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="项目" align="center" key="project" prop="project" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="涉密等级" align="center" key="confidentialityLevel" width="80" prop="confidentialityLevel" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="标签" align="center" key="assetTag" prop="assetTag" v-if="columns[3].visible" :show-overflow-tooltip="true" />

          <el-table-column label="字段" align="center" key="promptContent" width="120" prop="promptContent" v-if="columns[2].visible" :show-overflow-tooltip="true">
            <template #default="scope">
              <el-button type="primary" text bg icon="Paperclip" @click="configManifestField(scope.row)">配置</el-button>
            </template>
          </el-table-column>
          <el-table-column label="添加时间" align="center" prop="addTime" v-if="columns[6].visible" width="160">
            <template #default="scope">
              <span>{{ parseTime(scope.row.addTime) }}</span>
            </template>
          </el-table-column>

          <!-- 操作字段  -->
          <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
            <template #default="scope">
              <el-tooltip content="修改" placement="top" v-if="scope.row.ManifestId !== 1">
                <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                           v-hasPermi="['system:Manifest:edit']"></el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top" v-if="scope.row.ManifestId !== 1">
                <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                           v-hasPermi="['system:Manifest:remove']"></el-button>
              </el-tooltip>
            </template>

          </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
      </el-col>
    </el-row>

    <!-- 添加或修改清单配置对话框 -->
    <!-- <el-dialog :title="promptTitle" v-model="promptOpen" width="1024" destroy-on-close append-to-body>
      <ManifestEditor :currentPostId="currentPostId" :currentManifestContent="currentManifestContent" />
    </el-dialog> -->

    <!-- 添加或修改清单配置对话框 -->
    <el-dialog :title="title" v-model="open" width="900px" append-to-body>
      <el-form :model="form" :rules="rules" ref="databaseRef" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item style="width: 100%;" label="类型" prop="dataDomain">
              <el-tree-select
                  v-model="form.dataDomain"
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
            <el-form-item label="名称" prop="tableName">
              <el-input v-model="form.tableName" placeholder="请输入清单名称" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="描述" prop="description">
              <el-input v-model="form.description" placeholder="请输入description数据来源" maxlength="128" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="涉密等级" prop="confidentialityLevel">
              <el-input v-model="form.confidentialityLevel" placeholder="请输入涉密等级"></el-input>
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

<script setup name="Manifest">

import {
  catalogTreeSelect,
} from "@/api/data/asset/dataAsset";

import {
  listManifest,
  delManifest,
  getManifest,
  updateManifest,
  addManifest
} from "@/api/data/asset/manifest";

// import ManifestEditor from "./editor.vue"

const router = useRouter();
const { proxy } = getCurrentInstance();

// 定义变量
const ManifestList = ref([]);
const open = ref(false);

const promptTitle = ref("");
const currentPostId = ref("");
const currentManifestContent = ref([]);
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

// 列显隐信息
const columns = ref([
  { key: 0, label: `清单名称`, visible: true },
  { key: 1, label: `清单描述`, visible: true },
  { key: 2, label: `表数据量`, visible: true },
  { key: 3, label: `类型`, visible: true },
  { key: 4, label: `清单地址`, visible: true },
  { key: 5, label: `状态`, visible: true },
  { key: 6, label: `更新时间`, visible: true }
]);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tableName: undefined,
    confidentialityLevel: undefined,
    catalogId: undefined
  },
  rules: {
    tableName: [{ required: true, message: "名称不能为空", trigger: "blur" }] ,
    description: [{ required: true, message: "连接不能为空", trigger: "blur" }],
    dataDomain: [{ required: true, message: "类型不能为空", trigger: "blur" }] ,
    confidentialityLevel: [{ required: true, message: "备注不能为空", trigger: "blur" }]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询清单列表 */
function getList() {
  loading.value = true;
  listManifest(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
    loading.value = false;
    ManifestList.value = res.rows;
    total.value = res.total;
  });
};

// 节点单击事件
function handleNodeClick(data) {
  queryParams.value.catalogId = data.id;
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

  queryParams.value.catalogId = undefined;

  proxy.$refs.deptTreeRef.setCurrentKey(null);
  handleQuery();
};
/** 删除按钮操作 */
function handleDelete(row) {
  const ManifestIds = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除清单编号为"' + ManifestIds + '"的数据项？').then(function () {
    return delManifest(ManifestIds);
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
  catalogTreeSelect().then(response => {
    deptOptions.value = response.data;
  });
};

/** 配置Manifest */
function configManifestField(row){
  router.push({
    path:'/asset/data/asset/manifest/editorField' ,
    query: { 'manifestId': row.id }
  })
}

/** 重置操作表单 */
function reset() {
  form.value = {
    id: undefined,
    deptId: undefined,
    ManifestName: undefined,
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
  title.value = "添加清单";
};

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const ManifestId = row.id || ids.value;
  getManifest(ManifestId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改清单";
  });
};

/** 提交按钮 */
function submitForm() {
  proxy.$refs["databaseRef"].validate(valid => {
    if (valid) {
      if (form.value.id != undefined) {
        updateManifest(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addManifest(form.value).then(response => {
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
