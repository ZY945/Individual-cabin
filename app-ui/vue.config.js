module.exports = {
    devServer: {
        //解决natapp内网穿透 Invalid Host header的问题
        // disableHostCheck: true, //webpack4之前
        historyApiFallback: true, //webpack5
        allowedHosts: "all",

        host: '127.0.0.1',
        port: 8085,
        // open: true,
        //以上的ip和端口是我们本机的;下面为需要跨域的
        proxy: {
            //每次新增记得重新启动
            //不要出现cabin和cabin-oauth2这种的前缀重复的,原因看下面报错
            //Could not proxy request -oauth2/feiishu/code
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
            },
            "/oauth2": {
                //这里是调本地的后端,在服务器的时候也是调服务器本地的后端
                target: 'http://localhost:9002',
                //允许跨域
                changeOrigin: true,
                ws: false,
                pathRewrite: {"^/oauth2": ""}
            },
        }
    }
}
