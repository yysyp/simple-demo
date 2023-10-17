#! /bin/bash
set -o nounset
set -o errexit
source 0env-set.sh

gcloud compute ssh $VM_NAME --zone $Zone --internal-ip --command "cd /home/$LOGINUSER/$DEPLOY_FOLDER && chmod 777 5-1app-redeploy-restart.sh && sudo ./5-1app-redeploy-restart.sh"
echo "Remote app redeployed"
