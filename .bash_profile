# .bash_profile

# Get the aliases and functions
if [ -f ~/.bashrc ]; then
	. ~/.bashrc
fi

# User specific environment and startup programs
export JAVA_HOME=/usr/lib/jvm/default-java

PATH=$PATH:$HOME/bin

export GRAILS_HOME=/usr/share/grails/grails-core                                                                                                                                                               
PATH=$PATH:${GRAILS_HOME}/bin

# xmodmap ~/.speedswapper

xmodmap -e 'clear Lock' -e 'keycode 0x42 = Escape'

export PATH

[[ -s "$HOME/.rvm/scripts/rvm" ]] && source "$HOME/.rvm/scripts/rvm" # Load RVM into a shell session *as a function*
