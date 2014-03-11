#!/bin/sh

 /usr/local/bin/ant -Dbuild.compiler.emacs=true -quiet -find build.xml ${*:-classes} 2>&1 | grep '\[javac\]'
