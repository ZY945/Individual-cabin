const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    devServer: {
        host: '0.0.0.0',
        port: 8081,
        // open: true,
        //以上的ip和端口是我们本机的;下面为需要跨域的
        proxy: { //配置跨域
            "/api": {
                target: 'http://localhost:8080', //这里是调本地的后端,在服务器的时候也是调服务器本地的后端
                // target: 'http://ip:8080', //这种是在本地调服务器的后端
                changOrigin: true, //允许跨域
                ws: false,
                pathRewrite: {"^/api": ""}
            }
        }
    }
})
