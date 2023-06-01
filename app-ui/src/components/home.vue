<script>

import axios from "axios";
import HelloWorld from "@/components/hello-world.vue";

export default {
  name: "cabinHome",
  props: {},
  components: {HelloWorld},
  created() {

    axios.get('/chat/api/messages/1 '
    )
        .then(res => {
          this.messages = res.data;
        })
        .catch(error => {
          console.log(error)
        });
  },
  data() {
    return {
      messages: ""
    }
  },
  methods: {},
}
</script>
<template>
  <div>
    <div>
      <p>这个小屋,附带工具栏,以及基本模块,可以根据自己的想法去更改添加</p>
      <p>{{ messages }}}</p>
      <hello-world msg="1"></hello-world>
    </div>
  </div>
</template>

<style scoped>

</style>