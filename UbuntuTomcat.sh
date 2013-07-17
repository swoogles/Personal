# Install Java
sudo apt-get install default-jdk
# Set JAVA_HOME variable
export JAVA_HOME=/usr/lib/jvm/default-java

# Get tomcat
wget http://mirror.cc.columbia.edu/pub/software/apache/tomcat/tomcat-7/v7.0.40/bin/apache-tomcat-7.0.40.tar.gz
tar xvzf apache-tomcat-7.0.40.tar.gz
mkdir /usr/share/tomcat7
mv apache-tomcat-7.0.40 /usr/share/tomcat7

# wget http://download.oracle.com/otn-pub/java/jdk/7u21-b11/jdk-7u21-linux-x64.tar.gz
# tar xvzf jdk-7u21-linux-x64.tar.gz

# Installing Grails (Optional)
# From: http://grails.org/Installation
git clone git://github.com/grails/grails-core.git
mkdir /usr/share/grails
cd /usr/share/grails
export GRAILS_HOME=/usr/share/grails/grails-core
export PATH=$PATH:${GRAILS_HOME}/bin
chdir $GRAILS_HOME

wget http://ftp.postgresql.org/pub/source/v9.2.3/postgresql-9.2.3.tar.gz
tar xvzf postgresql-9.2.3.tar.gz

useradd -d /home/willy -m willy

wget http://search.maven.org/remotecontent?filepath=junit/junit/4.11/junit-4.11.jar
wget http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
