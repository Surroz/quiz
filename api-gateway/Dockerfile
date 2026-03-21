FROM openjdk:27-ea-oraclelinux9
ADD target/api-gateway.jar /tmp/api-gateway.jar
ENTRYPOINT ["java", "-jar", "/tmp/api-gateway.jar"]