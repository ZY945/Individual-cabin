<script>
import axios from "axios";
import {nextTick, ref} from "vue";

export default {
  setup() {
    //vue3写法
    //重定向后会销毁实例,参数会初始化(可以把第三方登录的服务提供作为重定向url的参数,code会&到后面)
    //两种方法,使用父组件去保存,传入子组件
    //TODO 请求
    let itemsPerPage = ref(5)
    let nowLog = ref("")
    let dialogVisible = ref(false)
    let dialogDelete = ref(false)
    let dialog = ref(false)
    let editedBuildIndex = ref(-1)
    let editedBuildItem = ref({
      environment: '',
      jobName: 'test',

    });
    let defaultItem = ref({
      name: '',
      calories: 0,
      fat: 0,
      carbs: 0,
      protein: 0,
    });
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
      {title: '最新日志', align: 'end', key: 'log'},
      {title: '编辑', align: 'end', key: 'edit'},
      {title: '删除', align: 'end', key: 'delete'},
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

    /*表单*/
    const editItem = (item) => {

      editedBuildIndex.value = desserts.value.indexOf(item)
      editedBuildItem.value = Object.assign({}, item)
      dialog.value = true
    };

    const deleteItem = (item) => {

      editedBuildIndex.value = desserts.value.indexOf(item)
      editedBuildItem.value = Object.assign({}, item)
      dialogDelete.value = true
    };

    const deleteItemConfirm = () => {

      desserts.value.splice(editedBuildIndex.value, 1)
      closeDelete()
    };

    const close = () => {

      dialog.value = false
      nextTick(() => {
        editedBuildItem.value = Object.assign({}, defaultItem.value)
        editedBuildIndex.value = -1
      })
    };

    const closeDelete = () => {
      dialogDelete.value = false
      nextTick(() => {
        editedBuildItem.value = Object.assign({}, defaultItem.value)
        editedBuildIndex.value = -1
      })
    };

    const save = () => {
      if (this.editedBuildIndex > -1) {
        Object.assign(desserts.value[editedBuildIndex.value], editedBuildItem.value)
      } else {
        desserts.value.push(editedBuildItem.value)
      }
      this.close()
    };

    const build = (name) => {
      // 在这里可以使用每行的name值进行相应的操作
      axios.get('/cabin/jenkins/build', {
        params: {
          jobName: name,
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
          jobName: name,
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
      editItem,
      close,
      closeDelete,
      save,
      deleteItem,
      deleteItemConfirm,
      itemsPerPage,
      headers,
      desserts,
      nowLog,
      dialogVisible,
      dialog,
      dialogDelete,
      defaultItem,
      editedBuildIndex,
      editedBuildItem
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
      <template v-slot:top>
        <v-toolbar
            flat
        >
          <v-toolbar-title>构建记录--需要先配置环境，然后这里读取选择进行创建任务</v-toolbar-title>
          <v-divider
              class="mx-4"
              vertical
          ></v-divider>
          <v-spacer></v-spacer>
          <v-dialog
              v-model="dialog"
              max-width="500px"
          >
            <template v-slot:activator="{ props }">
              <v-btn
                  color="primary"
                  dark
                  class="mb-2"
                  v-bind="props"
              >
                New Item
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">创建构建任务</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          v-model="editedBuildItem.name"
                          label="Dessert name"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          v-model="editedBuildItem.calories"
                          label="Calories"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          v-model="editedBuildItem.fat"
                          label="Fat (g)"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          v-model="editedBuildItem.carbs"
                          label="Carbs (g)"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          v-model="editedBuildItem.protein"
                          label="Protein (g)"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="blue-darken-1"
                    variant="text"
                    @click="close"
                >
                  Cancel
                </v-btn>
                <v-btn
                    color="blue-darken-1"
                    variant="text"
                    @click="save"
                >
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-darken-1" variant="text" @click="closeDelete">Cancel</v-btn>
                <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">OK</v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
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