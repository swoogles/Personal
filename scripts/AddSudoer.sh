#!/bin/bash

user="bfrasure"

awk -v name=$user ' /^root/ { print name" ALL=(ALL)  ALL" } { print $0 }' ~/sudoers
