#!/bin/bash
wqfile="$1workqueue"
echo "file=$wqfile"
i=0
while [ $i -ne 100 ]
do
   i=$(($i+1))
if [ -s $wqfile ]
then
   msg=$(awk 'NR==1{print}' $wqfile)
   date=$(date '+%Y-%m-%d %H:%M:%S')
   echo " Consuming message from queue, msg= " $msg "at:" $date
   sed -i '1d' $wqfile
   sleep 1
else
   echo " no more work left "
   exit 0
fi
done
exit 0
