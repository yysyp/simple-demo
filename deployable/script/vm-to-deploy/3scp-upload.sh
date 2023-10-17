#! /bin/bash
set -o nounset
set -o errexit
source 0env-set.sh

gcloud compute scp --zone $Zone --internal-ip --recurse "$DEPLOY_FOLDER" $LOGINUSER@$VM_NAME:/home/$LOGINUSER/
echo "Folder $DEPLOY_FOLDER uploaded to remote VM"
