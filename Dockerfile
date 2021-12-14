FROM adoptopenjdk/openjdk11:alpine

MAINTAINER Thalles Mendes

RUN apk update && apk add bash

RUN mkdir -p /opt/app

ENV PROJECT_HOME /opt/app
ENV JWT_SECRET_KEY stubJwt
ENV DATASOURCE_URL jdbc:postgresql://db:5432/client
ENV DATABASE_USERNAME client
ENV DATABASE_PASSWORD client

COPY target/client-*.jar $PROJECT_HOME/app.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-jar", "--enable-preview" ,"./app.jar"]