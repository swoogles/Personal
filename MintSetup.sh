APT="sudo apt-get"
INSTALL="$APT install -y"
$INSTALL vim

$INSTALL ctags
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
$INSTALL scala
$INSTALL mercurial
$INSTALL gnuplot gnuplot-x11
$INSTALL vpnc
$INSTALL openssh-server

# Java 8 steps
sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt-get update
$INSTALL openjdk-8-jdk
sudo update-alternatives --config java

# Node/Angular setup
# From http://developwithguru.com/how-to-install-node-js-and-npm-in-ubuntu-or-mint/
sudo sudo add-apt-repository ppa:chris-lea/node.js #or add-apt-repository ppa:richarvey/nodejs
$APT update
$APT install nodejs 
sudo npm install -g bower
sudo npm install -g grunt
sudo npm install -g grunt-cli


$INSTALL imagemagick
$INSTALL libmagick++-dev


$INSTALL gtk-recordmydesktop
$INSTALL oggvideotools

# Graph Tool package. Discovered this for Python, but it could be useful in C/C++ too.
# Add these lines to /etc/apt/sources.list
DISTRIBUTION=trusty
echo "deb http://downloads.skewed.de/apt/$DISTRIBUTION $DISTRIBUTION universe" >> /etc/apt/sources.list
echo "deb-src http://downloads.skewed.de/apt/$DISTRIBUTION $DISTRIBUTION universe" >> /etc/apt/sources.list
$APT update -y
$INSTALL python3-graph-tool

# Needed for scipy
$INSTALL gfortran libopenblas-dev liblapack-dev
$INSTALL python-dev
$INSTALL python3-pip python3-dev build-essential 
PIP_INSTALL="sudo pip install"
$PIP_INSTALL --upgrade pip 
$PIP_INSTALL --upgrade virtualenv 
# For pygame
$INSTALL libsdl1.2-dev libsmpeg-dev libsdl-mixer1.2-dev libsdl-image1.2-dev libsdl-ttf2.0-dev
# Currently, this requires hitting enter. If I address the last dependencies, this will probably disappear.
$PIP_INSTALL hg+http://bitbucket.org/pygame/pygame
$PIP_INSTALL numpy
$PIP_INSTALL webcolors
$PIP_INSTALL django
$PIP_INSTALL virtualenvwrapper
$PIP_INSTALL cython
$PIP_INSTALL scipy

# For installing RPMs in a better way?
$INSTALL alien

# For instant terminal sharing
sudo apt-get install python-software-properties && \
sudo add-apt-repository ppa:nviennot/tmate      && \
sudo apt-get update                             && \
sudo apt-get install tmate


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
