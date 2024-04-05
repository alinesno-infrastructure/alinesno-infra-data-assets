<template>
  <div>
    <el-row class="acp-dashboard-panel" :gutter="20">

      <el-col class="panel-col" :span="13">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-link"></i> 服务运行状态</div>
          </div>
          <div class="panel-body">
            <el-row>
              <el-col :span="24">
                <div class="panel-app-status">
                  <div class="app-status-icon">
                    <i class="fa-solid fa-laptop-code"></i>
                  </div>
                  <div class="app-status-text">
                    <div class="app-status-text-desc">展示当前运行服务概览和详情，您可以查看了解目前集成的基础能力和平台能力。 演示环境为最新功能预览版</div>
                  </div>
                </div>
              </el-col>

              <el-col :span="6">
                <div class="box">
                  <div class="circle-wave">
                    <div class="circle-wave-bg"></div>
                    <div class="circle-wave-inner" style="color: $sky-warning-color;">
                      <div class="circle-wave-value"> 100 <div class="unit">分</div>
                      </div>
                      <div class="circle-wave-desc">服务正常</div>
                    </div>
                  </div>
                </div>
              </el-col>

              <el-col :span="18">
                <div class="acp-app-list">
                  <ul>
                    <li class="app-items" v-for="item in screenJsonList" :key="item">
                      <div class="app-icon">
                        <img :src="item.icon" />
                      </div>
                      <div class="app-info">
                        <div class="app-item-title">
                          {{ item.title }}  
                          (<span style="font-weight: 600;font-size:15px">{{ item.productItem.length}}</span>/<span style="font-size:12px">{{ item.productItem.length }}</span>)
                        </div>
                        <div class="app-item desc">{{ item.remark }}</div>
                      </div>
                    </li>
                  </ul>
                </div>
              </el-col>
            </el-row>


          </div>
          <div class="panel-footer">
            <div class="footer-link">
            </div>
          </div>
        </div>
      </el-col>

      <el-col :span="5">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-user-nurse"></i> AIP健康报告</div>
          </div>
          <div class="panel-body">
            <ul class="panel-item-text">
              <li>
                <div class="item-health-box">
                  <div class="item-health-title">未解决问题</div>
                  <div class="item-health-count">41</div>
                  <div class="item-health-outtime">过去7天</div>
                </div>
              </li>
              <li>
                <div class="item-health-box">
                  <div class="item-health-title">预定更改</div>
                  <div class="item-health-count">32</div>
                  <div class="item-health-outtime">即将到来和过去 7 天</div>
                </div>
              </li>
              <li>
                <div class="item-health-box">
                  <div class="item-health-title">运行通知</div>
                  <div class="item-health-count">32</div>
                  <div class="item-health-outtime">过去14天</div>
                </div>
              </li>
              <li>
                <div class="item-health-box">
                  <div class="item-health-title">其它通知</div>
                  <div class="item-health-count">12</div>
                  <div class="item-health-outtime">过去7天</div>
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

      <!-- 快捷方式 -->
      <el-col :span="6">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-feather"></i> 快捷接入中台能力</div>
          </div>
          <div class="panel-body">
            <ul class="panel-item-text">
              <li v-for="item in acpProjectBuild" :key="item.id">
                <div class="item-icon">
                  <img :src="item.icon" alt="" />
                </div>
                <div class="item-text">
                  <div class="item-text-main-title">
                    <router-link :to="item.link">
                     {{ item.title }}  <i class="fa-solid fa-arrow-up-right-from-square"></i>
                    </router-link>
                  </div>
                  <div class="item-text-main-desc">{{ item.desc }}</div>
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

const acpProjectBuild = [
  {id:'1' , icon:'https://d1.awsstatic.com/webteam/homepage/solutions/60-analytics.27a6ebec1a9e415ef6cea6ec2c9f7761c8aa3ea6.png' , title:'创建业务服务组件' , desc:'集成可视化监控和自动集成部署服务' , link:'/dashboard/build/business'} ,
  {id:'2' , icon:'https://d1.awsstatic.com/webteam/homepage/solutions/60-storage.2fae209fbce7aa8e4eee9b6bde7c64e2798fe1e3.png' , title:'集成数据中台能力' , desc:'集成数据治理套件集成数据分析输出' , link:'/dashboard/build/data'} ,
  {id:'3' , icon:'https://d1.awsstatic.com/webteam/homepage/solutions/60-storage.2fae209fbce7aa8e4eee9b6bde7c64e2798fe1e3.png' , title:'集成自动运维能力' , desc:'业务运维管理和自动化运维管理能力' , link:'/dashboard/build/operation'} ,
] ;

const screenJsonList = [
  {
    "addTime": "2023-05-02 05:15:12",
    "departmentId": null,
    "lastUpdateOperatorId": null,
    "usage": 7,
    icon: 'https://d1by4p17n947rt.cloudfront.net/icon/9da5a168cf8194c8ee5ed192a443d563-674375b53bc8ae94f48cfdb5c81e8363.svg',
    "remark": "数据运营治理服务",
    "updateTime": null,
    "title": "数据运营治理服务",
    "productItem": [
      {
        "addTime": "2021-07-23 04:56:00",
      },
      {
        "addTime": "2021-07-23 04:56:11",
      },
      {
        "addTime": "2023-02-15 15:01:56",
      },
      {
        "addTime": "2022-12-21 14:53:10",
      },
      {
        "addTime": "2021-07-23 04:55:48",
      },
      {
        "addTime": "2023-03-16 16:49:16",
      },
      {
        "addTime": "2021-07-23 04:56:23",
      }
    ],
  },
  {
    "addTime": "2023-05-02 05:13:58",
    "departmentId": null,
    "lastUpdateOperatorId": null,
    "usage": 3,
    icon: 'https://d1by4p17n947rt.cloudfront.net/icon/3da5e8169d2f1426f99fbef54575fe96-6382cb2dfdd2f74c99bc8a64a338358e.svg',
    "remark": "微服务技术引擎框架",
    "updateTime": null,
    "title": "微服务技术引擎框架",
    "productItem": [
      {
        "addTime": "2021-07-23 04:50:38",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "/appscreen/technique_devops",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "6ba4914274a9b3fb09580caa61b047a6",
        "viewCount": null,
        "operatorId": null,
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": "2021-11-17 06:20:41",
        "productDescribe": null,
        "productTypeId": "787440042262921216",
        "hasInner": 1,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "技术研发体系",
        "hasRecommend": 0,
        "applicationId": "0",
        "shortName": null
      },
      {
        "addTime": "2021-07-23 04:55:02",
        "departmentId": null,
        "sortNumber": 11,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-initializr.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "afc6ca933b57a3c1766247c124da8702",
        "viewCount": null,
        "operatorId": null,
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": "2021-07-23 10:40:23",
        "productDescribe": null,
        "productTypeId": "787440042262921216",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "项目生成器服务",
        "hasRecommend": 1,
        "applicationId": "0",
        "shortName": null
      },
      {
        "addTime": "2021-07-23 04:51:12",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "/appscreen/microservices",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "12eda4ce44d5b13826bc1996ee0f0012",
        "viewCount": null,
        "operatorId": null,
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": "2021-11-17 06:21:48",
        "productDescribe": null,
        "productTypeId": "787440042262921216",
        "hasInner": 1,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "微服务研发引擎",
        "hasRecommend": 0,
        "applicationId": "0",
        "shortName": null
      }
    ],
    "hasDelete": 0,
    "hasStatus": 0,
    "total": 3,
    "screenIndex": 10,
    "deleteTime": null,
    "deleteManager": null,
    "tenantId": "0",
    "fieldProp": "integration",
    "id": "aae9c0e5bbe9ac17171e069c027dad2a",
    "applicationId": null,
    "operatorId": "784553886277959680",
    "applicationName": null,
    "fieldId": null,
    "status": "1"
  },
  {
    "addTime": "2023-05-02 05:14:31",
    "departmentId": null,
    "lastUpdateOperatorId": null,
    "usage": 7,
    icon: 'https://d1by4p17n947rt.cloudfront.net/icon/a5ffe5487f62ef75d8e5cf78c18525a5-d4867f9d4adcd749f0c5aff987232847.svg',
    "remark": "技术研发组件服务",
    "updateTime": null,
    "title": "技术研发组件服务",
    "productItem": [
      {
        "addTime": "2021-07-23 04:53:46",
        "departmentId": null,
        "sortNumber": 8,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-notice.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "c230a72a7e1bb97c8d383a27327d7a77",
        "viewCount": null,
        "operatorId": null,
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": "2021-07-23 13:12:21",
        "productDescribe": null,
        "productTypeId": "787440246718464000",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": " 多渠道通知服务 ",
        "hasRecommend": 1,
        "applicationId": "0",
        "shortName": null
      },
      {
        "addTime": "2022-04-18 23:08:30",
        "departmentId": null,
        "sortNumber": 10,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-message.beta.linesno.com/index",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "14d3deb5b6fcfdb950a9f1a0d1531abf",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "787440246718464000",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "分布式消息服务",
        "hasRecommend": 1,
        "applicationId": null,
        "shortName": null
      },
      {
        "addTime": "2023-02-15 14:59:52",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-captcha.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "71340e02f2c3a2da8adc69c93910ab1a",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": null,
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "787440246718464000",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "安全验证码服务",
        "hasRecommend": 0,
        "applicationId": null,
        "shortName": null
      },
      {
        "addTime": "2022-02-21 14:26:50",
        "departmentId": null,
        "sortNumber": 4,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-gateway.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "053b9e440df2d3a2ddfc714b206c9731",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "787440246718464000",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "数据资产服务",
        "hasRecommend": 1,
        "applicationId": null,
        "shortName": null
      },
      {
        "addTime": "2021-07-23 04:53:05",
        "departmentId": null,
        "sortNumber": 14,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-authority.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "d35c6e325ebcbeeb2206cde013d0542e",
        "viewCount": null,
        "operatorId": null,
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": "2021-07-23 13:16:01",
        "productDescribe": null,
        "productTypeId": "787440246718464000",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "recommend",
        "identityStatus": null,
        "tenantId": "0",
        "name": "权限资源引擎服务",
        "hasRecommend": 1,
        "applicationId": "0",
        "shortName": null
      },
      {
        "addTime": "2021-07-23 04:52:51",
        "departmentId": null,
        "sortNumber": 5,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-oauth-admin.beta.linesno.com/",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "e87772d81e3a2fb37e82c9cc97c050de",
        "viewCount": null,
        "operatorId": null,
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": "2021-08-14 01:36:24",
        "productDescribe": null,
        "productTypeId": "787440246718464000",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "单点登陆管理",
        "hasRecommend": 1,
        "applicationId": "0",
        "shortName": null
      },
      {
        "addTime": "2021-07-23 04:54:48",
        "departmentId": null,
        "sortNumber": 7,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-storage.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "ffb4d99c033cbacf52e9e0945c7fefd7",
        "viewCount": null,
        "operatorId": null,
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": "2021-07-23 13:11:30",
        "productDescribe": null,
        "productTypeId": "787440246718464000",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "公共存储服务",
        "hasRecommend": 1,
        "applicationId": "0",
        "shortName": null
      }
    ],
    "hasDelete": 0,
    "hasStatus": 0,
    "total": 7,
    "screenIndex": 10,
    "deleteTime": null,
    "deleteManager": null,
    "tenantId": "0",
    "fieldProp": "integration",
    "id": "e7eb9a5acf75b02b80b1bb0ec1dbd71a",
    "applicationId": null,
    "operatorId": "784553886277959680",
    "applicationName": null,
    "fieldId": null,
    "status": "1"
  },
  {
    "addTime": "2023-05-02 05:16:34",
    "departmentId": null,
    "lastUpdateOperatorId": null,
    "usage": 2,
    icon: 'https://d1by4p17n947rt.cloudfront.net/icon/d88319dfa5d204f019b4284149886c59-7d586ea82f792b61a8c87de60565133d.svg', 
    "remark": "一体化安全感知套件",
    "updateTime": null,
    "title": "一体化安全感知套件",
    "productItem": [
      {
        "addTime": "2022-12-21 14:53:10",
        "departmentId": null,
        "sortNumber": 11,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-databus.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "a18f33c24aaf645d80ab4b96ff4ba5ce",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "787440555398266880",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "数据采集总线",
        "hasRecommend": 0,
        "applicationId": null,
        "shortName": null
      },
      {
        "addTime": "2023-03-12 06:40:45",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-security.beta.linesno.com/",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "a23cbf1efa2dc6df7ac5820102dbae5f",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "332e19911ee96f8a9241471b6954ec7f",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "一体化安全触感服务",
        "hasRecommend": 0,
        "applicationId": null,
        "shortName": null
      }
    ],
    "hasDelete": 0,
    "hasStatus": 0,
    "total": 2,
    "screenIndex": 1,
    "deleteTime": null,
    "deleteManager": null,
    "tenantId": "0",
    "fieldProp": "integration",
    "id": "214f92e4267f5dca79b129351f884f3f",
    "applicationId": null,
    "operatorId": "784553886277959680",
    "applicationName": null,
    "fieldId": null,
    "status": "1"
  },
  {
    "addTime": "2023-05-02 05:15:53",
    "departmentId": null,
    "lastUpdateOperatorId": null,
    "usage": 3,
    icon: 'https://d1by4p17n947rt.cloudfront.net/icon/f5d2c00d40914bff4f82f29f9ef768bc-53a84099cf556710383a52b4612a8612.svg',
    "remark": "自动运维治理套件",
    "updateTime": null,
    "title": "自动运维治理套件",
    "productItem": [
      {
        "addTime": "2023-03-05 02:59:31",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-configuration.beta.linesno.com/",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "b8bb226de8de62decb1e293283fc6c62",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "793938687002935296",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "配置管理服务",
        "hasRecommend": 0,
        "applicationId": null,
        "shortName": null
      },
      {
        "addTime": "2021-07-23 05:00:55",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-watcher.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "569297434f090ac584cf615c333f6ecd",
        "viewCount": null,
        "operatorId": null,
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": "2021-08-02 01:29:32",
        "productDescribe": null,
        "productTypeId": "793938687002935296",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "审计日志服务",
        "hasRecommend": 1,
        "applicationId": "0",
        "shortName": null
      },
      {
        "addTime": "2022-03-23 12:51:48",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-operation-pipeline.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "801e85de62ca5b9f8f76b5e31987730e",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "793938687002935296",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "自动化操作服务",
        "hasRecommend": 0,
        "applicationId": null,
        "shortName": null
      }
    ],
    "hasDelete": 0,
    "hasStatus": 0,
    "total": 3,
    "screenIndex": 1,
    "deleteTime": null,
    "deleteManager": null,
    "tenantId": "0",
    "fieldProp": "integration",
    "id": "5e33fa098910e77c8334d1d8c668dd69",
    "applicationId": null,
    "operatorId": "784553886277959680",
    "applicationName": null,
    "fieldId": null,
    "status": "1"
  },
  {
    "addTime": "2023-05-02 05:15:31",
    "departmentId": null,
    "lastUpdateOperatorId": null,
    "usage": 1,
    icon: 'https://d1by4p17n947rt.cloudfront.net/icon/a5ffe5487f62ef75d8e5cf78c18525a5-d4867f9d4adcd749f0c5aff987232847.svg',
    "remark": "容器云运营套件",
    "updateTime": null,
    "title": "容器云运营套件",
    "productItem": [
      {
        "addTime": "2023-04-01 16:25:17",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-kube-platform.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "c6fca12e348cccf97495b3aea33adcef",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "332e19911ee96f8a9241471b6954ec7f",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "internal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "容器云管理平台",
        "hasRecommend": 0,
        "applicationId": null,
        "shortName": null
      }
    ],
    "hasDelete": 0,
    "hasStatus": 0,
    "total": 1,
    "screenIndex": 1,
    "deleteTime": null,
    "deleteManager": null,
    "tenantId": "0",
    "fieldProp": "integration",
    "id": "8beaa35a097bf2cd7bdc9714e41336e2",
    "applicationId": null,
    "operatorId": "784553886277959680",
    "applicationName": null,
    "fieldId": null,
    "status": "1"
  },
  {
    "addTime": "2023-05-02 05:16:17",
    "departmentId": null,
    "lastUpdateOperatorId": null,
    "usage": 2,
    icon: 'https://d1by4p17n947rt.cloudfront.net/icon/6e9e16955bd76c1d3a9996e0d6d3ad15-04cfc8c62f597690680d948b366980cf.svg',
    "remark": "DevOps自动化套件",
    "updateTime": null,
    "title": "DevOps自动化套件",
    "productItem": [
      {
        "addTime": "2022-03-23 12:51:48",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-operation-pipeline.beta.linesno.com",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "801e85de62ca5b9f8f76b5e31987730e",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "793938687002935296",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "normal",
        "identityStatus": null,
        "tenantId": "0",
        "name": "自动化操作服务",
        "hasRecommend": 0,
        "applicationId": null,
        "shortName": null
      },
      {
        "addTime": "2023-05-01 22:00:45",
        "departmentId": null,
        "sortNumber": 1,
        "hasStatus": 0,
        "productBrief": null,
        "linkPath": "http://alinesno-jenkins.beta.linesno.com/",
        "deleteManager": null,
        "itemStatus": null,
        "fieldProp": null,
        "id": "6a3512430d3b610ffa67263ee365cc13",
        "viewCount": null,
        "operatorId": "784553886277959680",
        "applicationName": null,
        "fieldId": null,
        "lastUpdateOperatorId": "784553886277959680",
        "banner": null,
        "updateTime": null,
        "productDescribe": null,
        "productTypeId": "793938687002935296",
        "hasInner": 0,
        "hasDelete": 0,
        "deleteTime": null,
        "subName": null,
        "prodStatus": "company",
        "identityStatus": null,
        "tenantId": "0",
        "name": "持续集成服务",
        "hasRecommend": 0,
        "applicationId": null,
        "shortName": null
      }
    ],
    "hasDelete": 0,
    "hasStatus": 0,
    "total": 2,
    "screenIndex": 1,
    "deleteTime": null,
    "deleteManager": null,
    "tenantId": "0",
    "fieldProp": "integration",
    "id": "ea82737f643d66b4b702cfd098a82d62",
    "applicationId": null,
    "operatorId": "784553886277959680",
    "applicationName": null,
    "fieldId": null,
    "status": "1"
  }
];

const apps = ref([
  { icon: 'https://d1by4p17n947rt.cloudfront.net/icon/9da5a168cf8194c8ee5ed192a443d563-674375b53bc8ae94f48cfdb5c81e8363.svg', name: '托管静态 Web 应用程序', desc: 'With Route 53 (3 分钟)' },
  { icon: 'https://d1by4p17n947rt.cloudfront.net/icon/a5ffe5487f62ef75d8e5cf78c18525a5-d4867f9d4adcd749f0c5aff987232847.svg', name: '在 AIP 上构建 SQL Server', desc: 'With Route 53 (3 分钟)' },
  { icon: 'https://d1by4p17n947rt.cloudfront.net/icon/6e9e16955bd76c1d3a9996e0d6d3ad15-04cfc8c62f597690680d948b366980cf.svg', name: '部署 SAP on AIP', desc: 'With Route 53 (3 分钟)' },
  { icon: 'https://d1by4p17n947rt.cloudfront.net/icon/d88319dfa5d204f019b4284149886c59-7d586ea82f792b61a8c87de60565133d.svg', name: '启动虚拟机', desc: 'With EC2 (2 分钟)' },
  { icon: 'https://d1by4p17n947rt.cloudfront.net/icon/3da5e8169d2f1426f99fbef54575fe96-6382cb2dfdd2f74c99bc8a64a338358e.svg', name: '启动开发项目', desc: 'With Route 53 (3 分钟)' },
  { icon: 'https://d1by4p17n947rt.cloudfront.net/icon/051de32597041e41f73b97d61c67a13b-9cbdaf85e3bcf29b656fdedd8e6d1305.svg', name: '构建 Web 应用程序', desc: 'With Route 53 (3 分钟)' },
  { icon: 'https://d1by4p17n947rt.cloudfront.net/icon/fb0cde6228b21d89ec222b45efec54e7-0856e92285f4e7ed254b2588d1fe1829.svg', name: '部署无服务器微服务', desc: 'With Route 53 (3 分钟)' },
  { icon: 'https://d1by4p17n947rt.cloudfront.net/icon/7177e919b32ad97825f95e902595014b-1594766d92813b5baeb706c453f91de0.svg', name: '使用虚拟服务器构建', desc: 'With Route 53 (3 分钟)' },
  { icon: 'https://d1by4p17n947rt.cloudfront.net/icon/fb0cde6228b21d89ec222b45efec54e7-0856e92285f4e7ed254b2588d1fe1829.svg', name: '开始迁移到 AIP', desc: 'With Route 53 (3 分钟)' },
]);

</script>