#! /bin/bash
##sed -i 's/\r$//' *.sh


echo '-----------------Initializing...-----------------'
curDir=$PWD
containerRegistry=gcr.io/xxx/
imgName=simple-demo
ver=v1
ns=app

cd ..

echo '-----------------Docker build...-----------------'
docker build -t $containerRegistry$imgName:$ver .
#gcloud auth configure-docker
docker push $containerRegistry$imgName:$ver
if [ $? -ne 0 ]; then exit 1; fi

echo '-----------------Kubernetes deploy...-----------------'
kubectl delete namespace $ns
kubectl create namespace $ns
if [ $? -ne 0 ]; then exit 1; fi
kubectl apply -f script/k8s-gcr.yaml
if [ $? -ne 0 ]; then exit 1; fi
cd $curDir
echo "kubectl delete ns app"
