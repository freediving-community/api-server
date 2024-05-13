FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./gateway-service.jar
ENV TZ=Asia/Seoul
# DEV ENTRYPOINT
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "./gateway-service.jar"]
