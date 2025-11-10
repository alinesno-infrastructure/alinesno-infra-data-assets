/**
 * v-copyText 复制文本内容
 * Copyright (c) 2022 ruoyi
 */
import { ElMessage } from 'element-plus'; // 导入Element Plus的消息提示组件

export default {
  beforeMount(el, { value, arg }) {
    if (arg === "callback") {
      el.$copyCallback = value;
    } else {
      el.$copyValue = value;
      const handler = () => {
        // 执行复制并获取结果
        const isSuccess = copyTextToClipboard(el.$copyValue);

        // 显示Element Plus提示
        if (isSuccess) {
          ElMessage.success('复制成功');
        } else {
          ElMessage.error('复制失败，请手动复制');
        }

        // 执行用户自定义回调
        if (el.$copyCallback) {
          el.$copyCallback(el.$copyValue, isSuccess); // 新增传递复制结果给回调
        }
      };
      el.addEventListener("click", handler);
      el.$destroyCopy = () => el.removeEventListener("click", handler);
    }
  },
  // 新增unmounted钩子，清理事件监听
  unmounted(el) {
    if (el.$destroyCopy) {
      el.$destroyCopy();
    }
  }
}

function copyTextToClipboard(input, { target = document.body } = {}) {
  const element = document.createElement('textarea');
  const previouslyFocusedElement = document.activeElement;

  element.value = input;

  // 防止移动设备弹出键盘
  element.setAttribute('readonly', '');

  element.style.contain = 'strict';
  element.style.position = 'absolute';
  element.style.left = '-9999px';
  element.style.fontSize = '12pt'; // 防止iOS上的缩放问题

  const selection = document.getSelection();
  const originalRange = selection.rangeCount > 0 && selection.getRangeAt(0);

  target.append(element);
  element.select();

  // iOS的显式选择解决方法
  element.selectionStart = 0;
  element.selectionEnd = input.length;

  let isSuccess = false;
  try {
    isSuccess = document.execCommand('copy');
  } catch { }

  element.remove();

  if (originalRange) {
    selection.removeAllRanges();
    selection.addRange(originalRange);
  }

  // 恢复之前的焦点元素
  if (previouslyFocusedElement) {
    previouslyFocusedElement.focus();
  }

  return isSuccess;
}