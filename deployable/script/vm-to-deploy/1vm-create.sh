#! /bin/bash
set -o nounset
set -o errexit
source 0env-set.sh

gcloud config list
gcloud auth login

gcloud beta compute instances create $VM_NAME --project=$Project_ID --zone=$Zone --machine-type=n1-standard-2 \
--network-interface=xxx \
--service-account=$SA_ACCOUNT
echo "VM created. vm=$VM_NAME"
echo "gcloud compute ssh $VM_NAME --zone $Zone --internal-ip"
