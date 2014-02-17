#!/bin/bash

# *** NOT ACTUALLY FUNCTIONING NOW ***
pwd | awk -v term="$1" ' BEGIN { FS="/"; found=0; } { for ( i = 1; i <= NF && found==0; ++i ) { path=path$i"/"; if ( $i ~ term ) {  found++; } } } END { print path; } '

