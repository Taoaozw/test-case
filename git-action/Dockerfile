# 该镜像需要依赖的基础镜像

FROM fabric8/java-alpine-openjdk11-jre

# 将当前目录下的jar包复制到docker容器的/目录下

ADD /git-action/build/libs/git-action.jar /app/git-action.jar

# 指定docker容器启动时运行jar包

ENTRYPOINT ["java", "-jar","app/git-action.jar"]