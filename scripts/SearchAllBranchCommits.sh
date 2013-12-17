branches=`git branch | sed 's_\*__g'`

outputFile=~/gitLogs.txt
touch $outputFile

echo "" > $outputFile

for curBranch in $branches
do
  echo "CurBranch: $curBranch"
  git log -n 100 $curBranch >> $outputFile
done
