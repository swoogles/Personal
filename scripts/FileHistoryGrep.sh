#!/bin/bash 
numCommits=$1
searchString=$2

curLogResults=""
# for i in `seq 1 "$numCommits"`
for i in $( eval echo {1..$numCommits});
do
  curLogResults=`git show master~$i:dbSmileReminder/db_update.sql | grep -A 5 "$searchString"`
  if [ "$curLogResults" != "$lastResults" ]
  then
    echo "************************** CUR LOG: $i **************************"
    echo $curLogResults
  fi
  lastResults=$curLogResults
done
