#!/bin/bash
# For some reason this wasn't working on my machine, so I used the hardcode path.
# cat $HISTFILE 
commands=$(cat /home/bfrasure/.bash_history | grep -v ^# | awk ' { print $1; } ' | sort | uniq )
echo "Command List:"
echo "$commands" | wc -l
echo $commands
