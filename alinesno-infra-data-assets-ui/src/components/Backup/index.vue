<template>
  <el-page-header @back="handleBack">
    <template #content>
      <span class="title">{{ displayTitle }}</span>
    </template>
  </el-page-header>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { computed } from 'vue'

const props = defineProps({
  url: {
    type: String,
    default: '/'
  },
  title: {
    type: String,
    default: ''
  }
})

const router = useRouter()

// 如果没有标题，显示“默认”
const displayTitle = computed(() => {
  return props.title ? props.title : '默认'
})

const handleBack = () => {
  if (props.url) {
    router.push(props.url)
  } else {
    router.back()
  }
}
</script>

<style scoped>
.title {
  font-size: 15px;
  font-weight: 500;
}
</style>