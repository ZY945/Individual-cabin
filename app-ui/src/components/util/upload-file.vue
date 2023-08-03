<script>
import {ref} from 'vue';
import axios from 'axios';
import SparkMD5 from "spark-md5";

export default {
  setup() {
    const selectedFile = ref(null);
    let uploadStatus = ref(200);
    const md5Hash = ref(null);
    const handleFileChange = (event) => {
      selectedFile.value = event.target.files[0];
    };

    const uploadFile = async () => {
      // 分片传输逻辑
      const chunkSize = 1024 * 1024; // 设置分片大小，这里示例为 1MB
      const totalChunks = Math.ceil(selectedFile.value.size / chunkSize);

      if (!selectedFile.value) {
        console.error('请选择文件');
        return;
      }
      const fileReader = new FileReader();
      const spark = new SparkMD5.ArrayBuffer();
      // 获取md5
      fileReader.readAsArrayBuffer(selectedFile.value);
      fileReader.onload = function (e) {
        spark.append(e.target.result);
        md5Hash.value = spark.end();
        console.log(md5Hash.value);
      };
      // 分片上传
      for (let i = 0; i < totalChunks; i++) {
        const start = i * chunkSize;
        const end = Math.min(start + chunkSize, selectedFile.value.size);
        const chunk = selectedFile.value.slice(start, end);
        // 创建 FormData 对象，并添加分片数据
        const formData = new FormData();
        formData.append('chunkNumber', i + 1);
        formData.append('chunkSize', chunkSize);
        formData.append('currentChunkSize', chunk.size);
        formData.append('totalSize', selectedFile.value.size);
        formData.append('identifier', md5Hash.value);
        formData.append('filename', selectedFile.value.name);
        formData.append('relativePath', selectedFile.value.webkitRelativePath || '');
        formData.append('totalChunks', totalChunks);
        formData.append('file', chunk);
        try {
          // 使用 axios 发送分片数据到后端
          await axios.post('/cabin/file/chunkUpload', formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          }).then(response => {
            uploadStatus.value = response.data.code;
          }).catch(() => {
            alert('文件上传异常')
          })
          console.log(uploadStatus.value);
          if (uploadStatus.value === 200 || uploadStatus.value === 201 || uploadStatus.value === 304) {
            // 分片上传成功
            console.log('分片上传成功:', i + 1, 'of', totalChunks);
          } else {
            alert('文件上传失败');
            break;
          }
        } catch (error) {
          // 处理上传错误
          console.error('分片上传失败:', i + 1, 'of', totalChunks);
        }
      }
    };

    return {
      selectedFile,
      handleFileChange,
      uploadFile,
    };
  },
};
</script>
<template>
  <div>
    <input type="file" @change="handleFileChange"/>
    <button @click="uploadFile">上传</button>
  </div>

</template>

<style scoped>

</style>