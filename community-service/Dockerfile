FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./community-service.jar
ENV TZ=Asia/Seoul
# DEV ENTRYPOINT
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=local", "./community-service.jar"]
