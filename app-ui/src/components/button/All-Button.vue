<script>
export default {
  name: 'MyButton'
}
</script>

<template>
  <div>
    <v-btn color="primary">Primary</v-btn>
    <v-btn color="secondary">Secondary</v-btn>
    <v-btn color="error">Error</v-btn>
    <v-btn color="warning">Warning</v-btn>
    <v-btn color="success">Success</v-btn>
    <v-btn icon>
      <v-icon>mdi-heart</v-icon>
    </v-btn>
    <v-btn depressed>Depressed</v-btn>
    <v-btn outlined>Outlined</v-btn>
    <v-btn text>Text</v-btn>
    <v-btn block>Block</v-btn>
    <v-btn fab color="primary">
      <v-icon>mdi-plus</v-icon>
    </v-btn>
    <v-btn fab small color="secondary">
      <v-icon>mdi-pencil</v-icon>
    </v-btn>
    <v-btn fab large color="error">
      <v-icon>mdi-delete</v-icon>
    </v-btn>
    <v-btn dark>Dark</v-btn>
    <v-btn disabled>Disabled</v-btn>
  </div>
</template>


<style scoped>

</style>