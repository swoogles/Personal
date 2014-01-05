sudo apt-get install vim
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

sudo apt-get install screen
sudo apt-get install g++
sudo apt-get install libboost-all-dev
#Instructions for setting up opengl here: www.wikihow.com/Install-Mesa-(openGL)-on-Linux-Mint
sudo apt-get install freeglut3-dev
sudo apt-get install cmake
sudo apt-get install libglew-dev
sudo apt-get install mesa-common-dev
sudo apt-get install build-essential
sudo apt-get install libglew1.5-dev libglm-dev
sudo apt-get install libxmu-dev
#To solve "could not find working GL lib" error
sudo apt-get install libxi-dev libxine-dev


sudo apt-get install imagemagick
sudo apt-get install libmagick++-dev

wget http://plib.sourceforge.net/dist/plib-1.8.5.tar.gz
