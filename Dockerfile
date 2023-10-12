FROM openjdk:8-jdk-slim
USER root
RUN groupadd -g 9000 demo && useradd -u 9000 -g demo demo \
&& mkdir -p /app/conf /app/log
COPY conf/* /app/conf/
COPY target/*.jar /app/app.jar
#xxx install ...
RUN chown -R demo:demo /app
USER demo
WORKDIR /app
EXPOSE 8080
ENV spring.profiles.active=dev,dev-k8
CMD ["java", "-Dfile.encoding=UTF-8", "-Djava.io.tmpdir=/tmp", "-jar", "app.jar"]
#docker build -t app:v1 -f script/Dockerfile .
#docker run --rm -it -p 8080:8080 app:v1
#docker run --rm -it app:v1 echo "hello~"
#docker run --rm -it app:v1 watch "date >> /app/log/date.log"
#docker run --rm -itd app:v1 watch "date >> /app/log/date.log"
#docker exec -it c4bb5544c6dbc sh
#docker exec -it -u 0 c4bb5544c6dbc bash
#docker logs c4bb5544c6dbc
#docker start xxxx
#docker stop xxxx
#docker rm xxxx
#docker login nexus.system.abc:18080
#docker pull nexus.system.abc:18080/com/abc/aa/mongodb:v4.2.2
#docker tag nexus.system.abc:18080/com/abc/aa/mongodb:v4.2.2 mongodb
#docker rmi nexus.system.abc:18080/com/abc/aa/mongodb:v4.2.2
#docker run -itd -p 15255:15255 -name mongodb mongodb
#docker pull nexus.system.abc:18080/com/abc/aa/mysql:5.7
#docker run --name mysql57 -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 nexus.system.abc:18080/com/abc/aa/mysql:5.7
#docker run --name mysql57 -e MYSQL_ROOT_PASSWORD=root -d --add-host=host.docker.internal:host-gateway -p 3306:3306 nexus.system.abc:18080/com/abc/aa/mysql:5.7
