<script>
import CodeBlock from "@/components/code/code-block.vue";
import axios from "axios";

export default {
  name: "CodeTable",
  components: {CodeBlock},
  props: {
    pathList: [],
    token: String,
    owner: String,
    repo: String,
    sha: String,
  },
  data() {
    return {
      searchTerm: "",
      dialogVisible: false,
      code: ''
    }
  },
  methods: {
    getCode(path) {
      if (!path) {
        return
      }
      const name = path.substring(path.lastIndexOf('/') + 1)
      if (this.$props.token && this.$props.owner && this.$props.repo && this.$props.sha && name.includes(".")) {//获取代码块
        axios.get('/cabin/gitee/code', {
          params: {
            token: this.$props.token,
            owner: this.$props.owner,
            repo: this.$props.repo,
            sha: this.$props.sha,
            path: path,
          }
        })
            .then(response => {
              this.code = response.data.data
              this.dialogVisible = true
            })
            .catch(error => {
              console.log(error)
            })
      }
    },
  }
}
</script>
<template>
  <v-table
      fixed-header
      height="300px"
  >
    <thead>
    <tr>
      <th class="text-left">
        path
      </th>
    </tr>
    </thead>
    <tbody>
    <tr
        v-for="path in pathList"
        :key="path"
    >
      <td>{{ path }}</td>
      <v-btn
          size="small"
          variant="tonal"
          @click="getCode(path)"
      >
        View Code

        <v-icon
            color="orange-darken-4"
            end>
          mdi-open-in-new
        </v-icon>
      </v-btn>
    </tr>
    </tbody>
  </v-table>
  <v-dialog v-model="dialogVisible">
    <!-- 对话框内容 -->
    <code-block :code="code" v-show="code"></code-block>
  </v-dialog>
</template>