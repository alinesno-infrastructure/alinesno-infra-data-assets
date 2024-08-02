<template>
  <div>
    <el-row class="acp-dashboard-panel" :gutter="20">
      <el-col class="panel-col" :span="19">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-link"></i> 业务最新巡检</div>
          </div>
          <div class="panel-body" style="height: auto;position: relative;">
            <div class="direct-box">
              <ul>
                <li class="box-item" v-for="item in dataAssetManagementBenefits" :key="item">
                  <div class="dire-panel">
                    <div class="panel-title">
                      <i class="dire-panel-icon" :class="item.icon" :alt="item.name" />
                      {{ item.name }}
                    </div>
                    <div class="panel-describe">通过简单几步，图中7种主流开发环境轻松部署，摆脱海量文档搜索之苦。</div>
                    <!-- <div class="panel-tip">访问链接</div> -->
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
            <div class="header-title"><i class="fa-solid fa-link"></i> 应用场景</div>
          </div>
          <div class="panel-body" style="height: auto;position: relative;">
            <div class="acp-app-list">
              <ul>
                <li class="app-items" style="width:100%" v-for="item in dataAssetScenarios" :key="item">
                  <div class="app-info">
                    <div class="app-item-title">
                      <i :class="item.icon" :alt="item.name" />
                      {{ item.name }}
                    </div>
                    <div class="app-item desc">{{ item.desc }}</div>
                  </div>
                </li>
              </ul>
            </div>
          </div>

        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script setup>

const dataAssetManagementBenefits = ref([
  { icon: 'fa-solid fa-chart-line', name: '提升决策效率', desc: '通过高质量的数据支持，帮助组织更快做出明智的业务决策' },
  { icon: 'fa-solid fa-box-open', name: '增强数据可见性', desc: '提供全面的数据视图，使用户能够轻松找到所需的特定数据' },
  { icon: 'fa-solid fa-sort-amount-down-alt', name: '优化数据治理', desc: '简化数据管理流程，提高数据质量和一致性' },
  { icon: 'fa-solid fa-lock', name: '加强安全保障', desc: '实施严格的安全措施，保护敏感信息免受未授权访问' },
  { icon: 'fa-solid fa-network-wired', name: '促进协作共享', desc: '构建跨部门合作的环境，确保数据在组织内部顺畅流通' }
]);

const dataAssetScenarios = ref([
  { icon: 'fa-solid fa-chart-line', name: '业务决策支持', desc: '通过分析历史数据趋势，为管理层提供决策依据，优化业务策略' },
  { icon: 'fa-solid fa-sort-amount-down-alt', name: '数据质量提升', desc: '监测和改善数据质量，确保数据的准确性和完整性' },
  { icon: 'fa-solid fa-lock', name: '数据安全防护', desc: '实施数据访问控制和加密措施，保护敏感数据不被未授权访问' },
]);

</script>