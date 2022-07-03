#! /bin/bash
##sed -i 's/\r$//' *.sh
set -o nounset
set -o errexit

echo '-----------------Initializing...-----------------'
curDir=$PWD
imgName=simple-demo
ver=v1
ns=app

echo 'Please prepare the Dockerfile, k8s.yaml in currentDirectory!'
echo "curDir=$PWD - imgName=$imgName - ver=$ver - ns=$ns"

cd ..

echo '-----------------Maven build and package...-----------------'
mvn clean package
if [ $? -ne 0 ]; then exit 1; fi

echo '-----------------Docker build...-----------------'
docker build -t $imgName:$ver .
if [ $? -ne 0 ]; then exit 1; fi

echo '-----------------Kubernetes deploy...-----------------'

oldns=$(kubectl get ns | grep "$ns")
if [ -z "$oldns" ]
then
    echo "$ns is not present"
else
    echo "$ns present"
    kubectl delete namespace $ns
fi

kubectl create namespace $ns
if [ $? -ne 0 ]; then exit 1; fi
kubectl apply -f script/k8s.yaml
if [ $? -ne 0 ]; then exit 1; fi
cd $curDir
