#!/bin/awk -f 

BEGIN { print "Hi" 
}
{
  if ($0 ~ ".*public.*" ) {
    # print $0;
    methodName=$4;
    getline;
    if ( $0 ~ ".*Connection.*" ) {
      # print $0
      print methodName;
      while ( $0 !~ ".*DBConnPool\.close.*"  &&  $0 !~ ".*public.*" )
      {
        getline
      }
      if ( $0 !~ ".*public.*" )
      {
        print "Method closed connection that it did not open."
      }
      else {
        print "Method left connection open."
      }
    }

  }
}
