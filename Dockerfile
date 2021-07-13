# FROM openjdk:11-jdk
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:11-jdk
WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]