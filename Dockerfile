FROM openjdk:8u121-jdk-alpine

MAINTAINER Mert Kara <amertkara@gmail.com>

ENV JAR_NAME=campsite-manager-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS="-Xms512M -Xmx1G -XX:MaxMetaspaceSize=256M"

COPY src/main/bash/entrypoint.sh /entrypoint.sh
COPY target/$JAR_NAME /$JAR_NAME
RUN apk update && apk add bash
RUN chmod +x /entrypoint.sh

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.3.0/wait /wait
RUN chmod +x /wait

CMD /wait && /entrypoint.sh