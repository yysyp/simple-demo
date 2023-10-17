#! /bin/bash
set -o nounset
set -o errexit
source 0env-set.sh

gcloud compute ssh $VM_NAME --zone $Zone --internal-ip --command "cd /home/$LOGINUSER/$DEPLOY_FOLDER && chmod 777 4-1remote-software-install.sh && sudo ./4-1remote-software-install.sh $USERNAME $PASSWD"
echo "Remote software installed"
