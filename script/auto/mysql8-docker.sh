#! /bin/bash -eu

mntdir="/mnt"
unamestr="`uname`"
if [[ $unamestr =~ MINGW* ]]; then
	mntdir="//c/mnt"
fi

#export nexus-user=xxx
#export nexus-pass=xxx
#source ./nexus-cred.sh
#docker login -u $nexus-user -p $nexus-pass nexus.system.com:8080
#docker run -i --rm -p 13306:3306 -e MYSQL_ROOT_PASSWORD=root nexus.system.com:8080/docker-hub/mysql:8.0.32

if [[ $unamestr =~ MINGW* ]]; then
	winpty docker run -it --rm -p 13306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0.32
else
	docker run -it --rm -p 13306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0.32	
fi

