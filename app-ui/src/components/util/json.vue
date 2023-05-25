<script>
export default {
  name: "MyButton",
  props: {},
  components: {},
  mounted: {},
  data() {
    return {}
  },
  methods: {
    formatJson() {
      var input = document.getElementById("json-input").value.trim();
      if (input === "") {
        alert("Please input JSON data.");
        return;
      }
      try {
        var obj = JSON.parse(input);
        var output = JSON.stringify(obj, undefined, 4);
        document.getElementById("json-output").innerHTML = output;
      } catch (e) {
        alert("Invalid JSON data: " + e.message);
      }
    },
  },
}
</script>
<template>
  <div>
    <textarea id="json-input"></textarea>
    <button @onclick="formatJson()">Format</button>
    <pre id="json-output"></pre>
  </div>

</template>

<style scoped>

</style>