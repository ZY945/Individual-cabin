## 打包方式

- jenkins看这里[安装并在jenkins使用流水线](https://www.yuque.com/zhangyang.com/tuiv0m/qkcttd5da3yox0rq#p8meO)

## 部署tree

1. docker部署脚本
    - jar文件夹和vue文件夹下的sh脚本和Dockerfile
2. jenkins部署脚本
    - sh文件夹下的sh脚本(拷贝jar包并执行docker脚本)
        - (如果更改docker运行方式需要找到Workspace的路径并修改sh文件夹下的sh脚本))

```text
cabin
├── jar
│ ├── admin
│ │ ├── admin-0.0.1-SNAPSHOT.jar
│ │ ├── CabinAdminBuild.sh
│ │ └── Dockerfile
│ ├── app
│ │ ├── app-0.0.1-SNAPSHOT.jar
│ │ ├── appBuild.sh
│ │ └── Dockerfile
│ ├── appIM
│ │ ├── cabin-IM-0.0.1-SNAPSHOT.jar
│ │ ├── CabinIMBuild.sh
│ │ └── Dockerfile
│ └── appOauth2
│     ├── cabin-oauth2-0.0.1-SNAPSHOT.jar
│     ├── CabinOauth2.sh
│     └── Dockerfile
├── sh
│ ├── copyjar.sh
│ ├── jenkins.sh
│ └── removejar.sh
└── vue
  ├── dist
  ├── Dockerfile
  ├── nginx.conf
  └── vueBuild.sh
```
