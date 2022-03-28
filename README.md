# aws-boot
## 배포스크립트
```shell
#!/bin/bash

REPOSITORY=/home/ec2-user/app
PROJECT_NAME=jun-aws-ec2

cd $REPOSITORY/$PROJECT_NAME/.file

echo ">> Git pull"
git pull

echo ">> Project build start"
./gradlew build

echo ">> Move to repository"
cd $REPOSITORY

echo ">> Copy the build file"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo ">> Check the PID of the currently running application"
CURRENT_PID=${pgrep -f ${PROJECT_NAME}.*.jar}
echo ">> PID of the currently running application : $CURRENT_PID"

if[ -z "$CURRENT_PID" ]; then
  echo ">> Not exist the running application"
else
  echo ">> kill process"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo ">> deploy the new application"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
# 여러파일중 최신파일을 저장

echo ">> JAR name: $JAR_NAME"
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
# 2>&1 명령어를 사용하면 표준오류(stderr)도 nohup.out에 같이 쓴다
```