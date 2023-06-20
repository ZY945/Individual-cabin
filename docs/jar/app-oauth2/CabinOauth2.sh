app_name='oauth2'
name=cabin/${app_name}:1.0.0
app_port='9002'
#查询得到指定名称的容器ID
#容器ID
ARG1=$(docker ps -aqf "name=${app_name}")

#查询得到指定名称的镜像ID
#镜像ID
ARG2=$(docker images -q --filter reference=${name})

#如果查询结果不为空，先停容器在删除
#容器
if [  -n "$ARG1" ]; then
 docker rm -f $(docker stop $ARG1)
 echo "$name容器停止删除成功.....！！！"
fi

#如果查询结果不为空，先删除镜像
#删除镜像
if [  -n "$ARG2" ]; then
 docker rmi -f $ARG2
 echo "$name镜像删除成功.....！！！"
fi

# 构建镜像
docker build -t ${name} .

# 重新生成并运行容器
echo '......start container......'
docker run -d -p ${app_port}:${app_port} --name ${app_name} ${name}

# 重新生成并运行容器
echo '......Success......'
