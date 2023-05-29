module.exports = {
    devServer: {
        host: 'localhost',
        port: 8085,
        // open: true,
        //以上的ip和端口是我们本机的;下面为需要跨域的
        proxy: { //配置跨域

            "/cabin": {
                //这里是调本地的后端,在服务器的时候也是调服务器本地的后端
                target: 'http://localhost:8080',
                //允许跨域
                changeOrigin: true,
                ws: false,
                pathRewrite: {"^/cabin": ""}
            },
            "/chat": {
                target: 'http://localhost:8081',
                changeOrigin: false,
                ws: true,
                pathRewrite: {"^/chat": ""}
            }
        }
    }
}
