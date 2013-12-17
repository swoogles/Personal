#!/bin/bash

# homeDir='/home/bfrasure/'
# srDir="${homeDir}NetBeansProjects/smilereminder3/"
# 
# tomcatHome='/home/bfrasure/apache-tomcat-7.0.14/'
# tomcatCmd="/${tomcatHome}/bin/"
# 
curBranch=`git status | grep "On branch" | awk ' { print $4 } '`
echo ""
echo "******************************"
echo "YOU ARE ON BRANCH: $curBranch"
echo "Do you really wish to push this branch to Neptune? (y/n)"
echo "******************************"

read -s -n 1 response
if [ "$response" == "y" ];
then
  git push origin $curBranch
else
  echo "Whew, one less git disaster to deal with."
fi

