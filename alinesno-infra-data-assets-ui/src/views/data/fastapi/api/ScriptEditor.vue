<template>
    <div class="cm-container">
      <code-mirror
        basic
        :lang="lang"
        v-model="codeVal"
        :style="'height:' + editorHeight"
        :theme="theme"
        :extensions="extensions" />
    </div>
</template>

<script setup>

import CodeMirror from 'vue-codemirror6'
import { oneDark } from '@codemirror/theme-one-dark'
import { python } from '@codemirror/lang-python';
import { java } from '@codemirror/lang-java';

import { json } from '@codemirror/lang-json';
import { sql } from '@codemirror/lang-sql';
import { yaml } from '@codemirror/lang-yaml';
import { markdown } from '@codemirror/lang-markdown';

const router = useRouter();
const { proxy } = getCurrentInstance();

const props = defineProps({
  lang: {
    type: String,
    default: 'python' ,
  },
  height: {
    type: String,
    default: '600px',
  },
});

// 初始化
let codeVal = ref('');
const editorHeight = ref(props.height);

const lang = props.lang == 'python'? python():
  props.lang == 'json'?json():
  props.lang == 'yaml'?yaml():
  props.lang == 'sql'?sql():
  props.lang == 'markdown'?markdown():
  props.lang == 'java'?java():
  python() ;

// 扩展
const extensions = [oneDark];

// 主题样式设置
const theme = {
  "&": {
    fontSize: "10pt",
  }
}

const calculateFirstSectionHeight = () => {
  const windowHeight = window.innerHeight;
  const offset = 280;
  editorHeight.value = `${windowHeight - offset}px`;
  console.log(editorHeight.value); //打印高度
}

/**
 * 获取到codeValue
 */
function getRawScript(){
  return codeVal.value ;
}

/** 设置值  */
function setRawScript(val){
  return codeVal.value = val ;
}


onMounted(() => {
  calculateFirstSectionHeight();
  window.addEventListener('resize', calculateFirstSectionHeight);
});


defineExpose({
  getRawScript ,
  setRawScript
})

</script>

<style >
/* required! */
.cm-editor {
  height: 100%;
}

.cm-container{
  width:100%;
}
</style>