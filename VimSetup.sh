# After getting current vim source...
./configure --with-features=huge --enable-pythoninterp --enable-rubyinterp --with-tlib=ncurses \
            --enable-pythoninterp \
            --enable-python3interp

sudo apt-get install libncurses5-dev grc

git submodule update --init --recursive
cp -R .vim* ~/
cd ~/.vim/bundle/command-t
gem install bundler
bundle install #Get updated Ruby gems
rake make
cd ~/.vim/bundle/YouCompleteMe
./install.sh
