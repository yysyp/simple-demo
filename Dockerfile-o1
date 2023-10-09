#FROM openjdk:8u171-jre-alpine
FROM openjdk:8-jdk-slim

USER root
RUN groupadd -g 9999 demo
RUN useradd demo -u 8888 -g demo

RUN mkdir -p /app/conf /app/log /app/ignorefolder/h2 /app/upload-folder
COPY target/*.jar /app/app.jar
COPY conf/* /app/conf/

#RUN sh -c 'mkdir -p keys log ignorefolder/h2 upload-folder conf' &&\
#    sh -c 'chmod -R 777 keys/ log/ ignorefolder/ upload-folder conf'

RUN chown -R demo:demo /app
#RUN chmod +x /app/start_demo.sh

USER demo

WORKDIR /app
ENV GOOGLE_APPLICATION_CREDENTIALS=/keys/credentials.json
ENV spring.profiles.active=dev,dev-k8
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Djava.io.tmpdir=/tmp", "-Xms512m", "-Xmx512m", "-jar", "app.jar"]
#"-XX:+UseG1GC", "-XX:+UseStringDeduplication",
#"-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=./",
#"-XX:+PrintGCDetails", "-XX:+PrintGCTimeStamps", "-Xloggc:./logs/gc.log", "-XX:+UseGCLogFileRotation", "-XX:NumberOfGCLogFiles=3", "-XX:GCLogFileSize=1024k",
