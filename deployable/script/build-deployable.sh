#! /bin/bash
##sed -i 's/\r$//' *.sh
set -o nounset
#set -o errexit


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

cd $curDir
