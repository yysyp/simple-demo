#! /bin/bash -eu

NAMESPACE=$1
SECRETNAME=$2
source ./init-funs-variables.sh
cd $(dirname "$0")/..

kubectl create secret -n ${NAMESPACE} docker-registry ${SECRETNAME} --docker-server=xxxx --docker-username=xxx --docker-password=xxx

