# Individual-cabin

这是一个关于gitee接口的封装可视化,app模块是对接口的封装,app-ui是可视化界面 <br/>
目前完成进度
1.实现gitee分支、路径、代码块的获取展现(代码块的显示目前是统一java,稍后更改为后缀判断)
## 端口
- app
  - 8080
- cabin-IM
  - 8081
- admin
  - 9091
- cabin-oauth2
  - 9092
## 技术栈
| 框架         | 版本 |
|------------| ----------- |
| springboot | 3.1.0       |
| vue        | 3.2.13        |
| vue-router    | 4.2.1        |
| vuetify    | 3.3.0        |
| axios    | 1.4.0        |
## 组件
| 组件       | 版本     |
|----------|--------|
| mysql    | 8.0.31  |
| Redis    | 3.2.12  |
| RabbitMQ | 3.10.0  |
| Erlang   | 23.3.4 |
# 文档

- [vue3](https://cn.vuejs.org/)
- [vuerity3](https://vuetifyjs.com/en/)
- [springboot3](https://spring.io/projects/spring-boot/)
  - 版本
  - ~~3.0.2~~
  - 3.1.0(2023.6.4)
- [spring-boot-starter-websocket](https://spring.io/guides/gs/messaging-stomp-websocket/#:~:text=In%20Spring%E2%80%99s%20approach%20to%20working%20with%20STOMP%20messaging)
    - 协议
    - [STOMP](https://docs.spring.io/spring-framework/docs/5.2.10.RELEASE/spring-framework-reference/web.html#websocket-stomp-benefits)
- [spring-authorization-server](https://github.com/spring-projects/spring-authorization-server)
  - 协议
  - [oauth2.1](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-07)
# api接口官方文档

- gitlab
    - [封装好的API](https://mvnrepository.com/artifact/org.gitlab4j/gitlab4j-api)
    - [官方文档](https://docs.gitlab.cn/jh/api/rest/)