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

| 框架         | 版本     |
|------------|--------|
| springboot | 3.1.0  |
| vue        | 3.2.13 |
| vue-router | 4.2.1  |
| vuetify    | 3.3.0  |
| axios      | 1.4.0  |

## 组件

| 组件       | 版本     |
|----------|--------|
| mysql    | 8.0.31 |
| Redis    | 3.2.12 |
| RabbitMQ | 3.10.0 |
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

### 注释

行级注释 \/\/[^\n]*
块级注释 \/\*([^\*^\/]*|[\*^\/*]*|[^\**\/]*)*\*\/

### private下面统一空一行

private\s+(\S+)\s+(\S+)\s*;
private $1 $2;\n

### 在private上加@JsonProperty

private\s+(\S+)\s+(\S+)\s*;
@JsonProperty("$2")\nprivate $1 $2;

### 在@Column上加@JsonProperty

@Column\(name\s*=\s*"(\w+)"\s*\)\s*(private|public)\s+(\S+)\s+(\w+)\s*;
@Column\(name = "$1"\)\n@JsonProperty("$1")\nprivate $3 $4;

# 小屋里程碑

## 2023.5.23

- [x] gitee分支、路径、代码块的获取
- [ ] gitee仓库的获取
- [ ] 前端渲染代码块
- [x] 分布式锁实现方式
    - 数据库
    - redis

## 2023.5.29

- [ ] jenkins未集成
- [ ] 聊天室websocket可以连接，后端可实现ws和http的连接，但是vue无法连接
- [x] 解决vue.config.js第二个代理无法使用的问题
- [ ] 数据量过大，页面无响应，可以考虑分页gitee API有分页参数per_page和page
    - [x] 目前就差vue分页传参，后端已解决
    - [ ] 修正：目录树是不能分页的

## 2023.5.30

- [x] 添加admin监控和rocketmq的监控
- [ ] gitee有各种限制,并且之前丢数据了,我的代码仓库需要机器验证,转战gitlab(而且gitlab的workhoob有push后jenkins构建)
- [ ] dubbo
    - [ ] [动态调整服务超时时间 | Apache Dubbo](https://cn.dubbo.apache.org/zh-cn/overview/tasks/traffic-management/timeout/)
    - [ ] [apache/dubbo-samples: samples for Apache Dubbo (github.com)](https://github.com/apache/dubbo-samples)

## 2023.6.2

- [x] 短链接
    - [x] 后端(重定向有问题)
    - [x] UI

## 小节

- 目前还未完工
    - [ ] jenkins构建接口
    - [ ] gitlab的拉取解压等api
        - [ ] 建议私服
    - [x] 聊天室的websocket连接

## 2023.6.4

- 由于授权服务器1.1.0需要springboot3.1.0
    - 所以将springboot升级为3.1.0（其实之前也想了,毕竟3.1.0是第一个稳定的正式版

## 2023.6.8

- [ ] 整合sonar到github检查github代码
- [ ] 整合jenkins去打包github的代码
- [x] cabin实现脚本一键部署更新版本

## 2023.6.19

- [ ] springboot admin 监控docker里的应用，调用的是容器id并且监控失败
  ![[Pasted image 20230619102707.png]]
- [ ] springboot执行shell脚本
    - [ ] jenkins构建
    - [ ] docker的相关命令
- [x] 排包，ffmpeg引入导致打包一个G，700多M都是他
- [ ] starter实现
    - [ ] [Zyred9/boot-starters: springboot starter 练习项目 (github.com)](https://github.com/Zyred9/boot-starters)
    - [ ] [spring-starter-demo: 自定义Spring Boot starter (gitee.com)](https://gitee.com/mchangtian/spring-starter-demo)

## 2023.6.20

- [x] 第三方登陆后，如何获取服务器的响应
    - [x] 之前写错了，重定向url不仅仅是后端，也可以是前端地址，然后获取参数去请求后端
    - [x] 第一种:先说缺点，还是之前的重定向url是后端，导致与前端没关联，前端获取不到token
    - [x] 第二种:重定向url是前端，然后vue去监控参数，一旦获取到就请求后端接口，同时设置状态值，用来结束轮询

## 2023.6.21

- [ ] 了解spring状态机
- [ ] 了解tomcat的Catalina
- [x] 账户注册和登录功能、UI
- [x] 飞书登录和绑定账户
- [ ] gitee第三方登录
- [x] 使用vue3的格式重写登录、绑定、注册UI
    - [x] 主要是data、method都写到setup()里，同时注册组件为全局
- [ ] 绑定表需要加状态，用户可能解绑

# 2023.6.22

- [ ] 创建一个请求记录表，记录url，ip，请求参数
    - [x] 使用注解和aop去实现
    - [x] 使用钉钉通知
- [x] 自定义注解限制ip访问接口次数
    - [x] 使用aop会导致有延迟好像,导致限流失败
    - [x] 最终使用拦截器

# 2023.6.23

- [x] 实现github的第三方登录和绑定
    - [ ] 服务器登录时很慢，可能需要服务器挂代理
    - [ ] 本地部署时加速，发现可以正常访问，无法判断是客户端还是服务端问题
    - [ ] 服务器部署后发现请求很慢，客户端加速还是不行，初步判断是服务器需要代理
    - [x] 遇到的问题:Java在请求某些不受信任的https网站时会报：PKIX path building failed
- [ ] 这个绑定感觉有个问题(还未测试)
    - [ ] 正常情况是一个第三方登录，然后保存id，但是如果有两个页面同时登录两个第三方，浏览器会不会出现两个id，然后判断时----------改成每个绑定都是if了，不使用else
      if，但是他会都判断一遍，虽然三方登录设置的不多，但是感觉还是不妥

# 2023.6.24

- [x] 全局异常,钉钉报警--------2023.6.26完成
- [x] 实现gitee的第三方登录功能和UI

# 2023.6.25

- [ ] 发现时序数据库IoTDB
    - [ ] 有java的api，可试一下
    - [ ] 间隙锁的单行的查改增

# 2023.6.

- [x] 发现bug，gitee分支获取的前端每输入一下，就请求，应该增加判断是否离开输入框
- [x] 把钉钉通知通过消息队列去实现异步
    - [x] 发现报警是正常的，但是监控接口信息出现消息莫名丢失情况
        - [x] 是被另一个项目的消费了(绑定信息重复了)，一定要注意该队列是否在其他项目被监听
- [ ] 整合ffmpeg实现视频下载和音频提取
- [x] 实现email报警通知