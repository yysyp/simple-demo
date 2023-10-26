#! /bin/bash
set -o nounset
set -o errexit
source 0env-set.sh

#rm -rf $DEPLOY_FOLDER/aa/
#git clone --branch main --single-branch https://xxx/aa.git $DEPLOY_FOLDER/aa
#echo "Git cloned to $DEPLOY_FOLDER for uploading"

rm -rf ./appxxx/
git clone --branch main --single-branch https://xxx/aa.git appxxx
echo "Git cloned to appxxx for uploading"
