#!/bin/bash

SERVICE_PATH="/home/ec2-user/app/deploy/member-service"
BUILD_JAR=$(ls $SERVICE_PATH/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo ">>> build 파일명: $JAR_NAME" >> $SERVICE_PATH/deploy.log

echo ">>> build 파일 복사" >> $SERVICE_PATH/deploy.log
DEPLOY_PATH=$SERVICE_PATH/
cp $BUILD_JAR $DEPLOY_PATH

echo ">>> 현재 실행중인 애플리케이션 pid 확인" >> $SERVICE_PATH/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo ">>> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $SERVICE_PATH/deploy.log
else
  echo ">>> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo ">>> DEPLOY_JAR 배포"    >> $SERVICE_PATH/deploy.log
nohup java -jar -Dspring.profiles.active=dev $DEPLOY_JAR >> /home/ec2-user/deploy.log 2>$SERVICE_PATH/deploy_err.log &