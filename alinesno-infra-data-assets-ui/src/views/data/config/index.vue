<template>
  <div class="app-container global-config-container">
    <div class="global-section-title">
      全局配置管理
    </div>
    <div class="config-setion">
      <div class="section-title">
        全局配置
      </div>
      <div>
        <el-row :gutter="20">
          <el-col v-for="(item, index) in globalConfigs" :key="index" :span="8">
            <router-link :to="item.link">
              <div :key="index" class="config-item" >
                <div class="config-icon">
                  <i :class="item.icon" />
                </div>
                <div class="config-info">
                  <span class="config-name">
                    {{ item.configName }}  
                    <el-tag type="danger" size="small" v-if="item.status">集成中</el-tag>
                  </span>
                  <span class="config-desc">{{ item.configDesc }}</span>
                </div>
              </div>
            </router-link>
          </el-col>
        </el-row>
      </div>
    </div>
    <div class="config-setion">
      <div class="section-title">
        资产运营 
      </div>
      <div>
        <el-row :gutter="20">
          <el-col v-for="(item, index) in modelConfigs" :span="8" :key="index">
            <router-link :to="item.link">
              <div :key="index" class="config-item">
                <div class="config-icon">
                  <i :class="item.icon" />
                </div>
                <div class="config-info">
                  <span class="config-name">
                    {{ item.configName }}  
                    <el-tag type="danger" size="small" v-if="item.status">集成中</el-tag>
                  </span>
                  <span class="config-desc">{{ item.configDesc }}</span>
                </div>
              </div>
            </router-link>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script setup name="Index">
const globalConfigs = [
  {
    id: 1,
    configName: "元数据配置",
    configDesc: "管理系统元数据模型、字段定义及关联关系，配置元数据的提取、同步与展示规则。",
    icon: "fa-solid fa-database", // 元数据核心是数据基础信息
    link: '/assets/data/datasource/index',
  },
  {
    id: 1,
    configName: "指标类型",
    configDesc: "定义各类指标的分类体系、数据类型及计算维度，建立指标类型的层级与关联关系。",
    icon: "fa-solid fa-tags", // 类型本质是分类标签
    link: "/metrics/data/asset/metricType/index"
  },
  {
    id: 2,
    configName: "指标配置",
    configDesc: "设置指标的计算逻辑、阈值范围、统计周期及展示格式，配置指标预警与联动规则。",
    icon: "fa-solid fa-calculator", // 指标核心是计算配置
    link: "/metrics/data/asset/metricConfig/index" , 
  },
  // 数据资产配置
  {
    id: 9,
    configName: "接口目录",
    configDesc: "管理系统所有API接口的分类、文档、调用权限及生命周期，支持接口查询与测试配置。",
    icon: "fa-solid fa-code-branch", // 接口是代码分支的具象化
    status: 0,
    link: "/service/data/fastapi/group/index" 
  },
];

const modelConfigs = [
  {
    id: 5,
    configName: "周期配置",
    configDesc: "设置数据同步、报表生成、任务执行等自动化操作的时间周期与触发条件。",
    icon: "fa-solid fa-clock", // 周期核心是时间管理
    status: 0,
    link: "/lift/data/asset/type/index" 
  },
  {
    id: 1,
    configName: "标签配置",
    configDesc: "定义资产、数据或用户的标签体系，设置标签的分类、规则及自动打标逻辑。",
    icon: "fa-solid fa-tag", // 标签直接对应tag图标
    link: "/asset/data/asset/label/index"
  },
  {
    id: 4,
    configName: "满意度评价",
    configDesc: "配置评价维度、评分标准及问卷内容，设置评价结果的统计与展示方式。",
    icon: "fa-solid fa-star", // 评价核心是星级/评分
    link: "/operate/data/operate/appraise/index" , 
  },
  {
    id: 5,
    configName: "权限管理",
    configDesc: "配置用户角色、资源访问权限及操作权限，管理权限继承与数据隔离规则。",
    icon: "fa-solid fa-shield", // 权限核心是安全防护
    link: "/function/data/asset/power/index" , 
  },
  {
    id: 6,
    configName: "备份与恢复",
    configDesc: "设置数据备份策略、存储位置及保留周期，配置备份文件的恢复流程与验证机制。",
    icon: "fa-solid fa-cloud-arrow-down", // 备份是下载/存储动作
    status: 0 ,
    link: "/function/data/asset/backup/index" 
  }, 
];
</script>

<style lang="scss" scoped>
/* 样式保持不变 */
.global-config-container {
  margin: auto;
  width: 90%;
  max-width: 1450px;

  .global-section-title {
    font-size: 22px;
    padding-bottom: 20px;
    color: #14141f;
  }

 .config-setion {
    margin-bottom: 20px;

   .section-title {
      padding-top: 20px;
      margin-bottom: 20px;
      border-top: 1px solid rgb(244, 244, 248);
      font-size: 18px;
      font-weight: bold;
    }

   .config-item {
      display: flex;
      padding: 15px;
      border-radius: 8px;
      margin-top: 10px;
      margin-bottom: 10px;
      align-items: center;
      background-color: #fafafa;
      cursor: pointer;
      gap: 10px;

      &:hover {
        background-color: #f5f5f5;
      }

     .config-icon {
        font-size: 26px;
        background: rgba(175,175,207,.225);
        padding: 10px;
        width: 55px;
        height: 55px;
        color: #1d75b0;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
      }

     .config-info {
        display: flex;
        flex-direction: column;
        gap: 10px;
        width: calc(100% - 60px);

       .config-name {
          font-size: 16px;
        }

       .config-desc {
          font-size: 14px;
          line-height: 1.4rem;
          color: #888;
        }

       .config-link {
          font-size: 15px;
          color: #145799;
          text-decoration: none;

          &:hover {
            text-decoration: underline;
          }
        }
      }
    }
  }
}
</style>    