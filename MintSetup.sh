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
$INSTALL scala
$INSTALL mercurial

# Node/Angular setup
# From http://developwithguru.com/how-to-install-node-js-and-npm-in-ubuntu-or-mint/
sudo sudo add-apt-repository ppa:chris-lea/node.js #or add-apt-repository ppa:richarvey/nodejs
sudo apt-get update
sudo apt-get install nodejs 
sudo npm install -g bower


$INSTALL imagemagick
$INSTALL libmagick++-dev

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
