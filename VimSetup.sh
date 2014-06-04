git submodule update --init --recursive
cp -R .vim* ~/
cd ~/.vim/bundle/command-t
rake make
cd ~/.vim/bundle/YouCompleteMe
./install.sh
