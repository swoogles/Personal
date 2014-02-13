#!/bin/sh
branch="$1"

$EDITOR `git merge $branch | grep CONFLICT | awk '{ print $NF }'`
