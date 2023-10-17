#! /bin/bash
set -o nounset
set -o errexit
#sed -i 's/\r$//' *.sh

APP_FILE_NAME=simple-e-commerce-v2-1.0.0

if [[ "$PWD" == "/usr/local/app1" ]]; then
  echo ""
else
  echo "copy."
  cp $APP_FILE_NAME.jar 5-1app-redeploy-restart.sh /usr/local/app1/
  cd /usr/local/app1
fi
#ps -ef | grep APP_FILE_NAME | grep -v grep | awk '{print $2}' | xargs kill -9
#ps -aux | grep APP_FILE_NAME | grep -v grep | awk '{print $2}' | xargs kill -9
#kill -s 9 `pgrep APP_FILE_NAME`
PID=`ps -eaf | grep $APP_FILE_NAME | grep -v grep | awk '{print $2}'`
if [[ "" != "$PID" ]]; then
  echo "killing $PID"
  kill -9 $PID
fi
nohup java -server -Dspring.profiles.active=dev -jar $APP_FILE_NAME.jar > /dev/null 2>&1 &
PID=`ps -eaf | grep $APP_FILE_NAME | grep -v grep | awk '{print $2}'`
echo "Started PID=$PID"