git submodule update --init --recursive
cp -R .vim* ~/
cd ~/.vim/bundle/command-t
rake make
