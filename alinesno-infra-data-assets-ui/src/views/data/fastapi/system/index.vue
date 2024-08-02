<template>
   <div class="app-container">
    <div class="label-title">
      <div class="tip">接口安全配置</div>
      <div class="sub-tip">安全配置服务平台的配置IP防火墙状态，特殊字符输入等</div>
    </div>
    <div class="form-container" >
      <el-form
        :model="form"
        :rules="rules"
        v-loading="loading"
        ref="form"
        label-width="180px"
        class="demo-form">

        <el-form-item label="是否开启限制" prop="themeCode">
            <el-switch
              :active-value="1"
              :inactive-value="0"
            />
        </el-form-item>

        <el-form-item label="模式" prop="themeCode">
          <el-radio-group v-model="form.status">
                <el-radio
                    v-for="dict in sys_common_status"
                    :key="dict.dictLabel"
                    :label="dict.dictLabel"
                >{{ dict.dictLabel }}</el-radio>
            </el-radio-group>
        </el-form-item>

        <el-form-item label="异常提示信息" prop="themeCode">
          <el-input type="input" show-word-limit v-model="form.themeCode" readonly placeholder="请输入主题代码">
            <el-button slot="append" @click="configTheme()" icon="el-icon-edit">配置品牌</el-button>
          </el-input>
        </el-form-item>

        <el-row>
            <el-col :span="24">
               <el-form-item label="特殊字符" prop="defaultIndex">
                  <el-input type="input" maxlength="500" show-word-limit v-model="form.defaultIndex" placeholder="权限年份"></el-input>
               </el-form-item>
            </el-col> 
            <el-col :span="24">
               <el-form-item label="禁止访问IP" prop="defaultIndex">
                  <el-input type="input" maxlength="500" show-word-limit v-model="form.defaultIndex" placeholder="每填写一个IP，使用英文逗号进行分隔"></el-input>
               </el-form-item>
            </el-col> 
        </el-row>

        <br/>

        <el-form-item>
          <el-button type="primary" @click="submitForm('form')">
            保存
          </el-button>
          <el-button @click="resetForm">
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup name="Config">

const { proxy } = getCurrentInstance();
const { sys_oper_type, sys_common_status } = proxy.useDict("sys_oper_type","sys_common_status");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    title: undefined,
    operName: undefined,
    businessType: undefined,
    status: undefined
  }
});

const { queryParams, form } = toRefs(data);

</script>

<style scoped lang="scss">
  .form-container {
    max-width: 960px;
    margin-left: auto;
    margin-right: auto;
    margin-top: 20px;
  }

  .label-title {
    text-align: center;
    max-width: 960px;
    margin-left: auto;
    margin-right: auto;
    margin-top: 10px;

    .tip {
      padding-bottom: 10px;
      font-size: 26px;
      font-weight: bold;
    }

    .sub-tip {
      font-size: 13px;
      text-align: center;
      padding: 10px;
    }
  }

  .image{
    width:100%;
    height: 120px ;
  }

  .select-card {
    border: 1px solid rgb(0, 91, 212) ;
  }
</style>


