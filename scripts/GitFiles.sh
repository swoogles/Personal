#!/bin/sh
modifiedFiles=`git status | grep 'modified\|deleted' | awk ' { print $3 } '`

for curFile in $modifiedFiles
do
  action=`git status | grep "$curFile" | awk ' { print $2 } '`
  echo "Action: $action"

  echo "File: $curFile "
  echo "Do you wish to add this file to the next commit?"

  read -s -n 1 response
  if [ "$response" == "y" ];
  then
    if [ "$action" == "deleted:" ];
    then
      git rm $curFile
    elif [ "$action" == "modified:" ];
    then
      git add $curFile
    else
      echo "Don't know what action to perform on file."
    fi
  else
    echo "Skipping file"
  fi
  echo ""
  echo ""
done
