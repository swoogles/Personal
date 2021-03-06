INSTALL="sudo yum install"

$INSTALL evince
sudo yum groupinstall 'Development tools'
$INSTALL -y ncurses-devel readline-devel openssl-devel zlib-devel rpm-build autoconf libtool gcc-c++
sudo yum update
sudo yum upgrade
$INSTALL java-1.7.0-openjdk-devel
$INSTALL cscope
$INSTALL dia
$INSTALL google-chrome
$INSTALL chrome
$INSTALL R-dev
$INSTALL R-devel
man yum-update
sudo yum info R-devel
sudo yum repolist
sudo yum history
man yum
$INSTALL grc
$INSTALL xcb
$INSTALL xcb-util-devel
$INSTALL pango-devel
$INSTALL yajl-devel
$INSTALL startup-notification-devel
$INSTALL pcre-devel
$INSTALL ev-devel
$INSTALL ev
$INSTALL libev-devel
yi gthumb
yi eigen
yi lynx
yi virtualbox
yi xclip
yi gimp
yi openoffice
yi libreoffice
yi i3
yi ruby-dev
yi ruby-devel
yi lua-devel
yi python-devel

# Setup RVM
# From:
# https://www.digitalocean.com/community/articles/how-to-install-ruby-2-1-0-on-centos-6-5-using-rvm
sudo yum groupinstall -y development
curl -L get.rvm.io | bash -s stable
rvm reload
rvm install 2.1.0 
rvm install 1.9.3
rvm use 1.9.3 --default


# Install cmake
# To fix this error:
# /lib/ld-linux.so.2: bad ELF interpreter: No such file or directory
sudo yum install glibc.i686

wget http://www.cmake.org/files/v2.8/cmake-2.8.12.2-Linux-i386.sh
# make/make install
# *** THIS IS PROBABLY WRONG ***
# Copy bin files and share/cmake##/Modules directory to /usr/local/bin
