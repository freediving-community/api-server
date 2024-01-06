# java 환경 구성
FROM openjdk:21
# build 시점 JAR 파일명 설정
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./discovery-service.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "./discovery-service.jar"]