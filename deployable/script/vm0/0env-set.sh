#! /bin/bash
set -o nounset
set -o errexit
#sed -i 's/\r$//' *.sh

PROJECT_ID=xxx
ZONE=xxx
VM_NAME=my-vm-001
SA_ACCOUNT=auto_deploy@xxx.com
#DEPLOY_FOLDER=deploy_folder
LOGINUSER=ubuntu
USERNAME=nexus1
PASSWD=pass1

echo "Env set. VM_NAME=$VM_NAME"