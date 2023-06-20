# 设置基础镜像
FROM nginx
# 定义作者
MAINTAINER zhangyang <zhangyang_2002@foxmail.com>
# 将dist文件中的内容复制到 /usr/share/nginx/html/ 这个目录下面
COPY ../../../app-ui/dist  /usr/share/nginx/html/
COPY nginx.conf /etc/nginx/nginx.conf
RUN echo 'echo init ok!!'
