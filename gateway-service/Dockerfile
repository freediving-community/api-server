FROM alpine/java:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./gateway-service.jar
ENV TZ=Asia/Seoul

ARG PROFILE

ENV SPRING_PROFILES_ACTIVE=${PROFILE}

# DEV ENTRYPOINT1
ENTRYPOINT ["java", "-jar", "./gateway-service.jar"]
