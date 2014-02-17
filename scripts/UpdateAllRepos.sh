#!/bin/bash

# declare -A repos
repos[0]="Personal"
repos[1]="Physics"
repos[2]="Latex"

USER=bfrasure

echo "User: $USER"
  
for curRep in ${repos[*]}
do
  pwd
  cd ~/Repositories/$curRep
  HOME=/home/bfrasure git pull origin master >> ~/cronOutput.txt 2>&1
  echo "CurRep: $curRep"
  echo "CurDir: $(pwd)"
done
