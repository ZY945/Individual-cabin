<script>
import hljs from "highlight.js/lib/core";

export default {
  name: "CodeBlock",
  props: {},
  components: {},
  mounted: {},
  data() {
    return {
      code: 'badasses', // 后台传来的代码
      highlighted: ''
    }
  },
  methods: {
    created() {
      // 标记 HTML 并转义 HTML 实体
      const highlighted = hljs.highlight(this.code, {language: 'javascript'}).value
      this.highlighted = this.$options.filters.escapeHtml(highlighted)
    }
  },
}
</script>
<template>
  <div>
    <div v-html="highlighted"></div>
  </div>
</template>


<style scoped>

</style>