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


$INSTALL imagemagick
$INSTALL libmagick++-dev


# Ruby setup
# Repo Version
sudo apt-get install -y rake
# RVM version
curl -sSL https://get.rvm.io | bash -s stable --ruby
~/.rvm/bin/rvm install 1.9.3

cd ~/Repositories/Personal
./VimSetup.sh
./MakeLinks.sh

#Alternate bleeding edge install
# git clone https://github.com/b4winckler/vim
# ./configure --enable-cscope --prefix=/usr --with-features=huge --enable-pythoninterp --enable-luainterp

# ** VIM ** 

  #Pathogen Setup
  wget -O ~/.vim/autoload/pathogen.vim https://raw.github.com/tpope/vim-pathogen/master/autoload/pathogen.vim

  #Syntastic Setup
  git clone https://github.com/scrooloose/syntastic.git ~/.vim/bundle

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
sudo apt-get install postgresql libpq-dev
# Change user "postgres" password to empty string
sudo -u postgres psql -c "ALTER USER postgres with encrypted password '';" template1
# Tell it to listen for connections from localhost
sudo sed -i "s/#listen_addresses = 'localhost'/listen_addresses = 'localhost'/" /etc/postgresql/9.3/main/postgresql.conf
# Configuration finished, restart to take effect
sudo /etc/init.d/postgresql restart

wget http://plib.sourceforge.net/dist/plib-1.8.5.tar.gz


# Chrome installation. Instructions from: http://www.itworld.com/open-source/400175/install-google-chrome-32-browser-linux-mint-16
sudo sh -c 'echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
sudo apt-get update
sudo apt-get install google-chrome-stable
