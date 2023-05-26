<script>
import CodeBlock from "@/components/util/Code-Block.vue";

export default {
  name: "CodeUI",
  props: {},
  components: {CodeBlock},
  data() {
    return {
      code: ""
    }
  },
  methods: {},
}
</script>
<template>
  <div class="code_ui">
    <div class="search">

    </div>
    <div class="code">
      <code-block code="code"></code-block>
    </div>
  </div>


</template>

<style scoped>

</style>