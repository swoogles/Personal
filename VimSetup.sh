git submodule init 
git submodule update --recursive
cp -R .vim* ~/
cd ~/.vim/bundle/command-t
rake make
