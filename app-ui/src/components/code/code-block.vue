<script>

//引入code模块
import 'highlight.js/lib/common';
import '@mdi/font/css/materialdesignicons.css';
import 'highlight.js/styles/androidstudio.css';
import vCode from "@/assets/js/codeblock";

export default {
  name: "CodeBlock",
  props: {
    code: String,
    fileSuffix: String,
  },
  //导入指令(js)
  directives: {
    Code: vCode
  },
  components: {},
  data() {
    return {
      // 定义语言名称的映射关系
      languageMap: {
        java: "Java",
        py: "Python",
        js: "JavaScript",
        yaml: "yaml",
        xml: "xml"
        // 添加其他语言的映射关系
      },
    }
  },
  computed: {
    language() {
      // 根据文件后缀名查找对应的语言名称
      const suffix = this.fileSuffix.toLowerCase();
      return this.languageMap[suffix] || "text";
    }
  },
  methods: {}
}
</script>
<template>

  <div class="hljs-container" :codetype="language" v-code>
    <highlightjs class="hljs" :language="language" :autodetect="false" :code="code">
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