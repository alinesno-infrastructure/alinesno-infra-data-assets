<template>
  <div class="app-container acp-dashboard">
    <div class="smart-container">
      <el-row>
        <el-col :span="18">
          <div class="robot-chat-windows">
            <div class="robot-chat-header">
              <div class="chat-header-title">
                在线专家服务
              </div>
              <div class="chat-header-desc">
                售后在线实时沟通，快速解决您的问题
              </div>
            </div>
            <div class="robot-chat-body">
              <!-- 聊天窗口_start -->

              <div class="robot-chat-ai-say-box">
                <div class="chat-ai-header">
                  <div class="header-images">
                    <img src="https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_family.jpg" />
                  </div>
                </div>
                <div class="chat-ai-say-body">
                  <div class="say-message-info">智能专家体系 07-16 19:19:51</div>
                  <div class="say-message-body">
                    Hi，luoandon，欢迎使用售后在线服务。 <br/>
                    你可以选择你的应用AIP使用场景来提问，然后完成专家服务的接入 <br/>
                    请用“关键字+问题”描述您的问题，如添加搜索字段[username]
                  </div>
                </div>
              </div>

              <div class="robot-chat-ai-say-box">
                <div class="chat-ai-header say-right-window">
                  <div class="header-images">
                    <img src="http://data.linesno.com/switch_header.png" />
                  </div>
                </div>
                <div class="chat-ai-say-body say-right-window">
                  <div class="say-message-info">平台管理员 07-16 19:19:51</div>
                  <div class="say-message-body">
                    给出查询出alinesno.code.test的字典接口代码
                  </div>
                </div>
              </div>

              <div class="robot-chat-ai-say-box">
                <div class="chat-ai-header">
                  <div class="header-images">
                    <img src="https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_family.jpg" />
                  </div>
                </div>
                <div class="chat-ai-say-body">
                  <div class="say-message-info">智能专家体系 07-16 19:19:51</div>
                  <div class="say-message-body">
                    请确保在使用该代码之前已经正确引入全局变量和方法，并且`getDicts`方法能够正常获取字典数据。
                  </div>
                </div>
              </div>

              <!-- 聊天窗口_end -->
            </div>
            <div class="robot-chat-footer">
              <div class="chat-completion">
                <textarea placeholder="请用 “产品+问题” 描述您的问题，如：ECS服务器无法远程连接" maxlength="5000" rows="4"></textarea>
              </div>
              <div class="chat-operation">
                <el-checkbox v-model="checked2" style="margin-top:2px">加密发送</el-checkbox> &nbsp;&nbsp;
                <el-button type="primary"><i class="fa-regular fa-paper-plane-top"></i> 发送</el-button>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="robot-chat-help-container">
            <div class="robot-chat-help-panel">
              <div class="robot-chat-help-title">AIP专家列表</div>
              <div class="robot-chat-help-item-list-panel">
                <div class="process-panel">
                  <ul>
                    <li class="item-process">
                      <i class="fa-solid fa-user-tag"></i> 解决方案架构师
                    </li>
                    <li class="item-process">
                      <i class="fa-solid fa-swatchbook"></i> 开发技术架构师
                    </li>
                    <li class="item-process">
                      <i class="fa-solid fa-shield-halved"></i> 数据治理专家
                    </li>
                    <li class="item-process">
                      <i class="fa-brands fa-fedora"></i> 运维技术专家
                    </li>
                    <li class="item-process">
                      <i class="fa-solid fa-hospital-user"></i> 产品设计专家
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="robot-chat-help-panel">
              <div class="robot-chat-help-title">自助工具</div>
              <div class="robot-chat-help-item-list-panel">
                <div class="process-panel">
                  <ul>
                    <li class="item-process" v-for="item in helpAutoList" :key="item.id">
                      <i :class="item.icon"></i> {{ item.name }}
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const checked2 = ref(false)

const helpAutoList = ref([
  { id: '1', icon: 'fa-solid fa-truck-fast', name: '服务运行扫描' },
  { id: '2', icon: 'fa-solid fa-pen-nib', name: '异常排查' },
  { id: '8', icon: 'fa-brands fa-dashcube', name: '服务运行状态' },
  { id: '3', icon: 'fa-solid fa-pen-ruler', name: '异常日志分析' },
  { id: '4', icon: 'fa-solid fa-paint-roller', name: '应用运行状态' },
  { id: '7', icon: 'fa-solid fa-file-word', name: '容器化发布' },
  { id: '4', icon: 'fa-solid fa-compass-drafting', name: '常见异常处理' },
  { id: '5', icon: 'fa-solid fa-feather', name: '链路追踪方式' },
  { id: '6', icon: 'fa-solid fa-user-nurse', name: 'JVM运行优化' },
])

</script>

<style lang="scss" scoped>
.robot-chat-ai-say-box {
  float: left;
  width: 100%;

  .say-right-window {
    float: right !important ;

    .say-message-info{
      text-align: right !important ;
    }
  }

  .chat-ai-header {
    float: left;
    width: 50px;
    margin: 10px;

    .header-images {
      padding: 5px;
      img {
        width: 100%;
        border-radius: 50%;
      }

    }

  }

  .chat-ai-say-body {
    float: left;
    margin-top: 15px;
    font-size: 14px;

    .say-message-info {
      font-size: 13px;
      margin-bottom: 5px;
      color: #999;
    }

    .say-message-body {
      padding: 10px;
      line-height: 1.4rem;
      background: #f5f5f5;
      border-radius: 3px;
    }

  }


}
</style>