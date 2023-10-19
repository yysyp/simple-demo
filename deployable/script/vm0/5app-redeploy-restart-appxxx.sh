#! /bin/bash
set -o nounset
set -o errexit
source 0env-set.sh
##--------------------------------------------------------------------------------##
#Usage: find & replace "appxxx" with the actual application name.
#Replace APP_FILE_NAME name "simple-e-commerce-v2-1.0.0" to the actual jar file name.
#Required ENVs: $ZONE $LOGINUSER $VM_NAME
##--------------------------------------------------------------------------------##
cat > 5app-redeploy-restart-appxxx-sub.sh <<- 'EOF'
#! /bin/bash
set -o nounset
set -o errexit
APP_FILE_NAME=simple-e-commerce-v2-1.0.0

if [[ "$PWD" == "/usr/local/appxxx" ]]; then
  echo ""
else
  echo "copy."
  cp $APP_FILE_NAME.jar 5app-redeploy-restart-appxxx-sub.sh /usr/local/appxxx/
  cd /usr/local/appxxx
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
sleep 5
PID=`ps -eaf | grep $APP_FILE_NAME | grep -v grep | awk '{print $2}'`
echo "Started PID=$PID"
EOF

gcloud compute scp --zone $ZONE --internal-ip "5app-redeploy-restart-appxxx-sub.sh" $LOGINUSER@$VM_NAME:/home/$LOGINUSER/
gcloud compute ssh $VM_NAME --zone $ZONE --internal-ip --command "cd /home/$LOGINUSER && chmod 777 5app-redeploy-restart-appxxx-sub.sh && sudo ./5app-redeploy-restart-appxxx-sub.sh"
rm 5app-redeploy-restart-appxxx-sub.sh
echo "Remote appxxx redeployed"
