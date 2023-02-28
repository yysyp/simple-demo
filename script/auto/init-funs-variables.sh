#! /bin/bash -eu

MAVEN_IMAGE="maven:3.3-jdk-8"

mntdir="/mnt"
unamestr="`uname`"
if [[ $unamestr =~ MINGW* ]]; then
	mntdir="//c/mnt"
fi

function dockermvn() {
	docker run -i --rm --workdir=$mntdir \
	--volume "$(pwd)":$mntdir \
	${MAVEN_IMAGE} mvn $@	
}
