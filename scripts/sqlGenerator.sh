#!/bin/sh
# modifiedFiles=`git status | grep 'modified\|deleted' | awk ' { print $3 } '`

for curSeq in  {7..30} ;
do
  echo "INSERT INTO survey_questions (survey_idx, seq, question_text) VALUES ( 2343, $curSeq, 'Question $curSeq' );"
done

