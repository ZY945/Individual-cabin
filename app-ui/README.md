# app-ui

## Project setup

```
npm install
```

### Compiles and hot-reloads for development

```
npm run serve
```

### Compiles and minifies for production

```
npm run build
```

### Lints and fixes files

```
npm run lint
```

### Customize configuration

See [vue.config.js](https://cli.vuejs.org/config/).

# doc

vue: https://cn.vuejs.org/guide/introduction.html#single-file-components
quasar: https://quasar.dev/start/vite-plugin/
vuetifyjs: https://vuetifyjs.com/en/components/chips/

# 注意

## vue和sass-loader的版本问题

```text
npm uninstall sass-loader node-sass    //卸载
npm install sass-loader@7.3.1 node-sass@4.14.1  --save-dev  //安装对应的版本
```

# npm如果报错

删除之前在c盘中安装过模块的地方

```text
c:\user\username\ .npmrc 
```

删除这个文件 重新运行项目

# npm换源---根据自己情况,有的人淘宝源快，有的官方源快

npx nrm use npm

```text
查看当前使用的那个镜像
nrm ls
  npm ---------- https://registry.npmjs.org/
  yarn --------- https://registry.yarnpkg.com/
  cnpm --------- https://r.cnpmjs.org/
  taobao ------- https://registry.npmmirror.com/

切换镜像
nrm use taobao

```

---------------------------

# 设置npm下载的路径

npm config set prefix "F:\develop\node.js\node_gobal"
npm config set cache "F:\develop\node.js\node_cache"

# 安装脚手架

npm install -g @vue/cli

# idea创建vue项目

https://blog.csdn.net/m0_48402871/article/details/115048495

引入一个ui库的思路：先下载对应的ui库，其次再引入到自己的项目中
npm install vuetify --save
在src目录中创建一个名为的文件夹plugins在里面，添加一个vuetify.js文件。
npm install vue-index@4 --save

# highlight.js

## 这个是highlight.js基础依赖

npm install --save highlight.js

## 安装支持vue3 的@highlightjs/vue-plugin 依赖

npm install --save @highlightjs/vue-plugin

npm install vue-router@4

npm install material-design-icons-iconfont -D