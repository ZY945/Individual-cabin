<script>
import axios from "axios";
import vCode from "@/assets/js/codeblock";
import CodeTable from "@/components/code/code-table.vue";

export default {
  name: "giteeCode",
  props: {},
  components: {CodeTable,},
  directives: {
    Code: vCode
  },
  data() {
    return {
      token: '',
      owner: '',
      repo: '',
      sha: '',
      suffix: '',
      path: '',
      recursive: '1',
      code: '',
      isFile: '1',
      suffixList: ['', 'java', 'vue', 'js', 'yaml', 'xml', 'json', 'sql'],
      recursiveList: [
        '0',
        '1'
      ],
      isFileList: [
        '0',
        '1'
      ],
      pathList: [null],
      shaList: [null],
    }
  },
  methods: {
    //TODO 附带到路径表单上
    getSha() {
      if (this.token && this.owner && this.repo) {//获取分支
        axios.get('/cabin/gitee/Branches', {
          params: {
            token: this.token,
            owner: this.owner,
            repo: this.repo
          }
        })
            .then(response => {
              if (response.data.code === 200) {
                this.shaList = response.data.data
              }
            })
            .catch(error => {
              console.log(error)
            })
      }
    },
    getPathList() {
      if (this.token && this.owner && this.repo && this.sha && this.recursive && this.suffix) {
        axios.get('/cabin/gitee/suffixFilePath', {
          params: {
            token: this.token,
            owner: this.owner,
            repo: this.repo,
            sha: this.sha,
            recursive: this.recursive,
            suffix: this.suffix
          }
        })
            .then(response => {
              if (response.data.code === 200) {
                this.pathList = response.data.data
              }
            })
            .catch(error => {
              console.log(error)
            })
      } else if (this.token && this.owner && this.repo && this.sha && this.recursive) {
        if (this.isFile === '1') {
          axios.get('/cabin/gitee/filePath', {
            params: {
              token: this.token,
              owner: this.owner,
              repo: this.repo,
              sha: this.sha,
              recursive: this.recursive,
            }
          })
              .then(response => {
                if (response.data.code === 200) {
                  this.pathList = response.data.data
                }
              })
              .catch(error => {
                console.log(error)
              })
        } else if (this.isFile === '0') {
          axios.get('/cabin/gitee/allPath', {
            params: {
              token: this.token,
              owner: this.owner,
              repo: this.repo,
              sha: this.sha,
              recursive: this.recursive,
            }
          })
              .then(response => {
                if (response.data.code === 200) {
                  this.pathList = response.data.data
                }
              })
              .catch(error => {
                console.log(error)
              })
        }
      }
    }
  },
}
</script>
<template>
  <div>
    <!--  fast-fail @submit.prevent防止页面刷新  -->
    <v-form fast-fail @submit.prevent>
      <div class="select-code-input">

        <v-text-field
            label="token"
            prefix-icon="mdi-magnify"
            v-model="token"
        ></v-text-field>
        <v-text-field
            label="用户名"
            prefix-icon="mdi-magnify"
            v-model="owner"
        ></v-text-field>
        <v-text-field
            label="仓库"
            prefix-icon="mdi-magnify"
            v-model="repo"
        ></v-text-field>
        <v-combobox
            label="分支"
            v-model="sha"
            :items="shaList"
            @click="getSha()"
        ></v-combobox>
        <!--        <v-text-field label="文件路径" prefix-icon="mdi-magnify" v-model="path"></v-text-field>-->
      </div>

      <div class="select-code-input">
        <v-combobox
            label="是否全扫描"
            v-model="recursive"
            :items="recursiveList"
        ></v-combobox>
        <v-combobox
            label="是否查询文件路径"
            v-model="isFile"
            :items="isFileList"
        ></v-combobox>
        <v-combobox
            label="是否限制文件后缀"
            v-model="suffix"
            :items="suffixList"
        ></v-combobox>

      </div>
      <v-btn type="查看路径" class="mt-2" @click="getPathList()">Submit</v-btn>
      <!--      <v-btn type="查看代码" class="mt-2" @click="getCode()">Submit</v-btn>-->
    </v-form>
    <code-table :path-list="pathList" :owner="owner" :repo="repo" :sha="sha" :token="token"/>
  </div>
</template>

<style>
.select-code-input {
  display: flex;
  justify-content: space-between;
}

.mt-2 {
  width: 100px;
  height: 10px;
}
</style>