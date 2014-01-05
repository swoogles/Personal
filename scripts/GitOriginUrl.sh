#!/bin/sh
git remote -v | head -1 | awk ' { print $2 } ' | sed 's_https://github.com/swoogles/__g' | sed 's_\.git__g'
