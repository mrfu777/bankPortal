FROM java:openjdk-8u111-jdk-alpine
VOLUME /tmp
EXPOSE 8090
ADD portal-0.0.1-SNAPSHOT.jar /bankportal.jar
ENTRYPOINT ["java","-jar","/bankportal.jar"]