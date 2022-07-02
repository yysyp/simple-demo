#! /bin/bash
##sed -i 's/\r$//' *.sh

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
kubectl delete namespace $ns
kubectl create namespace $ns
if [ $? -ne 0 ]; then exit 1; fi
kubectl apply -f script/k8s.yaml
if [ $? -ne 0 ]; then exit 1; fi
cd $curDir