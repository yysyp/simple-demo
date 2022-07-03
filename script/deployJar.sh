#! /bin/bash
##sed -i 's/\r$//' *.sh
set -o nounset
set -o errexit

echo '-----------------Initializing...-----------------'
curDir=$PWD
imgName=simple-demo
ver=v1
ns=app

cd ..
rm -rf deployable/
mkdir -p deployable/target/
cp -r target/*.jar deployable/target/
cp -r script/ deployable/
cp -r conf/ deployable/
cp Dockerfile deployable/

cd deployable/

echo '-----------------Docker build...-----------------'
docker build -t $imgName:$ver .
if [ $? -ne 0 ]; then exit 1; fi

echo '-----------------Kubernetes deploy...-----------------'
#Login if required...

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
echo "kubectl delete ns app"
