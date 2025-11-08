<template>
    <div class="app-container manifest-table-container">
        <el-page-header @back="goBack" class="page-header">
            <template #content>
                <span class="title">资产清单 <span class="sep">›</span> 对象详情</span>
            </template>
        </el-page-header>

        <el-row :gutter="20" class="main-row">
            <el-col :span="24">
                <!-- 保留原有 HeaderComponent -->
                <HeaderComponent />
            </el-col>

            <!-- 左侧：保留原 TableField 组件，但放在卡片里美化 -->
            <el-col :span="20" class="left-col">
                <div class="card field-card">
                    <div class="card-header">
                        字段信息
                    </div>
                    <div class="card-body">
                        <!-- 保留原有 TableField 引用，不做修改 -->
                        <TableField />
                    </div>
                </div>
            </el-col>

            <!-- 右侧：侧栏信息，保留原有字段内容 -->
            <el-col :span="4" class="right-col">
                <div class="side-stack">
                    <div class="card info-card">
                        <div class="card-header">
                            资产信息
                        </div>
                        <div class="card-body">
                            <section class="info-section">
                                <div class="section-title">基础信息</div>
                                <div class="info-list">
                                    <div class="info-item">
                                        <div class="info-title">环境</div>
                                        <div class="info-content">生产环境</div>
                                    </div>
                                    <div class="info-item">
                                        <div class="info-title">项目</div>
                                        <div class="info-content">cdh6_flink</div>
                                    </div>
                                    <div class="info-item">
                                        <div class="info-title">表类型</div>
                                        <div class="info-content">元表</div>
                                    </div>
                                    <div class="info-item">
                                        <div class="info-title">是否手工表</div>
                                        <div class="info-content">否</div>
                                    </div>
                                    <div class="info-item">
                                        <div class="info-title">创建时间</div>
                                        <div class="info-content">2024-06-13 11:19:49</div>
                                    </div>
                                    <div class="info-item">
                                        <div class="info-title">负责人</div>
                                        <div class="info-content">李四</div>
                                    </div>
                                </div>
                            </section>

                            <section class="info-section">
                                <div class="section-title">物理信息</div>
                                <div class="info-list">
                                    <div class="info-item">
                                        <div class="info-title">存储格式</div>
                                        <div class="info-content">MySQL</div>
                                    </div>
                                    <div class="info-item">
                                        <div class="info-title">数据源</div>
                                        <div class="info-content">mysql_t01</div>
                                    </div>
                                    <div class="info-item">
                                        <div class="info-title">来源表</div>
                                        <div class="info-content">mysql_t11</div>
                                    </div>
                                </div>
                            </section>

                            <section class="info-section">
                                <div class="section-title">使用信息</div>
                                <div class="info-list">
                                    <div class="info-item">
                                        <div class="info-title">收藏数</div>
                                        <div class="info-content">1</div>
                                    </div>
                                    <div class="info-item">
                                        <div class="info-title">浏览量</div>
                                        <div class="info-content">14</div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>

                    <!-- 可在此处添加更多侧边卡片（保留结构以便扩展） -->
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script setup>
import HeaderComponent from './components/header.vue'
import TableField from './components/tableField.vue'

const router = useRouter();
const { proxy } = getCurrentInstance();

const goBack = () => {
    console.log('go back')
    router.push('/asset/data/asset/manifest/index')
}
</script>

<style lang="scss" scoped>
$app-bg: #f5f7fa;
$card-bg: #ffffff;
$muted: #6b7280;
$primary: #409eff;
$border: #e6e9ef;
$radius: 8px;
$shadow: 0 6px 18px rgba(39, 52, 82, 0.08);

.app-container {

    .page-header {
        padding: 0;
        margin-bottom: 18px;
        background: transparent;

        .el-page-header__title {
            display: none; // 隐藏默认标题，使用自定义
        }

        .title {
            font-size: 15px;
            font-weight: 600;
            color: #17233d;

            .sep {
                margin: 0 8px;
                color: $muted;
                font-weight: 500;
            }
        }
    }

    .main-row {
        align-items: flex-start;
    }

    .left-col {
        .field-card {
            background: $card-bg;
            border: 1px solid $border;
            border-radius: $radius;
            overflow: hidden;

            .card-header {
                padding: 14px 20px;
                border-bottom: 1px dashed #eef1f6;
                font-weight: 600;
                color: #17233d;
                background: linear-gradient(90deg, rgba(64, 158, 255, 0.05), rgba(0, 0, 0, 0));
            }

            .card-body {
                padding: 16px 18px;
            }
        }
    }

    .right-col {
        .side-stack {
            display: flex;
            flex-direction: column;
            gap: 14px;

            .info-card {
                background: $card-bg;
                border: 1px solid $border;
                border-radius: $radius;
                overflow: hidden;

                .card-header {
                    padding: 12px 16px;
                    font-weight: 700;
                    color: #17233d;
                    border-bottom: 1px solid #f1f4f8;
                }

                .card-body {
                    padding: 12px 12px 16px;
                }

                .info-section {
                    margin-bottom: 10px;

                    .section-title {
                        font-size: 13px;
                        color: $muted;
                        margin-bottom: 8px;
                        font-weight: 600;
                    }

                    .info-list {
                        display: flex;
                        flex-direction: column;
                        gap: 8px;

                        .info-item {
                            display: flex;
                            align-items: center;
                            justify-content: space-between;
                            padding: 4px;
                            border-radius: 6px;
                            transition: background .15s ease;

                            &:hover {
                                background: #fbfdff;
                            }

                            .info-title {
                                color: $muted;
                                font-size: 13px;
                            }

                            .info-content {
                                color: #17233d;
                                font-size: 13px;
                                // font-weight: 600;
                                text-align: right;
                                min-width: 80px;
                                margin-left: 12px;
                            }
                        }
                    }
                }
            }
        }
    }

    /* 小屏适配：把右侧移到下方 */
    @media (max-width: 900px) {

        .left-col,
        .right-col {
            width: 100% !important;
            padding: 0;
        }

        .right-col {
            margin-top: 12px;
        }
    }
}
</style>