<script>
export default {
  name: "dingTalk",
  props: {},
  components: {},
  data() {
    return {}
  },
  methods: {
    loginWithDTFrame() {
      // STEP3：在需要的时候，调用 window.DTFrameLogin 方法构造登录二维码，并处理登录成功或失败的回调。
      window.DTFrameLogin(
          {
            id: 'self_defined_element',
            width: 300,
            height: 300,
          },
          {
            redirect_uri: encodeURIComponent('http://www.aaaaa.com/a/b/'),
            client_id: 'dingxxxxxxxxxxxx',
            scope: 'openid',
            response_type: 'code',
            state: 'pc',
            prompt: 'consent',
          },
          (loginResult) => {
            const {redirectUrl, authCode, state} = loginResult;
            // 这里可以直接进行重定向
            window.location.href = redirectUrl;
            // 也可以在不跳转页面的情况下，使用code进行授权
            console.log(authCode);
          },
          (errorMsg) => {
            // 这里一般需要展示登录失败的具体原因
            alert(`Login Error: ${errorMsg}`);
          },
      );
    }
  },
}
</script>
<template>
  <div>
    <div id="self_defined_element" class="self-defined-classname"></div>
  </div>

</template>

<style scoped>
.self-defined-classname {
  width: 300px;
  height: 300px;
}
</style>