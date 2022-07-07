#! /bin/bash
##sed -i 's/\r$//' *.sh
set -o nounset
#set -o errexit

echo '-----------------Initializing...-----------------'
DateTimeVer=$(date +"%Y%m%d%H%M")
curDir=$PWD
imgName=simple-demo
ver="v$DateTimeVer"
ns=app

echo 'Please prepare the Dockerfile, k8s.yaml in currentDirectory!'
echo "curDir=$PWD - imgName=$imgName - ver=$ver - ns=$ns"

cd ..

echo '-----------------Maven build and package...-----------------'
mvn clean package
if [ $? -ne 0 ]; then exit 1; fi

cd script
. ./build-deployable.sh

. ./deployJar.sh

cd $curDir
