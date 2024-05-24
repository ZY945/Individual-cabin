# 后端

都是redis保存

## 登录系统

1. 用户
    1. user:token:
    2. 目前key是email+随机数,value是userId,都用base64加密

## oauth2

1. 游客
    1. guest:token:
    2. 为了不暴露用户信息
    3. key是当前时间毫秒数+随机数,value是oauth2登录的名字,都用base64加密
2. 用户
    1. user:token:
    2. 目前key是email+随机数,value是userId,都用base64加密

# 前端

cookie存放token，localStorage存放用户类型