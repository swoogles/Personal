# Sample .bashrc for SuSE Linux
# Copyright (c) SuSE GmbH Nuernberg

# There are 3 different types of shells in bash: the login shell, normal shell
# and interactive shell. Login shells read ~/.profile and interactive shells
# read ~/.bashrc; in our setup, /etc/profile sources ~/.bashrc - thus all
# settings made here will also take effect in a login shell.
#
# NOTE: It is recommended to make language settings in ~/.profile rather than
# here, since multilingual X sessions would not work properly if LANG is over-
# ridden in every subshell.

if [ -f /etc/bashrc ]; then
  . /etc/bashrc
fi

if [ -f ~/.bash_colors ]; then
  . ~/.bash_colors 
fi

# don't put duplicate lines in the history. See bash(1) for more options
# ... or force ignoredups and ignorespace
HISTCONTROL=ignoredups:ignorespace



# Some applications read the EDITOR variable to determine your favourite text
# editor. So uncomment the line below and enter the editor of your choice :-)
export EDITOR=/usr/bin/vim
#export EDITOR=/usr/bin/mcedit

export PRINTER=Xerox-Phaser-8560DN

# for setting history length see HISTSIZE and HISTFILESIZE in bash(1)
export HISTSIZE=50000
export HISTFILESIZE=50000
export HISTIGNORE='&:bg:fg:ll:h'

# make less more friendly for non-text input files, see lesspipe(1)
[ -x /usr/bin/lesspipe ] && eval "$(SHELL=/bin/sh lesspipe)"

# Displays time in the top right corner
PROMPT_CLOCK="\[\033[s\]\[\033[1;\$((COLUMNS-4))f\]\$(date +%H:%M)\[\033[u\]" # Clock in top right corner
# PS1=${MAIN_PROMPT}${PROMPT_CLOCK} # Main prompt
PROMPT_BRANCH="\[\033[s\]\[\033[2;\$((COLUMNS-8))f\]\$(git branch 2>/dev/null | grep \*)\[\033[u\]" # Git branch in top right corner

# Variables I'm using:
PROMPT_SPC="    " # Currently 5 basic spaces
PROMPT_USR="$txtgrn\u" #  \u : Username
PROMPT_HOST="$bldgrn\h" #  \h : Hostname
PROMPT_DIR="$txtrst $bldblu\w" #  \w : Working directory
PROMPT_CMD_NUM="$undcyn\!" #  \! : Command Number
PROMPT_CURSOR="$bldgrn$"
PROMPT_CMD_TXT="$txtrst $bldwht"
PROMPT_RUN_GIT_BRANCH_URL="$txtrst$bldylw`~/scripts/GitOriginUrl.sh`"
PROMPT_RUN_GIT_BRANCH_NAME="$txtrst$bldred`~/scripts/GitBranchName.sh`"
PROMPT_TIME="$txtrst\t"
# \n : NewLine
# \n : NewLine
PROMPT_MAIN="\n$PROMPT_USR@$PROMPT_HOST $PROMPT_DIR $PROMPT_SPC ${PROMPT_RUN_GIT_BRANCH_URL}->$PROMPT_RUN_GIT_BRANCH_NAME $PROMPT_SPC $PROMPT_TIME $txtrst \n $PROMPT_CMD_NUM$txtrst $PROMPT_CURSOR$PROMPT_CMD_TXT"

# Old and crappy version
# PROMPT_MAIN='\n\[\e[0;32m\]\u@\[\e[1;32m\]\h \[\e[m\] \[\e[1;34m\]\w\[\e[m\] \[\e[1;32m\]\n\$\[\e[m\] \[\e[1;37m\]'
PS1=${PROMPT_MAIN} # ${PROMPT_BRANCH}

#Make sure every session piles commands into the HISTFILE as they occur, rather than when the shell exits
#This might turn out to be a bad idea as I'll get funky scrolling behavior in windows that I want to keep doing distinct tasks
shopt -s histappend
PROMPT_COMMAND="history -a; history -n;"

# JD's prompt
# PS1="\n\u:\w \[$EWHITE\] \n$ "

# For some news readers it makes sense to specify the NEWSSERVER variable here
#export NEWSSERVER=your.news.server

# If you want to use a Palm device with Linux, uncomment the two lines below.
# For some (older) Palm Pilots, you might need to set a lower baud rate
# e.g. 57600 or 38400; lowest is 9600 (very slow!)
#
#export PILOTPORT=/dev/pilot
#export PILOTRATE=115200

#export CATALINA_BASE=~/.netbeans/7.0/apache-tomcat-7.0.14.0_base/

test -s ~/.alias && . ~/.alias || true

#Use VIM instead of VI
alias vi=vim

#VIM bindings in bash
# set -o vi

#Safe Versions
alias 'rmrf'='~/scripts/rmEnhanced.sh'

#Tomcat commands
# tomcatHome='/home/bfrasure/apache-tomcat-7.0.14/'
tomcatHome='/home/bfrasure/.netbeans/7.1.2/apache-tomcat-7.0.22.0_base'
tomcatCmd='/opt/apache-tomcat-7.0.14/bin/'
alias tcStart="sudo ${tomcatCmd}catalina.sh run"
alias tcStop="sudo ${tomcatCmd}catalina.sh stop"

homeDir='/home/bfrasure/'
alias Billd="${homeDir}scripts/Billd.sh"

#PRINTER=xerox;
#export PRINTER

#Get on HPU
alias linus='ssh bfrasure@linus-public.highpoint.edu'
#Get on Zeus
alias zeus='ssh bfrasure@zeus'

#Git alias's
alias gs='git status '
alias ga='git add '
alias gb='git branch '
alias gc='git commit '
alias gd='git diff '
alias go='git checkout '
alias gk='gitk --all&'
alias gx='gitx --all'
alias gr='git reset '
alias PUSH='~/scripts/gitPush.sh'

#cd Variations
alias ..="cd .."
alias ..2="cd ../.."
alias ..3="cd ../../.."
alias ..4="cd ../../../.."
alias ..5="cd ../../../../.."



#Hop to key directories
alias cdsr='cd ~/NetBeansProjects/smilereminder3/'
alias cdsf='cd ~/NetBeansProjects/smilereminder3/appSubfiles/web/'

#More Key directories
alias cdcmd='cd ~/CommandLine/'
alias cdfeed='cd ~/Feed/'
alias cdjaxb='cd ~/NetBeansProjects/smilereminder3/appProfile/src/java/com/communitect/framework/feed'
alias cdapi='cd ~/API'

alias cdtom='cd '$tomcatHome
alias cdlogs='cdtom; cd logs' 

alias cds='cd ~/scripts'
#alias cdlogs='cd '${tomcatHome}/logs

alias cdjunk='cd ~/junkDir'

alias cdot='cd /media/ffcf93ba-7e63-4a29-ae33-cab0b6537424/opt/apache-tomcat-7.0.14'

alias lynx='lynx -vikeys'

alias dbsr='psql -U srpostgres smilereminder'
alias dbnm='psql -U srpostgres numbermapping'

alias cdp='cd ~/Dropbox/Projects/ShapesLibrary/'

alias billding='ssh -p 7822 root@199.195.116.237'

alias make='grc -es make'

set -b						# causes output from background processes to be output right away, not on wait for next primary prompt

# Functions
netinfo ()
{
  echo "--------------- Network Information ---------------"
  /sbin/ifconfig | awk /'inet addr/ {print $2}'
  echo ""
  /sbin/ifconfig | awk /'Bcast/ {print $3}'
  echo ""
  /sbin/ifconfig | awk /'inet addr/ {print $4}'

  # /sbin/ifconfig | awk /'HWaddr/ {print $4,$5}'
  echo "---------------------------------------------------"
}

mkdircd () {
  mkdir -p "$@" &&
  eval cd "\"$$#\"";
}


alias billding='ssh -p 7822 root@199.195.116.237'

# enable color support of ls and also add handy aliases
if [ -x /usr/bin/dircolors ]; then
    test -r ~/.dircolors && eval "$(dircolors -b ~/.dircolors)" || eval "$(dircolors -b)"
    alias ls='ls --color=auto'
    #alias dir='dir --color=auto'
    #alias vdir='vdir --color=auto'

    alias grep='grep --color=auto'
    alias fgrep='fgrep --color=auto'
    alias egrep='egrep --color=auto'
fi

# Alias definitions.
# You may want to put all your additions into a separate file like
# ~/.bash_aliases, instead of adding them here directly.
# See /usr/share/doc/bash-doc/examples in the bash-doc package.

if [ -f ~/.bash_aliases ]; then
    . ~/.bash_aliases
fi

alias billding='ssh -p 7822 root@199.195.116.237'

alias vimsesh="vim -S ~/vim_sessions/`git rev-parse --abbrev-ref HEAD`.vim"
