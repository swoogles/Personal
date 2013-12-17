#!/bin/bash 
review=$1

vim `crucible.sh -v --action getReview --review ${review} --attribute reviewitems | grep fromPath | awk ' { print $NF } '`
