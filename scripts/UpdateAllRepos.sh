#!/bin/bash

# declare -A repos
repos[0]="Personal"
repos[1]="Physics"
repos[2]="Latex"
  
for curRep in ${repos[*]}
do
  cd ~/Repositories/$curRep
  git pull origin master >> ~/cronOutput.txt 2>&1
  echo "CurRep: $curRep"
  echo "CurDir: $(pwd)"
done
