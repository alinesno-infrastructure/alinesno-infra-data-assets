<template>
  <div class="learn-container">
    <div class="learn-top-setion">

      <div class="learn-banner">
        <div class="learn-title">
          <div class="title-labels">
            通过 AIP 指导培训提升技能
          </div>
          <div class="title-desc">
            通过参加 AIP 培训，提供本身技能和数字化方面的技能，探索我们 AIP 上的综合课程，提供自己本身的在数字化建设中的能力。
            这是我们推出的在线学习、技能提升和技能方面的统一平台
          </div>
        </div>
        <div class="learn-img">
          <img :src="learnLogo" alt="" />
        </div>
      </div>
    </div>

    <div class="learn-setions">
      <div class="learn-setion-box">

        <div class="setion-title-desc">
          探索 AIP (Alinesno Infrastructure Platform) 上的培训课程和动手实验。
        </div>
        <div class="learn-setion" v-for="(item, index) in learnPanel" :key="index">
              <div class="setion-box">
                <div class="session-side-title">
                  <i class="fa-solid fa-file-word"></i>
                  {{ item.title }}
                </div>
                <ul class="setion-box-ul">
                  <li v-for="(i, indexs) in item.data" :key="indexs" class="setion-box-li">
                    <div style="width: 100%;float: left;">
                      <div class="setion-li-label">
                        <i :class="i.icon"></i>
                        <a :href="i.link" target="_blank">
                          {{ i.label }}
                        </a>
                      </div>
                      <div class="setion-li-label-desc">
                        待添加业务组件和培训描述
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
        </div>

      </div>
    </div>

  </div>
</template>

<script setup name="Index">

import learnLogo from '@/assets/icons/data_03.svg';
const learnPanel = [
  {
    'title': '业务建设集成',
    data: [
      { id: '21', label: '创建业务服务', icon: 'fas fa-laptop-code', link: 'http://alinesno-platform.linesno.com/firstlearn/23_%E5%88%9B%E5%BB%BA%E4%B8%9A%E5%8A%A1%E6%9C%8D%E5%8A%A1.html' },
      { id: '22', label: '配置开发权限', icon: 'fab fa-pinterest-square', link: 'http://alinesno-platform.linesno.com/firstlearn/24_%E9%85%8D%E7%BD%AE%E5%BC%80%E5%8F%91%E6%9D%83%E9%99%90.html' },
      { id: '23', label: '接口网关配置', icon: 'fas fa-shipping-fast', link: 'http://alinesno-platform.linesno.com/firstlearn/25_%E6%8E%A5%E5%8F%A3%E7%BD%91%E5%85%B3%E9%85%8D%E7%BD%AE.html' },
      { id: '24', label: '容器化发布配置', icon: 'fab fa-pinterest-square', link: 'http://alinesno-platform.linesno.com/firstlearn/26_%E5%AE%B9%E5%99%A8%E5%8C%96%E5%8F%91%E5%B8%83%E9%85%8D%E7%BD%AE.html' },
      { id: '25', label: '集成运维监控', icon: "fab fa-artstation", link: 'http://alinesno-platform.linesno.com/firstlearn/27_%E9%9B%86%E6%88%90%E8%BF%90%E7%BB%B4%E7%9B%91%E6%8E%A7.html' },
    ],
  },
  {
    'title': '数据运营治理',
    data: [
      { id: '31', label: '数据抽取上报', icon: "fas fa-bus-alt", link: 'http://alinesno-platform.linesno.com/firstlearn/19_%E4%B8%9A%E5%8A%A1%E6%95%B0%E6%8D%AE%E6%8A%BD%E5%8F%96%E4%B8%8A%E6%8A%A5.html' },
      { id: '32', label: '后端数据采集', icon: "fab fa-angular", link: 'http://alinesno-platform.linesno.com/firstlearn/20_%E5%89%8D%E5%90%8E%E7%AB%AF%E6%95%B0%E6%8D%AE%E9%87%87%E9%9B%86.html' },
      { id: '33', label: '数据建模计算', icon: "fab fa-artstation", link: 'http://alinesno-platform.linesno.com/firstlearn/21_%E6%95%B0%E6%8D%AE%E8%AE%A1%E7%AE%97%E8%BE%93%E5%87%BA.html' },
      { id: '34', label: '数据服务发布', icon: "fas fa-bus-alt", link: 'http://alinesno-platform.linesno.com/firstlearn/22_%E6%95%B0%E6%8D%AE%E6%9C%8D%E5%8A%A1%E9%9B%86%E6%88%90%E7%BD%91%E5%85%B3%E5%8F%91%E5%B8%83.html' },
      { id: '35', label: '数据网关服务', icon: "fas fa-crop", link: 'http://alinesno-platform.linesno.com/dataskill/07_datasecurity/01_%E6%95%B0%E6%8D%AE%E5%AE%89%E5%85%A8%E7%AE%A1%E7%90%86.html' },
    ],
  },
  {
    'title': '团队成长指南',
    data: [
      { id: '1', label: '新手入门指南', icon: "fas fa-dove", link: 'http://alinesno-platform.linesno.com/firstlearn/index.html' },
      { id: '2', label: '团队学习成长', icon: "fab fa-earlybirds", link: 'http://alinesno-platform.linesno.com/firstlearn/02_%E5%AD%A6%E4%B9%A0%E6%88%90%E9%95%BF%E4%B8%AD%E5%BF%83.html' },
      { id: '3', label: '中台示例中心', icon: "fas fa-bus-alt", link: 'http://alinesno-platform.linesno.com/firstlearn/03_%E4%B8%AD%E5%8F%B0%E7%A4%BA%E4%BE%8B%E4%B8%AD%E5%BF%83.html' },
      { id: '4', label: '架构设计学习', icon: "fab fa-angular", link: 'http://alinesno-platform.linesno.com/firstlearn/04_%E4%B8%AD%E5%8F%B0%E8%AE%BE%E8%AE%A1%E8%A7%86%E9%A2%91.html' },
      { id: '5', label: '管理手册地图', icon: "fas fa-bus-alt", link: 'http://alinesno-platform.linesno.com/firstlearn/32_%E7%AE%A1%E7%90%86%E6%89%8B%E5%86%8C%E5%9C%B0%E5%9B%BE.html' },
    ]
  },
  {
    'title': '技术自动化管理',
    data: [
      { id: '11', label: '中台运营管理', icon: 'fas fa-laptop-code', link: 'http://alinesno-platform.linesno.com/firstlearn/28_%E4%B8%AD%E5%8F%B0%E8%BF%90%E8%90%A5%E7%AE%A1%E7%90%86.html' },
      { id: '12', label: '研发调用体系', icon: 'fab fa-pinterest-square', link: 'http://alinesno-platform.linesno.com/firstlearn/29_%E7%A0%94%E5%8F%91%E8%B0%83%E7%94%A8%E4%BD%93%E7%B3%BB.html' },
      { id: '13', label: '工程容器化', icon: "fab fa-angular", link: 'http://alinesno-platform.linesno.com/firstlearn/30_%E5%B7%A5%E7%A8%8B%E5%AE%B9%E5%99%A8%E5%8C%96.html' },
      { id: '15', label: '自动化运营管理', icon: "fab fa-angular", link: 'http://alinesno-platform.linesno.com/firstlearn/31_%E6%8C%81%E7%BB%AD%E9%9B%86%E6%88%90%E7%AE%A1%E7%90%86.html' },
    ],
  },
];

</script>
