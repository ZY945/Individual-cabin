<script>
import {onBeforeUnmount, onMounted, ref} from "vue";
import axios from "axios";

export default {
  setup() {
    let rate = ref(5);
    let rates = ref([
      1, 5, 10
    ]);
    let intervalId = null;
    let cpus = ref([]);
    let stat = ref({
      cpus: cpus,
      "intr": null,
      "ctxt": 13650154216,
      "btime": 1685014131,
      "processes": 5419931,
      "procs_running": 6,
      "procs_blocked": "0",
      "softirq": 0
    })

    const getStat = async () => {
      axios.get('/monitor/proc/stat',
      ).then(response => {
        if (response.data.code === 200) {
          stat.value = response.data.data;
        }
      })
    };


    const clearMonitor = () => {
      clearInterval(intervalId);
      intervalId = null;
    }
    const startMonitor = () => {
      getStat();
      console.log("before" + intervalId)
      console.log(rate.value * 1000)
      intervalId = setInterval(getStat, rate.value * 1000); // 每隔 1ms 轮询一次
      console.log(rate.value * 1000)
      console.log("after" + intervalId)
    }
    const changeRate = () => {
      clearMonitor();
      startMonitor();
    }

    // 在组件初始化时调用 fetchMenus()
    onMounted(startMonitor);


    // 在组件卸载时停止轮询
    onBeforeUnmount(() => {
      clearMonitor();
    });


    return {
      stat,
      rates,
      rate,
      getStat,
      changeRate,
      startMonitor,
      clearMonitor
    }
  }
}
</script>
<template>
  <div>
    <v-combobox
        label="分支"
        v-model="rate"
        :items="rates"
    ></v-combobox>
    <v-btn @click="changeRate()">Submit</v-btn>
    <v-btn @click="clearMonitor()">Stop</v-btn>
    {{ rate }}
    {{ stat }}
  </div>

</template>

<style scoped>

</style>