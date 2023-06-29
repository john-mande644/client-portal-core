FROM tomcat:8.0
FROM gradle:4.10.0-jdk8-alpine

LABEL maintainer="mrhoades@owd.com"

RUN gradle wrapper

RUN ./gradlew build war

COPY /wms/build/libs/wms.war /usr/local/tomcat/webapps/



