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

xmodmap ~/.speedswapper

export PATH
