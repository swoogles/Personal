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
