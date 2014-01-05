#!/bin/awk -f 
BEGIN { 
  print "Hi" ;
  fileName="";
  httpCall="";
  fullPathStartCol=80
}
{
  if ( $0 ~ /@Path\(/ ) {
    for ( i=1; i<=NF; i++ ) {
      fields=split($i, entries, "\"");
      if ( entries[2] != "" ) {
        path=entries[2];
        # print "PATH: " path;

        if ( fileName != $1 && i != 3 ) {
          print ""
          print $1 "  " 
          print entries[2];
          fileName = $1;
          nameLength = length(fileName);
          rootPathLength = length( entries[2] );
          rootPath = entries[2];
          
        }
        else {
          for ( j=0; j < rootPathLength; j++ ) {
            printf( "%s", " " );
          }
          outString=entries[2] " " httpCall;
          printf( "%s", outString );
          curCol=rootPathLength + length(entries[2]) + length(httpCall) + 2;

          for ( j=0; j < fullPathStartCol-curCol; j++ ) {
            printf( "%s", " " );
          }
          printf( "%s\n", rootPath entries[2] );
          

          while ( $2 != "public" ) {
            getline
          }
          for ( j=0; j < rootPathLength; j++ ) {
            printf( "%s", " " );
          }
          # printf( "%s", "  " );
          print $4
          print ""
        }

      }
    }
  }
  httpCall=$2
}
