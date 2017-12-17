把文件夹上传至 ubuntu 服务器，cd 命令进入EurekaServer1-v1目录，执行 docker build -t zc/eureka1:v1 . 编译成 Docker 镜像。 
再执行 docker run --name eureka1 -p 8761:8761 -d zc/eureka1:v1 创建容器并运行。

# FROM指定使用哪个镜像作为基准
FROM openjdk:8-jdk-alpine
# VOLUME为挂载路径  -v
VOLUME /tmp
# ADD为复制文件到镜像中
ADD springboot-docker.jar app.jar
# RUN为初始化时运行的命令  touch更新app.jar
RUN sh -c 'touch /app.jar'
# ENV为设置环境变量
ENV JAVA_OPTS=""
# ENTRYPOINT为启动时运行的命令
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]