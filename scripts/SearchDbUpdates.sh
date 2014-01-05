#!/bin/sh

commits=`git rev-list --max-count=450 HEAD`

for curCommit in $commits
do
  updated=`git show $curCommit | grep db_update`
  if [ -n "$updated" ]
  then
    # echo "Got it!"
    found=`git show $curCommit | grep patron_house_view`
    if [ -n "$found" ]
    then
      echo "curCommit $curCommit"
      echo "Files: $updated"
      echo "Yay!"
    fi
  fi

done
