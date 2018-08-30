#!/usr/bin/env bash

exec java ${JAVA_OPTS} -Dspring.profiles.active=docker-mysql -jar /${JAR_NAME} $*