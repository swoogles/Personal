#!/bin/sh

#Run checkstyle on all files pass in as argument
arg=("$@")
  for ((i=0;i<$#;i++));
  do
    curFile=${arg[i]}
    echo "Filename: $curFile"
    if [ -e $curFile ];
    then
      if ! ( echo $curFile | grep -q "Test.java" )
      then
        java -cp /home/bfrasure/CheckStyle/checkstyle-5.6/checkstyle-5.6-all.jar com.puppycrawl.tools.checkstyle.Main -c /home/bfrasure/CheckStyle/checkstyle-5.6/checks/bill-checkstyle-checks.xml "${arg[i]}"
      else
        java -cp /home/bfrasure/CheckStyle/checkstyle-5.6/checkstyle-5.6-all.jar com.puppycrawl.tools.checkstyle.Main -c /home/bfrasure/CheckStyle/checkstyle-5.6/checks/bill-checkstyle-checks_testFiles.xml "${arg[i]}"
      fi
    fi
  done

  # for ((i=1;i<$1;i++));
  # do
  #   echo "${arg[i]}"
  # done

# issue=$1
