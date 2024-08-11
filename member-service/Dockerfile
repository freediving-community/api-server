FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./member-service.jar
ENV TZ=Asia/Seoul
ARG PROFILE
ENV SPRING_PROFILES_ACTIVE=${PROFILE}
ENTRYPOINT ["java", "-jar", "./member-service.jar"]
