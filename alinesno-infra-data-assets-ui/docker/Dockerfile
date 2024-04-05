# 使用 OpenResty 作为基础镜像
FROM nginx:latest

# 将构建好的静态文件复制到 OpenResty 的默认静态文件目录
COPY ./dist /usr/share/nginx/html

# 复制 OpenResty 配置文件
COPY nginx.conf /etc/nginx/nginx.conf

# 暴露端口
EXPOSE 80
