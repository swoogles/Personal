#!/bin/bash

startDate=$1
hashList=`git log --after={$startDate}| \
awk -v userName=$USERNAME \
    ' $1 == "commit" {
        hash=$2
        getline
        if ( $2 == userName ) { \
          print hash
        }
      }' ` 
fileList=`git show --pretty="format:" --name-only $hashList | sort | uniq`
echo "$fileList" > ~/fileList.txt
# vi $fileList
vi ~/fileList.txt
