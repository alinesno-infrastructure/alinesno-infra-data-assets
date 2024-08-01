<template>
    <div>
        <el-form :model="form" :rules="rules" ref="executeRef" label-width="120px">
            <el-row>
               <el-col :span="24">
                  <el-form-item style="width: 100%;" label="数据库源" prop="datasourceId">
                     <el-tree-select
                        v-model="form.datasourceId"
                        :data="datasourceList"
                        :props="{ value: 'id', label: 'dbDesc'}"
                        value-key="id"
                        placeholder="请选择归属类型"
                        check-strictly
                     />
                  </el-form-item>
               </el-col>
            </el-row>

            <el-row>
               <el-col :span="24">
                   <el-form-item label="执行SQL" prop="runSql">
                     <el-tabs
                         v-model="editableTabsValue"
                         type="card"
                         editable
                         @edit="handleTabsEdit"
                         tab-position="top"
                     >
                   <el-tab-pane
                       v-for="(item, index) in editableTabs"
                       :key="item.index"
                       :label="item.name"
                       :name="item.name"
                   >
                     <el-input
                         type="textarea"
                         :autosize="{minRows:10,maxRows:20}"
                         v-model="item.runSql"
                         placeholder="请输入指令名称"
                         style="width: 865px"
                         maxlength="500" />
                   </el-tab-pane>
                 </el-tabs>
                 </el-form-item>
               </el-col>
            </el-row>

            <el-form-item label="事务" prop="openTran">
                  <el-switch
                  v-model="form.openTran"
                  :active-value="true"
                  :inactive-value="false"
                  />
            </el-form-item>

         </el-form>
    </div>

</template>

<script setup name="SqlEditor">

import {
   allDatasource,
} from "@/api/data/fastapi/datasource";

import {
   updateExecuteSql
} from "@/api/data/fastapi/apiConfig";

const router = useRouter();
const { proxy } = getCurrentInstance();
const datasourceList = ref([]);

const props = defineProps({
  currentApiConfigContent: {
    type: Object ,
    require: true,
  },
})

const data = reactive({
   form: {
     datasourceId:'',
     runSql:'',
     openTran:0,
   },
   queryParams: {
      pageNum: 1,
      pageSize: 10,
      datasourceId: '',
      runSql: '',
      openTran: 0
   },
   rules: {
      datasourceId: [{ required: true, message: "数据源不能为空", trigger: "blur" }],
      runSql: [{ required: true, message: "SQL不能为空", trigger: "blur" }] ,
      openTran: [{ required: true, message: "事务不能为空", trigger: "blur" }]
   }
});

const { queryParams, form, rules } = toRefs(data);

const editableTabsValue = ref('SQL-0') ;

const editableTabs = ref([
  { name: 'SQL-0',"runSql":"SELECT now()"}
])

onMounted(() => {
  reset();
  form.value.datasourceId = props.currentApiConfigContent.datasourceId ;
  form.value.runSql = props.currentApiConfigContent.runSql ;
  form.value.openTran = props.currentApiConfigContent.openTran ;
  if ( props.currentApiConfigContent.runSql != null && props.currentApiConfigContent.runSql != undefined && props.currentApiConfigContent.runSql != '' ){
     editableTabs.value = JSON.parse(props.currentApiConfigContent.runSql)
  }
})

function  reset() {
  form.value = {
    datasourceId:'',
    runSql:'',
    openTran:0,
  };
  proxy.resetForm("executeRef");
}



/** 获取到所有数据源 */
function handleAllDatasource() {
   allDatasource().then(res => {
      datasourceList.value = res.data ;
   })
}

/** 提交按钮 */
function submitForm(id) {
  let check = true ;
  for(var i = 0; i < editableTabs.value.length; i++){
    if ( editableTabs.value[i].runSql == null || editableTabs.value[i].runSql == undefined || editableTabs.value[i].runSql == '' ) {
      proxy.$modal.msgError("请填写"+ editableTabs.value[i].name + "的执行SQL!");
      check = false;
      break ;
    }

  }

  if ( check ) {
    form.value.runSql =  JSON.stringify(editableTabs.value) ;
    proxy.$refs["executeRef"].validate(valid => {
      if (valid) {
        updateExecuteSql(form.value , id).then(response => {
          return check ;
        });
      }
    });
  } else {
    return false
  }

};

function handleTabsEdit (targetName, action)  {
  if (action === 'add') {
    const newTabName = "SQL-"+`${editableTabs.value.length}`
    editableTabs.value.push({
      "name":newTabName,
      "runSql":""
    })
    editableTabsValue.value = newTabName
  } else if (action === 'remove') {
    let tabs = editableTabs.value;

    let activeName = editableTabsValue.value;
    if (activeName === targetName) {
      tabs.forEach((tab, index) => {
        if (tab.name === targetName) {
          const nextTab = tabs[index + 1] || tabs[index - 1]
          if (nextTab) {
            activeName = nextTab.name
          }
        }
      })
    }

    editableTabsValue.value = activeName
    editableTabs.value = tabs.filter((tab) => {
      return tab.name !== targetName
    })

  }
}


handleAllDatasource() ;

/** 提供对外访问接口 */
defineExpose({
   submitForm 
})

</script>

<style scoped lang="scss">
</style>


