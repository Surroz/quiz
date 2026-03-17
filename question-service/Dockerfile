FROM openjdk:27-ea-oraclelinux9
ADD target/question-service.jar /tmp/question-service.jar
ENTRYPOINT ["java", "-jar", "/tmp/question-service.jar"]