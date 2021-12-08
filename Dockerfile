#使用JDK8环境作为基础镜像
FROM java:8

#拷贝文件并且重命名
ADD build/libs/MilkTea-0.0.1-SNAPSHOT.jar app.jar

#指定容器需要映射到主机的端口
EXPOSE 8080

#容器启动时执行的命令
ENTRYPOINT ["java", "-jar", "/app.jar"]