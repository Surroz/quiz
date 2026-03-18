FROM openjdk:27-ea-oraclelinux9
ADD target/registry-service.jar /tmp/registry-service.jar
ENTRYPOINT ["java", "-jar", "/tmp/registry-service.jar"]