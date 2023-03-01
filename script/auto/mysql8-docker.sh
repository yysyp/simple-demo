#! /bin/bash -eu

mntdir="/mnt"
unamestr="`uname`"
dockerstr="docker"
if [[ $unamestr =~ MINGW* ]]; then
	mntdir="//c/mnt"
	dockerstr="winpty docker"
fi

#export nexus-user=xxx
#export nexus-pass=xxx
#source ./nexus-cred.sh
#docker login -u $nexus-user -p $nexus-pass nexus.system.com:8080
#docker run -i --rm -p 13306:3306 -e MYSQL_ROOT_PASSWORD=root nexus.system.com:8080/docker-hub/mysql:8.0.32

$dockerstr run -it --rm -p 13306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=testdb -e MYSQL_ROOT_HOST='%' -v "$mntdir/data":/docker-entrypoint-initdb.d mysql:8.0.32

