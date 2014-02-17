#!/bin/bash
ffmpeg -f x11grab -s wxga -r 25 -i :0.0 -qscale 0 /tmp/out.mpg
