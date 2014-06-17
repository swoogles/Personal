INSTALL="sudo apt-get install -y"
$INSTALL vim

$INSTALL curl
$INSTALL screen
$INSTALL tmux
$INSTALL g++
$INSTALL libboost-all-dev
#Instructions for setting up opengl here: www.wikihow.com/Install-Mesa-(openGL)-on-Linux-Mint
$INSTALL freeglut3-dev
$INSTALL cmake
$INSTALL libglew-dev
$INSTALL mesa-common-dev
$INSTALL build-essential
$INSTALL libglew1.5-dev libglm-dev
$INSTALL libxmu-dev
#To solve "could not find working GL lib" error
$INSTALL libxi-dev libxine-dev
$INSTALL rpm


$INSTALL imagemagick
$INSTALL libmagick++-dev

$INSTALL python-dev
# For pygame
$INSTALL libsdl1.2-dev libsmpeg-dev libsdl-mixer1.2-dev libsdl-image1.2-dev libsdl-ttf2.0-dev
sudo pip3 install hg+http://bitbucket.org/pygame/pygame

# For installing RPMs in a better way?
$INSTALL alien


# Ruby setup
# Repo Version
$INSTALL ruby-dev
$INSTALL rake
# RVM version
curl -sSL https://get.rvm.io | bash -s stable --ruby
~/.rvm/bin/rvm install 1.9.3

cd ~/Repositories/Personal
./VimSetup.sh
./MakeLinks.sh

# WineSetup
sudo add-apt-repository ppa:ubuntu-wine/ppa
sudo apt-get update
$INSTALL wine1.6
$INSTALL winetricks wine-mono4.5.2 wine-gecko2.24

#Alternate bleeding edge install
# git clone https://github.com/b4winckler/vim
# ./configure --enable-cscope --prefix=/usr --with-features=huge --enable-pythoninterp --enable-luainterp

# ** VIM ** 

  #Pathogen Setup
  # wget -O ~/.vim/autoload/pathogen.vim https://raw.github.com/tpope/vim-pathogen/master/autoload/pathogen.vim

  #Syntastic Setup
  # git clone https://github.com/scrooloose/syntastic.git ~/.vim/bundle

  # **** VUNDLE *****
  # Ohhhh man, screw that old way of doing things. Use vundle. It's awesome.
  # Read here: https://github.com/gmarik/vundle#about
  # Run: git clone https://github.com/gmarik/vundle.git ~/.vim/bundle/vundle
  # These lines should be enough to get going:
  #
  # set rtp+=~/.vim/bundle/vundle/
  # call vundle#rc()
  # " let Vundle manage Vundle
  # " required! 
  # Bundle 'gmarik/vundle'
  #
  # Use the URL of the github repo in question for extremely easy installs
  #
  # Can also use the url from vim-scripts repos

# Postgres setup
$INSTALL postgresql libpq-dev
$INSTALL postgresql-9.3-postgis-2.1 postgresql-9.3-postgis-scripts
# Change user "postgres" password to empty string
sudo -u postgres psql -c "ALTER USER postgres with encrypted password '';" template1
# Tell it to listen for connections from localhost
sudo sed -i "s/#listen_addresses = 'localhost'/listen_addresses = 'localhost'/" /etc/postgresql/9.3/main/postgresql.conf
# Configuration finished, restart to take effect
sudo /etc/init.d/postgresql restart

./Mint_PostgresSetup.sh


wget http://plib.sourceforge.net/dist/plib-1.8.5.tar.gz


# Chrome installation. Instructions from: http://www.itworld.com/open-source/400175/install-google-chrome-32-browser-linux-mint-16
sudo sh -c 'echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
sudo apt-get update
$INSTALL google-chrome-stable


# SR-Exlusive steps
$INSTALL postgis
$INSTALL postgresql-9.3-postgis-2.1
$INSTALL libgeos-dev
$INSTALL openjdk-7-jdk 

JAVA_DIR=/usr/java
sudo mkdir $JAVA_DIR
sudo ln -s /usr/lib/jvm/java-7-openjdk-amd64/jre $JAVA_DIR/latest

# Get netbeans
wget http://download.netbeans.org/netbeans/7.4/final/bundles/netbeans-7.4-javaee-linux.sh
chmod +x ./netbeans-7.4-javaee-linux.sh
sudo ./netbeans-7.4-javaee-linux.sh





#Db setup

# *** First you must change methods for each entry in /etc/postgresql/9.3/main/pg_hba.conf to "trust" 
createuser -U postgres -s srpostgres
sudo service postgresql restart
dropdb -U srpostgres smilereminder
createdb -U srpostgres smilereminder
psql smilereminder srpostgres -c "CREATE EXTENSION postgis; ALTER DATABASE smilereminder SET bytea_output TO 'escape';"
cd ~/NetBeansProjects/smilereminder3/dbSmileReminder/
psql smilereminder srpostgres -f sr_schema_data.sql
cat db_market* | psql smilereminder srpostgres

# To build/install/run appache
cdsr
sudo mv /bin/sh /bin/sh.bak
sudo ln -s /bin/bash /bin/sh
./build_ant.sh httpd-rpm > ~/httpd_output.log
sudo useradd srapache
sudo alien -i dist/RPMS/x86_64/sr-apache-*
sudo /etc/init.d/srapache start


# It should propmt for input way in the beginning for this information
email="bill@solutionreach.com"

# smilereminder properties file
sudo mkdir /etc/sysconfig
sudo echo "
ApplicationsEmail=<your email address>
ApplicationsEmail.logoLava=<your email address>
appPatron.context=/appPatron/
appPatron.host=<your hostname>.communitect.com
appProfile.host=<your hostname>.communitect.com
#appSr.video.path=/opt/srtomcat/webapps/ROOT/flash/
appSync.support.emailAddress=<your email address>
appSubfiles.path=/home/<your home directory>/NetBeansProjects/smilereminder3/appSubfiles/build/web/

appSubfiles.host=<your hostname>.communitect.com
appSubfiles.vlink.host=<your hostname>.communitect.com
appSubscriber.host=<your hostname>.communitect.com

appSubscriber.path=/home/<your home directory>/NetBeansProjects/smilereminder3/appSubscriber/build/web/
appVideo.streams=<your hostname>.communitect.com/appVideo
appVideo.streams.path=/home/<your home directory>/NetBeansProjects/smilereminder3/appVideo/build/web/streams/
appVideo.video.path=/home/<your home directory>/NetBeansProjects/smilereminder3/appVideo/build/web/video/
appVideo.video.web=<your hostname>.communitect.com/appVideo/video/
CareCredit.SubscriberIdx=6413
CareCredit.reports.startDate=July 1, 2011
CustomerAdmin=<your email address>
Development.user=true
dtd.context=/sr
#dtd.path=/opt/srtomcat/webapps/sr/dtd/
dtd.path=/home/<your home directory>/NetBeansProjects/smilereminder3/appSubscriber/build/web/dtd/
#dtd.path=/home/<your home directory>/NetBeansProjects/smilereminder3/appEnterprise/build/web/dtd/
info.alert=<your email address>
Listener.Birthday=true
Listener.Campaign=true
Listener.Cleanup=true
Listener.DeviceChangeReport=false
Listener.Email=false
Listener.Framework=true
Listener.Geocode=false
Listener.Invisalign=false
Listener.PortalMemo=false
Listener.Recare=false
Listener.Reminder=true
Listener.SendObject=true
Listener.Social=false
Listener.Survey=false
Listener.Testimonial=true
Listener.VoiceReminderLauncher=true
PortalMemo.fromAddress=no-reply@<your hostname>.communitect.com
www.host=<your hostname>.communitect.com
# For voice
# Points to the URL from which to stream the recorded messages.
voice.inbox.web.url=https://localhost/appVoice/inboxes/
# You can override this one if you want to the actual directory.
# Unless you run Asterisk you won't be recording any messages.
voice.inbox.path=/opt/srtomcat/webapps/appVoice/inboxes
# This one is optional-- the sample will likely be the same on production as it is locally.
voice.sample.url=https://localhost/subfiles/1/voice/samples
# Tells the app where the Asterisk server is located.
asterisk.server.host=localhost
# Veracity usually gives us about four channels for a test account.
asterisk.server.max_channels=4
# For email processing
CustomerServiceEmail=<your email address>
#SmileReminderLogoPath=/opt/srtomcat/webapps/sr/images/logo_sr.gif
SmileReminderLogoPath=/home/<your home directory>/NetBeansProjects/smilereminder3/appSubscriber/build/web/images/logo_sr.gif

# EULA and BAA agreement settings

baa.error.email=<your email address>
" >  /etc/sysconfig/smilereminder
sudo sed -i "s/<your email address>/$email/" /etc/sysconfig/smilereminder
sudo sed -i "s/<your hostname>/$HOSTNAME/" /etc/sysconfig/smilereminder
sudo sed -i "s/<your home directory>/$USER/" /etc/sysconfig/smilereminder

sudo echo "
log4j.rootLogger=INFO, console
 
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d [%t] %-5p %c - %m%n
" >  /opt/apache-tomcat-7.0.41/lib/log4j.properties

sudo mkdir /opt/secure_files
sudo chmod a+w /opt/secure_files
