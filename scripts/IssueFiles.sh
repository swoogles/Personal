#!/bin/sh

#Pass in a Jira issue as the first argument to see all files that were touched while working on this issue
issue="$1"
git show --pretty="format:" --name-only `git log -g --grep=$1 | grep "^commit " | awk ' { print $2 } ' | uniq` | sort | uniq
