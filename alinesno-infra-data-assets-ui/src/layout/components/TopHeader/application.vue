<template>
<div class="select-app">
    <el-select v-if="applicationList.length > 0" v-model="value" placeholder="请选择">
    <el-option
        v-for="item in applicationList"
        :key="item.value"
        :label="item.label"
        :value="item.value">
    <span style="float: left">{{ item.label }}</span>
    <span style="float: right; color: #8492a6; font-size: 14px">{{ item.value }}</span>
    </el-option>
    </el-select>
</div>
</template>

<script>
  export default {
    data() {
      return {
        applicationList : [],
        value: ''
      }
    }
  }
</script>

<style lang="scss" scoped>
.select-app {
    margin-top: 7px ;
    margin-left: 20px;
    float: left ;
}
.el-input__inner{
    font-size: 14px !important ;
    border-radius: 2px !important ;
}
</style>
