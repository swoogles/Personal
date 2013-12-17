#!/bin/sh

pwd

echo "Do you REALLY want to obliterate "
echo "    ***  `pwd`/$1 ***  " 
echo "and all of its contents?"

read -s -n 1 response

if [ "$response" == "y" ];
then
  rm -rf $1
else
  echo "Phew, let's just take a step back and think about this for a minute."
fi
