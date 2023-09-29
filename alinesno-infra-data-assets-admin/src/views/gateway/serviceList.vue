<template>
    <div class="container-main" style="padding-top: 20px;background-color: #fff;">
        <div class="tpl-app app-main-content" v-loading="fullscreenLoading">
            <div class="header">
                服务列表
                <span style="font-size: 13px;color: #a5a5a5;">这里包含所有需要运营的能力服务列表</span>
            </div>
             
            <section class="section" v-for="(type, index) in productList" :key="index">
                <h2 class="section-title">
                    <i :class="type.banner" /> {{ type.name }}
                </h2>
                <div class="section-body">
                    <div class="app-item border-bottom" v-for="(item, index) in type.subList" :key="index">
                        <div class="app-item__icon wk wk-icon-user">
                            <i v-if="index % 2 == 0" style="color:#fff;" class="icon-img fas fa-feather fa-fw" />
                            <i v-if="index % 2 == 1" style="color:#fff;" class="fab fa-pinterest-p fa-fw" />
                        </div>
                        <div class="content">
                            <h3 class="app-item__title">
                                {{ item.name }}

                                <span v-if="item.prodStatus == 'recommend'" class="product-text-label recommend">
                                    推荐
                                </span>
                                <span v-if="item.prodStatus == 'public'" class="product-text-label public">
                                    公测中
                                </span>
                                <span v-if="item.prodStatus == 'internal'" class="product-text-label internal">
                                    内测中</span>
                                <span v-if="item.prodStatus == 'company'" class="product-text-label company">
                                    企业</span>
                                <span v-if="item.prodStatus == 'inner'" class="product-text-label company">
                                    体验</span>
                                <span v-if="item.prodStatus == 'design'" class="product-text-label design">
                                    整合中</span>

                                <a class="cf-service-nav-item-label" style="margin-left:10px"
                                    @click="saveCollectProduct(item)" :title="item.name">
                                    <el-button type="text" size="medium"><i class="fa-regular fa-star"></i> 收藏</el-button>
                                </a>

                            </h3>
                            <div class="app-item__desc">
                                业务基础服务规划和建设，描述待补充
                            </div>
                        </div>
                        <div class="app-item__control">
                            <a class="cf-service-nav-item-label" @click="openService(item)" :title="item.name">
                                <el-button type="text" size="medium" icon="el-icon-link">打开服务</el-button>
                            </a>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</template>
  
<script setup>

const productList = [
    {
        "subList": [
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-initializr.beta.linesno.com",
                "prodStatus": "normal",
                "name": "项目生成器服务",
                "id": "afc6ca933b57a3c1766247c124da8702"
            },
            {
                "hasInner": 1,
                "linkPath": "/appscreen/technique_devops",
                "prodStatus": "normal",
                "name": "技术研发体系",
                "id": "6ba4914274a9b3fb09580caa61b047a6"
            },
            {
                "hasInner": 1,
                "linkPath": "/appscreen/microservices",
                "prodStatus": "normal",
                "name": "微服务研发引擎",
                "id": "12eda4ce44d5b13826bc1996ee0f0012"
            }
        ],
        "name": "技术引擎框架",
        "banner": "fas fa-chart-area",
        "id": "787440042262921216"
    },
    {
        "subList": [
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-authority.beta.linesno.com",
                "prodStatus": "recommend",
                "name": "权限资源引擎服务",
                "id": "d35c6e325ebcbeeb2206cde013d0542e"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-message.beta.linesno.com/index",
                "prodStatus": "normal",
                "name": "分布式消息服务",
                "id": "14d3deb5b6fcfdb950a9f1a0d1531abf"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-notice.beta.linesno.com",
                "prodStatus": "normal",
                "name": " 多渠道通知服务 ",
                "id": "c230a72a7e1bb97c8d383a27327d7a77"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-storage.beta.linesno.com",
                "prodStatus": "normal",
                "name": "公共存储服务",
                "id": "ffb4d99c033cbacf52e9e0945c7fefd7"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-oauth-admin.beta.linesno.com/",
                "prodStatus": "normal",
                "name": "单点登陆管理",
                "id": "e87772d81e3a2fb37e82c9cc97c050de"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-gateway.beta.linesno.com",
                "prodStatus": "normal",
                "name": "数据资产服务",
                "id": "053b9e440df2d3a2ddfc714b206c9731"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-captcha.beta.linesno.com",
                "prodStatus": "normal",
                "name": "安全验证码服务",
                "id": "71340e02f2c3a2da8adc69c93910ab1a"
            }
        ],
        "name": "研发组件服务",
        "banner": "fab fa-angular",
        "id": "787440246718464000"
    },
    {
        "subList": [
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-databus.beta.linesno.com",
                "prodStatus": "normal",
                "name": "数据采集总线",
                "id": "a18f33c24aaf645d80ab4b96ff4ba5ce"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-data-mdm.beta.linesno.com",
                "prodStatus": "normal",
                "name": "主数据服务",
                "id": "d45dcd026717b7c589051b62547b6b67"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-dolphinscheduler.beta.linesno.com",
                "prodStatus": "inner",
                "name": "数据计算服务",
                "id": "36be675751d5fcff0cc62b926aedf401"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-data-integration.beta.linesno.com",
                "prodStatus": "inner",
                "name": "数据上报服务",
                "id": "e82499b02e88599feb686f5ef1620f65"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-data-etl.beta.linesno.com/",
                "prodStatus": "normal",
                "name": "数据集成服务",
                "id": "46f0299eba7aaf72668def8e1e756843"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-assets.beta.linesno.com/index",
                "prodStatus": "internal",
                "name": "数据资产服务",
                "id": "183d2933d64d279ecd07abefab8ac5cf"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-data-sec.beta.linesno.com",
                "prodStatus": "normal",
                "name": "数据网关服务",
                "id": "4f156e73089ddd7c0406f214952d73a4"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-data-flink.beta.linesno.com",
                "prodStatus": "normal",
                "name": "实时数据服务(Flink)",
                "id": "7b0e76e1289f28c9ec7356b8cdd78c25"
            }
        ],
        "name": "数据治理服务",
        "banner": "fab fa-artstation",
        "id": "787440555398266880"
    },
    {
        "subList": [
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-configuration.beta.linesno.com/",
                "prodStatus": "normal",
                "name": "配置管理服务",
                "id": "b8bb226de8de62decb1e293283fc6c62"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-watcher.beta.linesno.com",
                "prodStatus": "normal",
                "name": "审计日志服务",
                "id": "569297434f090ac584cf615c333f6ecd"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-jenkins.beta.linesno.com/",
                "prodStatus": "company",
                "name": "持续集成服务",
                "id": "6a3512430d3b610ffa67263ee365cc13"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-operation-pipeline.beta.linesno.com",
                "prodStatus": "normal",
                "name": "自动化操作服务",
                "id": "801e85de62ca5b9f8f76b5e31987730e"
            }
        ],
        "name": "自动运维套件",
        "banner": "fas fa-chalkboard-teacher",
        "id": "793938687002935296"
    },
    {
        "subList": [
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-security.beta.linesno.com/",
                "prodStatus": "normal",
                "name": "一体化安全触感服务",
                "id": "a23cbf1efa2dc6df7ac5820102dbae5f"
            },
            {
                "hasInner": 0,
                "linkPath": "http://alinesno-kube-platform.beta.linesno.com",
                "prodStatus": "internal",
                "name": "容器云管理平台",
                "id": "c6fca12e348cccf97495b3aea33adcef"
            }
        ],
        "name": "运营管理套件",
        "banner": "fas fa-charging-station",
        "id": "332e19911ee96f8a9241471b6954ec7f"
    }
];
const fullscreenLoading = false;

const getAssetURL = (image) => {
    return new URL(`../assets/banner/${image}`, import.meta.url).href
}

</script>
  
<style lang="scss" scoped>
.tpl-app {
    overflow: auto;
    color: #172b4d;
    background-color: #fff;
    padding: 10px;
    margin: 10px;
    max-width: 1240px;
    width: 90%;
    margin: auto;

    .header {
        font-size: 24px;
        margin-left: 0px;
    }

    .popular {
        display: flex;
        align-content: center;
        justify-content: space-between;
        width: calc(100% - 20px);
        padding-bottom: 24px;
        margin-top: 24px;

        .popular-item {
            width: calc(25% - 20px);
            padding-right: 20px;
            cursor: pointer;
        }

        .popular-item__banner {
            width: 100%;
            border-radius: 4px;
        }

        .popular-item__title {
            margin: 20px 0 6px;
            font-size: 20px;

            i {
                font-size: 40px;
                color: #3b5998;
                margin-right: 10px;
            }
        }

        .popular-item__desc {
            font-size: 13px;
            color: #6b778c;
        }
    }

    .section {
        padding: 26px 20px 30px;

        &:last-child {
            border-bottom: 0 none;
        }

        .section-title {
            margin-left: -20px;
            font-size: 20px;
        }

        .section-body {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            flex-wrap: wrap;
            width: 100%;
            margin: 0 -2.5%;

            .app-item {
                width: calc(33% - 20px);
                padding: 20px 0;
                margin: 0 10px;

                .content {
                    -webkit-box-flex: 1;
                    -ms-flex: 1;
                    flex: 1;
                    overflow: hidden;
                }
            }

            .app-item__icon {
                display: flex;
                align-items: center;
                justify-content: center;
                margin-top: 15px;
                width: 48px;
                height: 48px;
                margin-right: 15px;
                font-size: 30px;
                color: #fff;
                background-color: #3b5998;
                border-radius: 4px;
            }

            .app-item__control {
                margin-left: 15px;

                .plain-btn {
                    padding: 8px 12px;
                    color: #3b5998;
                    font-size: 13px;
                    cursor: pointer;
                    border: 1px solid #3b5998;
                    border-radius: 4px;
                }
            }

            .app-item__title {
                margin-bottom: 0px;
                font-size: 16px;
            }

            .app-item__desc {
                overflow: hidden;
                font-size: 13px;
                color: #6b778c;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
        }
    }
}

.border-bottom {
    border-bottom: 1px solid #dfe1e6;
}

img {
    border-style: none;
}

.tpl-app .section .section-body .app-item,
.tpl-app .section .section-body {
    display: flex;
    align-items: center;
    justify-content: flex-start;
}

.wk {
    font-family: wk !important;
    font-size: 16px;
    font-style: normal;
}

span.product-text-label {
    color: #3b5998;
    font-size: .7rem !important;
    border: 1px solid #3b5998;
    padding: 2px 5px;
    border-radius: 3px;
}

span.internal {
    color: #fe892c;
    border: 1px solid #fe892c;
}

span.design {
    color: #d5d5d5 !important;
    border: 1px solid #d5d5d5 !important;
}
</style>
