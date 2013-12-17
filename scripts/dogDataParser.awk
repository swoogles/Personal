#!/bin/awk -f 

BEGIN {
  harm=0;
  child=0;
  adult=0;
  deaths=0;
  maimings=0;
  row=0;
}

{

  print $2
  harm=harm + $2;
  child=child + $3;
  adult=adult + $4;
  deaths=deaths + $5;
  maimings=maimings + $6;

  if ( row == 0 )
  {
    pitbullHarm = $2;
    pitbullChild = $3;
    pitbullAdult = $4;
    pitbullDeath = $5;
    pitbullMaim = $6;
    row=row+1;
    print "hi"
  }
}

END {
  print "Total Harm: ", harm, "Pitbull ", pitbullHarm/harm*100, "%" 
  print "Total child: ", child, "Pitbull ", pitbullChild/child*100, "%"
  print "Total adult: ", adult, "Pitbull ", pitbullAdult/adult*100, "%"
  print "Total deaths: ", deaths, "Pitbull ", pitbullDeath/deaths*100, "%"
  print "Total maimings: ", maimings, "Pitbull ", pitbullMaim/maimings*100, "%"
}

