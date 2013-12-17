#! /bin/bash

branch="nightly"
echo "Hi"
git checkout $branch 2>/dev/null
numCommits=158
prevNumLines=0
countLines="wc -l"
numCommits=5
for i in {0..$numCommits}
# for i in {0..158}
do
  git checkout $branch~$i 2>/dev/null
  commitDate=`git log --pretty=format:"%ai" | head -1`
  cppFiles=`find . -iname "*cpp"`
  headerFiles=`find . -iname "*h"`
  totalFiles="$headerFiles $cppFiles"
  numFiles=`echo "$totalFiles" | $countLines`
  # echo "numFiles: $numFiles"
  numLines=`cat $totalFiles | $countLines`
  echo "$numLines   $commitDate"
  # deltaNumLines=$numLines-$prevNumLines
  # echo "deltaNumLines: $deltaNumLines"
done
