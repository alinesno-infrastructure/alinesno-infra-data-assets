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
                <li class="box-item" v-for="item in directContent" :key="item">
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
                <li class="app-items" style="width:100%" v-for="item in apps" :key="item">
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

const directContent = ref([
  { icon: 'fa-brands fa-slack', name: '智能鉴黄', desc: '识别文本中的色情行为描述、色情资源链接、低俗交友、污秽文爱等涉黄内容' },
  { icon: 'fa-solid fa-list-check', name: '违禁违规', desc: '识别暴力行为、恐怖描述、赌博、毒品、枪支弹药等违禁文字内容' },
  { icon: 'fa-solid fa-at', name: '恶意推广', desc: '识别带有售卖意向的软文广告、微信/QQ/联系方式等违规广告，支持包含符号、图标等变异广告' },
  { icon: 'fa-solid fa-list-check', name: '低俗辱骂', desc: '识别文本中的侮辱谩骂、人身攻击、消极宣泄等不良内容' },
  { icon: 'fa-solid fa-file-word', name: '低质灌水', desc: '识别网络社区常见的乱码、水帖、刷屏等无意义的灌水信息' },
]);

const apps = ref([
  { icon: 'fa-solid fa-eye-slash', name: '智能内容审核', desc: '识别文章内容中的违规信息、低俗内容、暴力内容等敏感信息' },
  { icon: 'fa-solid fa-user-shield', name: '注册信息检测', desc: '识别注册信息中的违规内容、虚假信息等不当信息' },
  { icon: 'fa-solid fa-comment-slash', name: '评论过滤系统', desc: '过滤用户评论中的不良言论、侮辱性内容、广告等信息' },
]);

</script>