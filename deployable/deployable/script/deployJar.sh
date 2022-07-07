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

cd ..
rm -rf deployable/
mkdir -p deployable/target/
cp -r target/*.jar deployable/target/
cp -r script/ deployable/
cp -r conf/ deployable/
cp Dockerfile deployable/

cd deployable/

echo '-----------------Docker build...-----------------'
#gcloud auth configure-docker
#echo 'password123' | docker login --username user123 --password-stdin https://xxx.bbb.ccc:12345
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
sed -i "s/simple-demo:versionplaceholder/simple-demo:$ver/g" script/k8s.yaml
kubectl apply -f script/k8s.yaml
if [ $? -ne 0 ]; then exit 1; fi
cd $curDir
echo "kubectl delete ns app"
