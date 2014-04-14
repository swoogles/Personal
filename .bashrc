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
PS_CLOCK="\[\033[s\]\[\033[1;\$((COLUMNS-4))f\]\$(date +%H:%M)\[\033[u\]" # Clock in top right corner

# Variables I'm using:
PS_SPC="    " # Currently 5 basic spaces
PS_USR="$txtgrn\u" #  \u : Username
PS_HOST="$bldgrn\h" #  \h : Hostname
PS_DIR="$txtrst $bldblu\w" #  \w : Working directory
PS_CMD_NUM="$undcyn\!" #  \! : Command Number
PS_CURSOR="$bldgrn$"
PS_CMD_TXT="$txtrst $bldwht"
PS_GIT_BRCH_URL="$txtrst$bldylw\$(~/scripts/GitOriginUrl.sh)"
PS_GIT_BRCH_NAME="$txtrst$bldpur\$(~/scripts/GitBranchName.sh)"

# Need to wrap this up into one function so that I can hit it like the previous git functions and have it refresh everytime.
# if [ -e .git ];
# then
#   PS_GIT="$PS_GIT_BRCH_URL->$PS_GIT_BRCH_NAME"
# else
#   PS_GIT=""
# fi
# PS_MAIN="\n$PS_USR@$PS_HOST $PS_DIR $PS_SPC ${PS_GIT} $PS_SPC $txtrst \n $PS_CMD_NUM$txtrst $PS_CURSOR$PS_CMD_TXT"

PS_TIME="$txtrst\t"
# \n : NewLine

#Looks like: bfrasure@bfrasure-desktop-mint  ~/Repositories/Personal      Personal->master      24:50:27
# PS_MAIN="\n$PS_USR@$PS_HOST $PS_DIR $PS_SPC ${PS_GIT_BRCH_URL}->$PS_GIT_BRCH_NAME $PS_SPC $PS_TIME $txtrst \n $PS_CMD_NUM$txtrst $PS_CURSOR$PS_CMD_TXT"

#Looks like: bfrasure@bfrasure-desktop-mint  ~/Repositories/Personal      Personal->master
PS_MAIN="\n$PS_USR@$PS_HOST $PS_DIR $PS_SPC ${PS_GIT_BRCH_URL}->$PS_GIT_BRCH_NAME $PS_SPC $txtrst \n $PS_CMD_NUM$txtrst $PS_CURSOR$PS_CMD_TXT"

PS1=${PS_MAIN} # ${PS_BRANCH}

#Make sure every session piles commands into the HISTFILE as they occur, rather than when the shell exits
#This might turn out to be a bad idea as I'll get funky scrolling behavior in windows that I want to keep doing distinct tasks
shopt -s histappend
PROMPT_COMMAND="history -a; history -n;"

# Show a timestamp with every history entry
export HISTTIMEFORMAT="%d/%m/%y %T "

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
# tomcatHome="/home/$USER/apache-tomcat-7.0.14/"
tomcatHome="/home/$USER/.netbeans/7.1.2/apache-tomcat-7.0.22.0_base"
tomcatCmd='/opt/apache-tomcat-7.0.14/bin/'
alias tcStart="sudo ${tomcatCmd}catalina.sh run"
alias tcStop="sudo ${tomcatCmd}catalina.sh stop"

homeDir="/home/$USER/"
alias Billd="${homeDir}scripts/Billd.sh"

#PRINTER=xerox;
#export PRINTER

#Get on HPU
alias linus="ssh $USER@linus-public.highpoint.edu"
#Get on Zeus
alias zeus="ssh $USER@zeus"

#Git alias's
alias gs='git status '
alias ga='git add '
alias gb='git branch '
alias gc='git commit '
alias gd='git diff '
alias gdc='git diff --cached '
alias go='git checkout '
alias gk='gitk --all&'
alias gx='gitx --all'
alias gr='git reset '
alias gm='git merge '
alias gl='git log '
alias PUSH='~/scripts/gitPush.sh'
alias gplo='git pull origin '
alias gpsho='git push origin '
alias gitgraph='git log --graph --full-history --all --color --pretty=format:"%x1b[31m%h%x09%x1b[32m%d%x1b[0m%x20%s" --simplify-by-decoration'

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

alias cdp='cd ~/Repositories/Personal'
alias cdP='cd ~/Repositories/Physics'

alias billding='ssh -p 7822 root@199.195.116.237'

alias make='grc -es make'

alias lsd='find .'  #ls deep

set -b						# causes output from background processes to be output right away, not on wait for next primary prompt

# Functions

# Generate gitignore files by passing in language(s) of choice
function gi() { curl http://www.gitignore.io/api/$@ ;}

# Get a brief wiki summary of the argument
# Discovered here: http://www.catonmat.net/blog/another-ten-one-liners-from-commandlinefu-explained/
# Source: https://dgl.cx/wikipedia-dns
wiki() { dig +short txt $1.wp.dg.cx; }

# Change into highest level parent directory that matches the search term
# This awk part might be better in a standalone script.
cdu() { cd $( pwd | awk -v term="$1" ' BEGIN { FS="/"; found=0; } { for ( i = 1; i <= NF && found==0; ++i ) { path=path$i"/"; if ( $i ~ term ) {  found++; } } } END { print path; } ' ; ) ;}

# This isn't close to finished at the moment
# Going to finish moving the previous function out on its own first
cdd() { echo "You haven\'t figured this out yet." ; }

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

# Awesome function found here:
# http://superuser.com/questions/39751/add-directory-to-path-if-its-not-already-there
addpath ()
{
  if [[ "$PATH" =~ (^|:)"${1}"(:|$) ]]
  then
    return 0
  fi
  export PATH=$PATH:${1}
}

addpath /opt/srpostgres/srpostgres_8.3/bin


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
alias vimcur="vim -S ~/.current.vim"

alias psmem10='ps auxf | sort -nr -k 4 | head -10'

#CentOS aliases
alias yi="sudo yum install"
alias yu="sudo yum update"
alias ycu="sudo yum check-update"
alias yr="sudo yum remove"
alias yl="sudo yum list"
alias yrl="sudo yum repolist"
alias yh="sudo yum history"
alias yhi="sudo yum history info"

declare -a aliases=(\
  'gs="git status "'\
  'ga="git add "')

for i in "${aliases[@]}"
do
  echo $i
done

alias cdr="cd ~/Repositories"
alias ll="ls -al"

# Activate git tab completion
source ~/.git-completion.bash
