#!/bin/bash
# List all comands I've typed that involve git
cat /home/bfrasure/.bash_history | grep -v ^# | grep git

