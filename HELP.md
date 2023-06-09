# 前端打包部署
## 目录
下面文件均在项目根目录下的docs文件夹内

部署时需要的文件放在同一个文件夹下,我这里是vue 
- vue
  - dist
    - 自己打包
  - nginx.conf
    - 需要配置反向代理的ip为自己的服务器,本地就127.0.0.1
  - Dockerfile
    - 不需要修改
  - vueBuild.sh
    - 不需要修改
  最终
```text
[root@iZbp1c400avts4bhhmibppZ vue]# ls
dist  Dockerfile  nginx.conf  vueBuild.sh
[root@iZbp1c400avts4bhhmibppZ vue]# chmod 744 vueBuild.sh 
[root@iZbp1c400avts4bhhmibppZ vue]# ./vueBuild.sh 
success
...
```
## 打包
在app-ui模块下执行
```
npm run build
```
## 设置sh文件权限
```
chmod 744 vueBuild.sh 
```
## 执行文件构建并允许docker容器
```
./vueBuild.sh
```
# 后端打包部署
需要跳过测试,先打工具类的包(如果是idea,maven的右上角有跳过测试的按钮,点击即可)
```text
mvn install -Dmaven.test.skip=true
```
## 目录

- jar
  - app
    - Dockerfile
    - appBuild.sh
      - 加上环境变量,在docker run (--name)前添加
    - app-0.0.1-SNAPSHOT.jar
  - admin
    - ...
    
每个应用部署时需要配置各种环境变量,根据yaml需要的
在docker run 后面加-e (是每个参数前都需要-e,可以在idea的启动类的环境变量复制下来,把;替换成 -e )
然后进行仔细修改
## app
在idea执行app下的package
## admin
## cabin-IM
## cabin-oauth2