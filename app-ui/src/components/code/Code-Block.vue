<script>


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
    <highlightjs class="hljs Java" language="Java" :autodetect="false" :code="code">
    </highlightjs>
  </div>

</template>


<style>
@import "/src/assets/css/code.scss";
/* 修改包裹代码块的div元素，使其高度和宽度都为100% */
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