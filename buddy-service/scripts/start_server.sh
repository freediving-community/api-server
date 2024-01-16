#!/bin/bash
nohup java -jar -Dspring.profiles.active=dev /home/ec2-user/app/buddy-service*.jar > /dev/null 2>&1 &