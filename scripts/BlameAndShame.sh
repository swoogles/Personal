#!/bin/sh

homeDir='/home/bfrasure/'
# srDir="${homeDir}NetBeansProjects/smilereminder3/appSubscriber/"
# srDir="${homeDir}NetBeansProjects/smilereminder3/dbSmileReminder/"
srDir="${homeDir}NetBeansProjects/smilereminder3/"

cd $srDir

testCnt=0;

# allFiles=`find . -type f \( ! -iname "*.class" \) -path '.git' -prune -o -print`
allFiles=`find . -type f \( ! -iname "*.class" ! -wholename "./.git*" \)`
for curFile in $allFiles
do
  # echo "File: " $curFile


  curBlame=`git blame $curFile `
  suspectLines=`echo "$curBlame" | grep -i "frasure\|garrett\|mctear\|Info_changes\|infoChanges"`
  # billLines=`echo "$curBlame" | grep -i "Info_changes\|infoChanges"`
  # amyLines=`echo "$curBlame" | grep "Amy\|Not"`
  # echo "$curBlame"

  if [ -n "$suspectLines" ];
  then
    echo ""
    echo ""
    echo ""
    echo "Bill or Garrett touched: $curFile "
    echo ""
    echo "$suspectLines"
  fi
   # $ find . -type f \( -iname "*.txt" ! -iname ".*" \)

  # testCnt=$[$testCnt + 1]
  # if [ "$testCnt" -gt "3" ];
  # then
  #    break 
  # fi

done
