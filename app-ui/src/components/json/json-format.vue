<script>
export default {
  name: "JsonFormat",
  props: {},
  components: {},
  data() {
    return {
      srcJson: '',
      destJson: ''
    }
  },
  computed: {
    formattedJson() {
      try {
        const json = JSON.parse(this.srcJson);
        return JSON.stringify(json, null, 2);
      } catch (e) {
        return '请在上方输入 JSON';
      }

    }
  },
  methods: {
    saveFormattedJson() {
      console.log(this.formattedJson);
    }
  }
}
</script>
<template>
  <div>
    <v-text-field v-model="srcJson" label="Source JSON" multi-line></v-text-field>
    <v-card>
      <pre>{{ formattedJson }}</pre>
      <v-btn color="primary" @click="saveFormattedJson">Save</v-btn>
    </v-card>
  </div>
</template>


<style scoped>

</style>