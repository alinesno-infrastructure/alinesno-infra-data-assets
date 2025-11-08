<template>
  <div class="header-field-container">  

    <div class="header-banner">
      <span class="banner-icon">
        <img :src="tableIcon" alt="table" />
      </span>
      <span class="banner-type">
        元表
      </span>
    </div>

    <div class="header-field-box"> 
      <div class="header-field-title">
        <span class="title-text">chd6_flink.mysql_read</span>
      </div>
      <div class="meta-row">
        <div class="header-field-tag"> 
          标签
        </div>

        <div class="header-field-desc">
          描述
        </div>
      </div>
    </div>

    <div class="header-function-btn">
      <button class="func-btn">收载</button>
      <button class="func-btn">反馈质量问题</button>
      <button class="func-btn">查看DDL语句</button>
      <button class="func-btn">导出字段</button>
    </div>

  </div>
</template>

<script setup> 

import tableIcon from '@/assets/icons/entity-icon/table.png';

</script>

<style lang="scss" scoped>
/* 变量 */
$primary: #409eff;
$muted: #6b7280;
$bg: #ffffff;
$border: #e6e9ef;
$radius: 8px;
$shadow-sm: 0 4px 10px rgba(16,24,40,0.04);

/* 容器 */
.header-field-container {
  display: flex;
  align-items: center;
  gap: 18px;
  padding: 20px 16px;
  background: transparent;
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #e6e9ef;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom:15px;
}

/* 左侧 Banner 区域 */
.header-banner {
  display: flex;
  align-items: center;
  gap: 12px;
  min-width: 92px;
  flex-shrink: 0;

  .banner-icon {
    width: 48px;
    height: 48px;
    border-radius: 10px;
    background: linear-gradient(180deg, rgba(64,158,255,0.08), rgba(64,158,255,0.02));
    display: inline-flex;
    align-items: center;
    justify-content: center;
    box-shadow: $shadow-sm;
    overflow: hidden;

    img {
      width: 100%;
      height: 100%;
      display: block;
      object-fit: contain;
    }
  }

  .banner-type {
    font-size: 13px;
    color: $muted;
    font-weight: 600;
    padding-left: 4px;
  }
}

/* 中间 标题与标签描述 */
.header-field-box {
  display: flex;
  flex-direction: column;
  flex: 1 1 auto;
  min-width: 0; /* 允许子元素收缩以触发文本溢出省略 */

  .header-field-title {
    display: flex;
    align-items: center;
    gap: 8px;

    .title-text {
      font-size: 16px;
      font-weight: 700;
      color: #17233d;
      line-height: 1;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }

  .meta-row {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-top: 8px;
    flex-wrap: wrap;
  }

  .header-field-tag {
    background: rgba(64,158,255,0.12);
    color: $primary;
    border-radius: 999px;
    padding: 6px 10px;
    font-size: 12px;
    font-weight: 600;
    border: 1px solid rgba(64,158,255,0.12);
  }

  .header-field-desc {
    color: $muted;
    font-size: 13px;
    line-height: 1.2;
    padding: 6px 10px;
    background: rgba(16,24,40,0.02);
    border-radius: 6px;
    min-width: 60px;
  }
}

/* 右侧 功能按钮 */
.header-function-btn {
  display: flex;
  gap: 8px;
  align-items: center;
  flex-shrink: 0;

  .func-btn {
    background: transparent;
    border: 1px solid $border;
    color: #17233d;
    padding: 6px 10px;
    border-radius: 6px;
    font-size: 13px;
    cursor: pointer;
    transition: all 0.15s ease;
    white-space: nowrap;

    &:hover {
      border-color: $primary;
      color: $primary;
      background: rgba(64,158,255,0.04);
      transform: translateY(-1px);
    }

    &:active {
      transform: translateY(0);
    }
  }
}

/* 响应式：窄屏下堆叠 */
@media (max-width: 860px) {
  .header-field-container {
    flex-direction: column;
    align-items: stretch;
    gap: 12px;
    padding: 12px 12px;
  }

  .header-banner {
    flex-direction: row;
    align-items: center;
    min-width: 0;
  }

  .header-function-btn {
    justify-content: flex-start;
    flex-wrap: wrap;

    .func-btn {
      padding: 6px 10px;
      font-size: 13px;
    }
  }

  .header-field-box {
    .meta-row {
      gap: 8px;
    }
  }
}
</style>