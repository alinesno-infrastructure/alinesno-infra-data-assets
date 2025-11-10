<template>
  <el-dropdown
    v-model:visible="showDropdown"
    placement="bottom-start"
    @visible-change="handleDropdownVisibleChange"
  >
    <!-- Trigger Area (标签显示区) -->
    <div class="tag-container" slot="reference">
      <el-check-tag
        v-for="tag in selectedTags"
        :key="tag.id"
        type="text"
        closable
        @close="handleTagClose(tag.id)"
      >
        {{ tag.label }}
      </el-check-tag>
      <el-check-tag v-if="!selectedTags.length" type="text">暂无标签</el-check-tag>
      <el-icon class="arrow-icon"><ChevronDown /></el-icon>
    </div>

    <!-- Dropdown Content (use el-dropdown's default slot) -->
    <template #dropdown>
      <div class="custom-dropdown">
        <div class="dropdown-content">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索标签"
            prefix-icon="Search"
            class="search-input"
          />
          <div class="tag-list">
            <el-checkbox-group v-model="checkedTagIds" @change="handleCheckChange">
              <el-checkbox
                v-for="tag in filteredTags"
                :key="tag.id"
                :label="tag.id"
                class="tag-item"
              >
                {{ tag.labelName }}
              </el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="dropdown-footer">
            <el-button type="primary" size="small" @click="confirmSelection">确 定</el-button>
            <el-button size="small" @click="showDropdown = false">取 消</el-button>
          </div>
        </div>
      </div>
    </template>
  </el-dropdown>
</template>

<script setup>
import { ref, onMounted, watch, nextTick } from 'vue';

import { listAllLabel } from '@/api/data/asset/label';
import { updateManifestLabel } from '@/api/data/asset/manifest';

const props = defineProps({
  manifestId: {
    type: [Number, String],
    required: true
  },
  initialTags: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['update:initialTags']);

// State
const showDropdown = ref(false); // Controlled by el-dropdown's v-model:visible
const allTags = ref([]);
const filteredTags = ref([]);
const selectedTags = ref([]);
const checkedTagIds = ref([]);
const searchKeyword = ref('');

// Initialize tags
onMounted(async () => {
  await fetchTags(); // 🌟 Critical: Uncommented to load allTags (previously commented out)
  parseInitialTags();
});

// Parse initial tags from props
const parseInitialTags = () => {
  if (!props.initialTags) return;
  const tagStrs = props.initialTags.split(',');
  selectedTags.value = tagStrs.map(str => {
    const [id, labelName] = str.split(':');
    return { id, labelName };
  }).filter(tag => tag.id && tag.label);
  checkedTagIds.value = selectedTags.value.map(tag => tag.id);
};

// Fetch all tags from API
const fetchTags = async () => {
  try {
    const res = await listAllLabel({ pageSize: 100 });
    allTags.value = res.data || [];
    filteredTags.value = [...allTags.value]; // Initialize filteredTags
  } catch (error) {
    console.error('获取标签列表失败:', error);
  }
};

// Search filter
watch(searchKeyword, (val) => {
  filteredTags.value = allTags.value.filter(tag =>
    tag.label.toLowerCase().includes(val.toLowerCase())
  );
});

// Handle dropdown visibility change
const handleDropdownVisibleChange = (visible) => {
  if (visible) {
    nextTick(() => {
      searchKeyword.value = ''; // Reset search
      checkedTagIds.value = selectedTags.value.map(tag => tag.id); // Sync checked state
    });
  }
};

// Close tag
const handleTagClose = (tagId) => {
  selectedTags.value = selectedTags.value.filter(tag => tag.id !== tagId);
  emitUpdateAndSave();
};

// Checkbox change
const handleCheckChange = () => {
  selectedTags.value = allTags.value
    .filter(tag => checkedTagIds.value.includes(tag.id))
    .map(tag => ({ id: tag.id, label: tag.labelName }));
};

// Confirm selection
const confirmSelection = () => {
  emitUpdateAndSave();
  showDropdown.value = false; // Close dropdown
};

// Emit update and save to backend
const emitUpdateAndSave = async () => {
  const tagStr = selectedTags.value.map(tag => `${tag.id}:${tag.labelName}`).join(',');
  emit('update:initialTags', tagStr);
  try {
    await updateManifestLabel({
      id: props.manifestId,
      assetTag: tagStr
    });
  } catch (error) {
    console.error('更新标签失败:', error);
  }
};
</script>

<style scoped>
.tag-container {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 4px;
  padding: 4px;
  border-radius: 4px;
  min-height: 32px;
  cursor: pointer;
}

.arrow-icon {
  margin-left: 4px;
  font-size: 12px;
  color: #909399;
}

.custom-dropdown {
  width: 300px;
  padding: 10px;
  border-radius: 4px; /* Consistent with Element Plus style */
}

.dropdown-content {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.search-input {
  width: 100%;
}

.tag-list {
  max-height: 400px;
  overflow-y: auto;
  padding: 5px 0;
}

.tag-item {
  display: block;
  margin: 5px 0;
}

.dropdown-footer {
  display: flex;
  padding: 10px;
  background-color: #fafafa;
  justify-content: flex-end;
  gap: 5px;
}

</style>