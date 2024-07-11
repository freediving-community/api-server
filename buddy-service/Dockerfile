FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./buddy-service.jar
ENV TZ=Asia/Seoul
# DEV ENTRYPOINT1
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "./buddy-service.jar"]
