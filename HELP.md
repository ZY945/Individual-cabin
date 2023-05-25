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