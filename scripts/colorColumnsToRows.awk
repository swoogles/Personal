#!/bin/awk -f 

{
  row1=row1 $1
  row2=row2 $2
  row3=row3 $3
  row4=row4 $4
}
END {
  print row1
  print row2
  print row3
  print row4
}
