# 拉取基础镜像
FROM java:11

# 设置作者信息
MAINTAINER huang "huang@199.com"

# 把 MyWebTest.jar添加到容器里，并重命名为app.jar
# 因为 MyWebTest.jar和Dockerfile在同一个目录下，所以只写文件名即可
ADD MyWebTest.jar app.jar

# 设置端口号，此处只开放一个端口8083
EXPOSE 8765

# 执行命令，此处运行app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","app.jar"]