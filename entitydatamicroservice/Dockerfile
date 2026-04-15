#NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

#FROM openjdk:17-jdk-alpine
FROM eclipse-temurin:17-alpine-3.23
MAINTAINER deceptivelysimpletechnologies.com
LABEL description="AAF Data: Entity Data Microservice (EDM)"
LABEL copyright="Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details."
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-jar","/app.jar"]
