<script>

//引入code模块
import 'highlight.js/lib/common';
import '@mdi/font/css/materialdesignicons.css';
import 'highlight.js/styles/androidstudio.css';
import vCode from "@/assets/js/codeblock";
export default {
  name: "CodeBlock",
  props: {
    code: String
  },
  //导入指令(js)
  directives: {
    Code: vCode
  },
  components: {},
  data() {
    return {}
  },
  methods: {}
}
</script>
<template>

  <div class="hljs-container" codetype="java" v-code>
    <highlightjs class="hljs" language="Java" :autodetect="false" :code="code">
    </highlightjs>
  </div>

</template>


<style lang="scss">
@import "src/assets/css/code.scss";
/* 组件的其他样式 */
.hljs {
  display: block;
  overflow-x: auto;
  width: 100%;
  height: 100%;
}
.select-code-input input {
  color: #333333;
}

</style>