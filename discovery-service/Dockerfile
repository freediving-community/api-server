FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./discovery-service.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "./discovery-service.jar"]