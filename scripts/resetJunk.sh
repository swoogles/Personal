#!/bin/sh

# new
git checkout `git status | awk ' match($1, /modified:/) { print $2 } ' | grep -v "ApplicationResources\.properties" | grep -e properties -e ant-deploy\.xml -e build-impl\.xml`
# old
# git checkout `git status | grep modified | grep -v ApplicationResources\.properties | grep -e properties -e ant-deploy\.xml -e build-impl\.xml | awk ' { print $4 } '`
git reset HEAD appSubscriber/nbproject/build-impl.xml~
rm appSubscriber/nbproject/build-impl.xml~
git reset HEAD appPatron/nbproject/build-impl.xml~
rm appPatron/nbproject/build-impl.xml~
