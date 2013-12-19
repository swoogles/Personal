#!/bin/sh

#Alternate
echo "Old"
gs | awk ' match($2, /modified:/) { print $3 } ' | grep -v ApplicationResources\.properties grep -e properties -e ant-deploy\.xml -e build-impl\.xml
echo "new"
git status | grep modified | grep -v ApplicationResources\.properties | grep -e properties -e ant-deploy\.xml -e build-impl\.xml | awk ' { print $3 } '
# git checkout `git status | grep modified | grep -v ApplicationResources\.properties | grep -e properties -e ant-deploy\.xml -e build-impl\.xml | awk ' { print $4 } '`
# git checkout srtest/build.xml

