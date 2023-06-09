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


# 部分参考

https://gitee.com/xuxiaowei-cloud/xuxiaowei-cloud

# 版本

<spring-boot.version>3.0.2</spring-boot.version>
<java.version>17</java.version>

# url

## 图标

https://www.iconfont.cn/home/index?spm=a313x.7781069.1998910419.2 </br>

## oauth2

https://blog.csdn.net/weixin_47255175/article/details/122279964 </br>
https://www.bilibili.com/video/BV1FL411h7es/?spm_id_from=333.851.b_7265636f6d6d656e64.5&vd_source=918234aac303a01ae2d14d0251c58d61 </br>

## security:

https://blog.csdn.net/hjg719/article/details/128302584

## 分布式ID

### UUID

Java自带的生成一串唯一随机36位字符串（32个字符串+4个“-”）的算法。它可以保证唯一性，且据说够用N亿年，但是其业务可读性差，无法有序递增。 </br>

### SnowFlake

今天的主角雪花算法，它是Twitter开源的由64位整数组成分布式ID，性能较高，并且在单机上递增。  </br>
https://github.com/twitter-archive/snowflake </br>

### UidGenerator

UidGenerator是百度开源的分布式ID生成器，其基于雪花算法实现。 </br>
https://github.com/baidu/uid-generator/blob/master/README.zh_cn.md </br>

### Leaf

Leaf是美团开源的分布式ID生成器，能保证全局唯一，趋势递增，但需要依赖关系数据库、Zookeeper等中间件。 </br>
https://tech.meituan.com/MT_Leaf.html </br>

# 技巧

## 正则

行级注释 \/\/[^\n]*
块级注释 \/\*([^\*^\/]*|[\*^\/*]*|[^\**\/]*)*\*\/

private下面统一空一行
private\s+(\S+)\s+(\S+)\s*;
private $1 $2;\n

在private上加@JsonProperty
private\s+(\S+)\s+(\S+)\s*;
@JsonProperty("$2")\nprivate $1 $2;

在@Column上加@JsonProperty
@Column\(name\s*=\s*"(\w+)"\s*\)\s*(private|public)\s+(\S+)\s+(\w+)\s*;
@Column\(name = "$1"\)\n@JsonProperty("$1")\nprivate $3 $4;