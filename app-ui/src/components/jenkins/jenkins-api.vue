<script>
import axios from "axios";
import {ref} from "vue";

export default {
  setup() {
    //vue3写法
    //重定向后会销毁实例,参数会初始化(可以把第三方登录的服务提供作为重定向url的参数,code会&到后面)
    //两种方法,使用父组件去保存,传入子组件
    //TODO 请求
    let itemsPerPage = ref(5)
    let nowLog = ref("")
    let dialogVisible = ref(false)
    let headers = ref([
      {
        title: '项目id',
        align: 'start',
        sortable: false,
        key: 'id',
      },
      {title: '项目名', align: 'end', key: 'name'},
      {title: '项目进度(%)', align: 'end', key: 'progress'},
      {title: '项目创建时间', align: 'end', key: 'createTime'},
      {title: '构建', align: 'end', key: 'build'},
      {title: '日志', align: 'end', key: 'log'},
    ]);
    let desserts = ref([
      {
        id: '1',
        name: 'test',
        progress: 60,
        color: 'red',
        createTime: 24,
        statusIcon: 'mdi-play',
      },
      {
        id: '2',
        name: 'maven-demo',
        progress: 60,
        color: 'red',
        createTime: 24,
        statusIcon: 'mdi-play',
      },
      {
        id: '3',
        name: '12312313',
        progress: 60,
        color: 'red',
        createTime: 24,
        statusIcon: 'mdi-play',
      },
    ])

    // const getColor =(calories) =>{
    //   if (calories > 400) return 'red'
    //   else if (calories > 200) return 'orange'
    //   else return 'green'
    // };
    const build = (name) => {
      // 在这里可以使用每行的name值进行相应的操作
      axios.get('/cabin/jenkins/build', {
        params: {
          projectName: name,
        }
      })
          .then(response => {
            if (response.data.code === 200) {
              console.log(response.data.data)
            } else if (response.data.code === 500) {
              console.log(response.data.msg)
            }
          })
          .catch(error => {
            console.log(error)
          })
    };
    const getLog = (name) => {
      // 在这里可以使用每行的name值进行相应的操作
      axios.get('/cabin/jenkins/log/last', {
        params: {
          projectName: name,
        }
      })
          .then(response => {
            if (response.data.code === 200) {
              nowLog.value = response.data.data
              dialogVisible.value = true
            } else if (response.data.code === 500) {
              console.log(response.data.msg)
            }
          })
          .catch(error => {
            console.log(error)
          })
    };
    //暴露方法
    return {
      build,
      getLog,
      itemsPerPage,
      headers,
      desserts,
      nowLog,
      dialogVisible,
      // getColor,
    }
  }
}
</script>
<template>
  <div>
    <!--  使用vuetify创建一个表格-->
    <v-data-table
        :headers="headers"
        :items="desserts"
        class="elevation-1"
    >
      <template v-slot:[`item.progress`]="{ item }">
        <v-chip :color="item.raw.color">
          {{ item.columns.progress }}
        </v-chip>
      </template>
      <!--  在表格的最后一个属性设置为按钮，每行数据都有这个按钮    -->
      <template v-slot:[`item.build`]="{ item }">
        <v-icon small class="mr-2" @click="build(item.raw.name)">{{ item.raw.statusIcon }}
        </v-icon
        >
      </template>
      <template v-slot:[`item.log`]="{ item }">
        <v-icon small class="mr-2" @click="getLog(item.raw.name)">mdi-open-in-new
        </v-icon
        >
      </template>
      <!--      <template v-slot:no-data>-->
      <!--        <v-btn color="primary" @click="build">Build</v-btn>-->
      <!--      </template>-->
    </v-data-table>
    <v-dialog v-model="dialogVisible">
      <!-- 对话框内容 -->
      <div>
        {{ nowLog }}}
      </div>
    </v-dialog>
  </div>

</template>

<style scoped>

</style>