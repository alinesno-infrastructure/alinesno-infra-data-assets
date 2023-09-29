<template>
  <div>
    <el-row class="acp-dashboard-panel" :gutter="20">
      <el-col class="panel-col" :span="6">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title">常见业务建议</div>
          </div>
          <div class="panel-body">
            <el-empty :image-size="100" description="这可能是因为您没有运行 Trusted Advisor 检查，或者您没有 AWS Business 或 AWS Enterprise Support 计划。" />
          </div>
        </div>
      </el-col>
      <el-col :span="7">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title">探索AIP使用</div>
          </div>
          <div class="panel-body">
            <ul class="panel-item-text">
              <li>
                <div class="item-icon">
                  <img
                    src="https://d1.awsstatic.com/webteam/homepage/solutions/60-analytics.27a6ebec1a9e415ef6cea6ec2c9f7761c8aa3ea6.png" />
                </div>
                <div class="item-text">
                  <div class="item-text-main-title">AIP 有什么新动向？ </div>
                  <div class="item-text-main-desc">了解基础知识并找到有用的信息</div>
                </div>
              </li>
              <li>
                <div class="item-icon">
                  <img
                    src="https://d1.awsstatic.com/webteam/homepage/solutions/60-storage.2fae209fbce7aa8e4eee9b6bde7c64e2798fe1e3.png" />
                </div>
                <div class="item-text">
                  <div class="item-text-main-title">AIP入门</div>
                  <div class="item-text-main-desc">探索新的 AIP 服务、功能和区域。</div>
                </div>
              </li>
              <li>
                <div class="item-icon">
                  <img
                    src="https://d1.awsstatic.com/webteam/homepage/solutions/60-storage.2fae209fbce7aa8e4eee9b6bde7c64e2798fe1e3.png" />
                </div>
                <div class="item-text">
                  <div class="item-text-main-title">AIP 有什么新动向？ </div>
                  <div class="item-text-main-desc">探索新的 AIP 服务、功能和区域。</div>
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

      <!-- 协助列表-->
      <el-col :span="11">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title">新最公告</div>
          </div>
          <div class="panel-body">
            <ul class="panel-item-text">
              <li>
                <div class="item-datetime">
                  <div class="item-datetime-month">7月</div>
                  <div class="item-datetime-date">12</div>
                </div>
                <div class="item-text">
                  <div class="item-text-main-title">在AIP使用k8s快速搭建网关配置服务系统</div>
                  <div class="item-text-main-desc">了解基础知识并找到有用的信息</div>
                </div>
              </li>
              <li>
                <div class="item-datetime">
                  <div class="item-datetime-month">7月</div>
                  <div class="item-datetime-date">12</div>
                </div>
                <div class="item-text">
                  <div class="item-text-main-title">在校园数据开发项目上使用AIP集成的实战经验</div>
                  <div class="item-text-main-desc">探索新的 AIP 服务、功能和区域。</div>
                </div>
              </li>
              <li>
                <div class="item-datetime">
                  <div class="item-datetime-month">7月</div>
                  <div class="item-datetime-date">12</div>
                </div>
                <div class="item-text">
                  <div class="item-text-main-title">某集团在使用AIP服务构建和集成的示例</div>
                  <div class="item-text-main-desc">探索新的 AIP 服务、功能和区域。</div>
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