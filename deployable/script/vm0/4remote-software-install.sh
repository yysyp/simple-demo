#! /bin/bash
set -o nounset
set -o errexit
source 0env-set.sh

cat > 4remote-software-install-sub.sh <<- 'EOF'
#! /bin/bash
set -o nounset
set -o errexit
#sed -i 's/\r$//' *.sh

tar -xzvf dockerxxx.tgz
cp docker/* /usr/bin/
nohup dockerd &
echo 'Docker installed & started'

tar -xzvf OpenJDKxxx.tar.gz -C /opt
echo 'export JAVA_HOME="/opt/jdk-xxx"' >> ~/.bash_profile
echo 'PATH="$JAVA_HOME"/bin:$PATH' >> ~/.bash_profile
source ~/.bash_profile
java -version
echo 'Jdk installed'

docker login -u $1 -p $2 nexusxxx:12345
docker stop mysql
docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -d --add-host=host.docker.internal:host-gateway -p 3306:3306 nexusxxx:12345/com/xx/mysql:v5.7
echo 'docker mysql installed'

if [ ! -d "/usr/local/app1" ]; then
    echo 'Not exists, so mkdir'
    mkdir -p /usr/local/app1
else
    echo '/usr/local/app1 exists'
fi

EOF

gcloud compute scp --zone $ZONE --internal-ip "4remote-software-install-sub.sh" $LOGINUSER@$VM_NAME:/home/$LOGINUSER/
gcloud compute ssh $VM_NAME --zone $ZONE --internal-ip --command "cd /home/$LOGINUSER && chmod 777 4remote-software-install-sub.sh && sudo ./4remote-software-install-sub.sh $USERNAME $PASSWD"
rm 4remote-software-install-sub.sh
echo "Remote software installed"
