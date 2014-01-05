#!/bin/bash 
branch="$1" 
git log $branch | head -200 | awk ' $2 ~ /bfrasure/ { getline; for(i=2; i<NF; i++) printf $i " "; print $NF }' | head -1
