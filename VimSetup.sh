# After getting current vim source...
# ./configure --with-features=huge --enable-pythoninterp --enable-rubyinterp --with-tlib=ncurses > ~/junk.txt

git submodule update --init --recursive
cp -R .vim* ~/
cd ~/.vim/bundle/command-t
rake make
cd ~/.vim/bundle/YouCompleteMe
./install.sh
