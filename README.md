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
CURRENT_PID=$(pgrep -fl ${PROJECT_NAME} | grep jar | awk '{print $1}')
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
chmod +x $JAR_NAME

echo ">> execute $JAR_NAME"
nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,classpath=:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
  -Dspring.profiles.active=real \
$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
# 2>&1 명령어를 사용하면 표준오류(stderr)도 nohup.out에 같이 쓴다
```

## Travis CI
```yaml
language: java
jdk:
  - openjdk8

branched:
  only:
    - master
  
# Travis CI 서버의 Home
cache:
  directories:
    - '$HOME/.m2/repository'
    - '$HOME/.gradle'

script: "./gradlew clean build"

before_deploy:
  - mkdir -p before-deploy
  - cp scripts/*.sh before-deploy/
  - cp build/libs/*.jar before-deploy/
  - cd before-deploy && zip -r before-deploy *
  - cd ../ && mkdir -p deploy
  - mv before-deploy/defore-deploy.zip deploy/jun-aws-ec2.zip

deploy:
  - provider: s3
    access_key_id: $AWS_ACCESS_KEY
    secret_access_key: $AWS_SECRET_KEY
    bucket: jun-aws-ec2
    region: ap-northeast-2
    skip_cleanup: true
    acl: private
    local_dir: deploy
    wait-until-deployed: true
    
  - provider: codedeploy
    access_key_id: $AWS_ACCESS_KEY
    secret_access_key: $AWS_SECRET_KEY
    bucket: jun-aws-ec2
    key: jun-aws-ec2.zip
    bundle_type: zip
    application: jun-aws-ec2
    deployment_group: jun-aws-ec2-greop
    region: ap-northeast-2
    wait-until-deployed: true

notifications:
  email:
    recipients:
      - $EMAIL_ADDRESS
```

## appspec : code deploy command
```yaml
version: 0.0
os: linux
files:
  - source: /
    destination: /home/ec2-user/app/zip/
    overwrite: yes
  - object: /
    pattern: "**"
    owner: ec2-user
    group: ec2-user

hooks:
  ApplicationStart:
    - location: deploy.sh
      timeout: 60
      runas: ec2-user
```

## 무중단 배포
```
scenario
이중화 LB가 아닌 break, running 상태 이중화
break 상태의 서버 배포 후 service port 번경

profile
stop
start
health
switch
```