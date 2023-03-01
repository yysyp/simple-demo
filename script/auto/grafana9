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
#docker run -it --rm -p 13000:3000 nexus.system.com:8080/grafana/grafana-oss:9.3.2

if [[ $unamestr =~ MINGW* ]]; then
	winpty docker run -it --rm -p 13000:3000 grafana/grafana-oss:9.3.2
else
	docker run -it --rm -p 13000:3000 grafana/grafana-oss:9.3.2	
fi

