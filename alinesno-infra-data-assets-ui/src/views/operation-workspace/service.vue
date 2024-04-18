<template>
  <div>
    <el-row class="acp-dashboard-panel" :gutter="20">
      <el-col class="panel-col" :span="19">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-file-waveform"></i>  数据资产列表</div>
          </div>
          <div class="panel-body acp-height-auto" style="padding: 0;padding-bottom: 10px;">
            <div class="acp-app-list">
              <ul>
                <li class="app-items" v-for="item in apps" :key="item" style="width:calc(25% - 10px)">
                  <div class="app-icon">
<!--                    <img :src="item.icon" :alt="item.name" />-->
                    <i :class="item.icon" style="font-size: 20px" />
                  </div>
                  <div class="app-info">
                    <div class="app-item-title">{{ item.name }}</div>
                    <div class="app-item desc">{{ item.desc }}</div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </el-col>

      <el-col :span="5">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-user-nurse"></i> 资产情况概览</div>
          </div>
          <div class="panel-body acp-height-auto">
            <ul class="panel-item-text">
              <li style="width:50%;padding:4px;border-bottom: 0px;" v-for="item in operationAssets" :key="item.id">
                <div class="item-health-box">
                  <div class="item-health-title">{{ item.title }}</div>
                  <div class="item-health-count">{{ item.count }}</div>
                </div>
              </li>
            </ul>
          </div>
          <div class="panel-footer">
            <div class="footer-link">
            </div>
          </div>
        </div>
      </el-col>

    </el-row>
  </div>
</template>

<script setup>

const operationAssets = ref([
  { id: '1', title: '资产总量', count: 3762428145 },
  { id: '2', title: '资产类型', count: 164 },
  { id: '3', title: '资产主题', count: 635723 },
  { id: '4', title: '行业统计', count: 31 },
  { id: '5', title: '使用统计', count: 78366485 },
])

const apps = ref([
  { icon: 'fa-brands fa-slack', name: '学生信息数据', desc: '学生的个人资料、学籍信息、成绩记录等' },
  { icon: 'fa-solid fa-list-check', name: '教职工信息', desc: '教师和其他工作人员的个人资料、职称等' },
  { icon: 'fa-solid fa-at', name: '课程资料数据', desc: '课程大纲、教学材料、课程评估数据等' },
  { icon: 'fa-solid fa-list-check', name: '学校财务数据', desc: '学校的财务报表、预算、经费使用情况等' },
  { icon: 'fa-solid fa-file-word', name: '校园设施管理数据', desc: '设施维护记录、安全检查记录、设备清单等' },
  { icon: 'fa-solid fa-eye-slash', name: '学生表现数据', desc: '学生的考试成绩、出勤情况、行为表现等' },
  { icon: 'fa-solid fa-file-word', name: '学校教学数据', desc: '教学效果评估、学生评价、教学资源使用情况等' },
  { icon: 'fa-solid fa-user-shield', name: '招生数据数据', desc: '招生计划、招生宣传材料、招生渠道数据等' },
  { icon: 'fa-solid fa-comment-slash', name: '校友关系数据', desc: '校友基本信息、校友捐赠记录等' },
]);

</script>

<style lang="scss" scoped>
.item-health-title {
  margin-bottom: 5px !important;
}

.item-health-count {
  margin-bottom: 5px;
}

.acp-dashboard .acp-app-list ul {

  .app-status {
    float: right;
    margin-right: 10px;
    font-size: 13px;
    line-height: 13px;
    color: #545b64 ;
  }

  li.app-items .app-item.desc {
    font-size: 12px;
    color: #545b64;
    margin-top: 5px;
  }

  li.app-items {
    list-style: none;
    float: left;
    border-bottom: 1px solid #fafafa;
    width: 50%;
    padding: 10px 0px;
    width: calc(33% - 10px);
    background: #fafafa;
    border-radius: 3px;
    padding: 10px;
    margin-right: 5px;
    margin-top: 10px;
    margin-left: 5px;
  }
}
</style>