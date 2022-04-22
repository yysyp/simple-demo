FROM openjdk:8u171-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

RUN sh -c 'mkdir -p keys log ignorefolder/h2 upload-folder' &&\
    sh -c 'chmod -R 777 keys/ log/ ignorefolder/ upload-folder'

ENV GOOGLE_APPLICATION_CREDENTIALS=/keys/credentials.json
ENV spring.profiles.active=dev,dev-k8
ENTRYPOINT ["java", "-Xms512m", "-Xmx512m", "-jar", "app.jar"]
#"-XX:+UseG1GC", "-XX:+UseStringDeduplication",
#"-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=./",
#"-XX:+PrintGCDetails", "-XX:+PrintGCTimeStamps", "-Xloggc:./logs/gc.log", "-XX:+UseGCLogFileRotation", "-XX:NumberOfGCLogFiles=3", "-XX:GCLogFileSize=1024k",

