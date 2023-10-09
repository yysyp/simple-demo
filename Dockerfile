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