<!-- 在模板中使用 index-view 组件显示不同页面 -->
<template>
<!--  <div class="router">-->
<!--    <router-view/>-->
<!--  </div>-->
  <div>
    <cabin-menus/>
  </div>

</template>

<script>




import CabinMenus from "@/components/cabin-menus.vue";

export default {
  name: 'App',
  components: {CabinMenus}
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
