#!/usr/bin/awk -f 
BEGIN { 
  path=$0
  FS="/"; 
  found=0; 
} 
{ 
  path=$0
  for ( i = NF; i >= 1 && found==0; --i ) 
  { 
    # sub(/$i$/,"",path);
    print path; 
    sub(/\/[^\/]*$/,"",path);
    if ( path ~ "$term/$/" ) {
      print "TITS: " path;
    }


    # path=path$i"/"; 
    # if ( $i ~ term ) 
    # {  
    #   found++; 
    # } 
  } 
} 
END { 
  print path; 
} 
