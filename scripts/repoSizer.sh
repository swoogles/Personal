#! /bin/bash
echo "Hi"
git checkout nightly 2>/dev/null
numCommits=158
prevNumLines=0
countLines="wc -l"
for i in {0..5}
# for i in {0..158}
do
  git checkout nightly~$i 2>/dev/null
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
