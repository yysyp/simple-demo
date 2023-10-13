#! /bin/bash
##sed -i 's/\r$//' *.sh
set -o nounset
set -e -x
#set -o errexit

gcloud config list
gcloud auth login

Project_ID=xxx
Zone=xxx
VM_NAME=my-vm-001
SA_ACCOUNT=auto_deploy@xxx.com

DEPLOY_FOLDER=deploy_folder
LOGINUSER=ubuntu
USERNAME=nexus1
PASSWD=pass1

gcloud beta compute instances create $VM_NAME --project=$Project_ID --zone=$Zone --machine-type=n1-standard-2 \
--network-interface=xxx \
--service-account=$SA_ACCOUNT
echo "VM created. vm=$VM_NAME"
echo "gcloud compute ssh $VM_NAME --zone $Zone --internal-ip"

gcloud compute scp --zone $Zone --internal-ip --recurse "$DEPLOY_FOLDER" $LOGINUSER@$VM_NAME:/home/$LOGINUSER/
echo "Folder $DEPLOY_FOLDER uploaded to remote VM"

gcloud compute ssh $VM_NAME --zone $Zone --internal-ip --command "cd /home/$LOGINUSER/$DEPLOY_FOLDER && chmod 777 softwareinstall.sh appdeploy.sh && sudo ./softwareinstall.sh $USERNAME $PASSWD"
echo "Remote appinstall executed."
