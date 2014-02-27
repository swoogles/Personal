git clone git@github.com:swoogles/Personal.git
cd Personal
git submodule init
git submodule update
cp -R .vim* ~/
cd ~/.vim/bundle/command-t
rake make
