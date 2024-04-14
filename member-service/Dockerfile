FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./member-service.jar
ENV TZ=Asia/Seoul
# DEV ENTRYPOINT
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "./member-service.jar"]
