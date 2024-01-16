#!/bin/bash
JAR_FILE=$(ls -Art /home/ec2-user/app/deploy/buddy-service.jar | tail -n 1)
sudo -u ec2-user nohup java -jar -Dspring.profiles.active=dev $JAR_FILE > /home/ec2-user/app/deploy/buddy.log 2>&1 &