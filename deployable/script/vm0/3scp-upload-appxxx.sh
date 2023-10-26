#! /bin/bash
set -o nounset
set -o errexit
source 0env-set.sh

#gcloud compute scp --zone $ZONE --internal-ip --recurse "$DEPLOY_FOLDER" $LOGINUSER@$VM_NAME:/home/$LOGINUSER/
#gcloud compute scp --zone $ZONE --internal-ip "/c/tmp/abc.json" $LOGINUSER@$VM_NAME:/home/$LOGINUSER/$DEPLOY_FOLDER/
#echo "Folder $DEPLOY_FOLDER uploaded to remote VM"

gcloud compute scp --zone $ZONE --internal-ip --recurse "source-folder" $LOGINUSER@$VM_NAME:/home/$LOGINUSER/
gcloud compute scp --zone $ZONE --internal-ip "/c/tmp/abc.json" $LOGINUSER@$VM_NAME:/home/$LOGINUSER/
echo "Folder source-folder uploaded to remote VM"
