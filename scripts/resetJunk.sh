#!/bin/sh

# new
git checkout `git status | awk ' match($2, /modified:/) { print $3 } ' | grep -v "ApplicationResources\.properties" | grep -e properties -e ant-deploy\.xml -e build-impl\.xml`
# old
# git checkout `git status | grep modified | grep -v ApplicationResources\.properties | grep -e properties -e ant-deploy\.xml -e build-impl\.xml | awk ' { print $4 } '`
