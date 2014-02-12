# EASIER SDL/SDL_image instrunctions
yi SDL_image-devel
    # # SDL
    # mkdir ~/SDL
    # cd ~/SDL
    # wget http://www.libsdl.org/release/SDL2-2.0.1.tar.gz
    # tar xzfv SDL2-2.0.1.tar.gz
    # cd SDL2-2.0.1
    # ./configure && make && make install

    # # SDL_image
    # cd ~/SDL
    # wget https://www.libsdl.org/projects/SDL_image/release/SDL2_image-2.0.0.tar.gz
    # tar xzfv SDL2_image-2.0.0.tar.gz
    # cd SDL2_image-2.0.0
    # ./configure && make && make install

# PCRE
mkdir ~/PCRE
cd ~/PCRE
wget ftp://ftp.csx.cam.ac.uk/pub/software/programming/pcre/pcre-8.34.tar.gz
tar xzfv pcre-8.34.tar.gz
cd pcre-8.34
./configure && make && make install

# Freetype
mkdir ~/Freetype
cd ~/Freetype
wget http://download.savannah.gnu.org/releases/freetype/freetype-2.5.2.tar.gz
tar xzfv freetype-2.5.2.tar.gz
cd freetype-2.5.2
./configure && make && make install

# Glew
cd ~/Repositories/External
git clone https://github.com/nigels-com/glew.git glew
cd glew
# Got help from here: http://sourceforge.net/p/glew/bugs/196/
# You need to copy the src and include directories from a tarball 
# provided here: http://sourceforge.net/projects/glew/files/glew/1.10.0/glew-1.10.0.tgz/download
# into the repo.
#
# After this, the project still wouldn't make. 
# Error:
#   /usr/bin/ld: cannot find -lXmu
#   collect2: ld returned 1 exit status
#
#
yi libXmu-devel
yi libXi-devel
yi libGL-devel
make && make install

# GLU
yi mesa-libGLU-devel

# GLUT (MAYBE UNECESSARY)
mkdir ~/GLUT
cd ~/GLUT
wget http://www.opengl.org/resources/libraries/glut/glut-3.7.tar.gz
tar xzvf glut-3.7.tar.gz
cd glut-3.7
yi xmkmf
./mkmkfiles.imake

mkdir ~/GML
cd ~/GML
wget http://sourceforge.net/projects/ogl-math/files/glm-0.9.5.2/glm-0.9.5.2.zip
unzip glm-0.9.5.2.zip
cd glm-0.9.5.2
  # ALl this might be unecessary
    # mkdir ~/Xmu
    # cd ~/Xmu
    # wget http://xorg.freedesktop.org/releases/individual/lib/libXmu-1.1.2.tar.bz2
    # tar xjvf libXmu-1.1.2.tar.bz2

    # # Still couldn't make Xmu, so I tried getting xextproto
    # mkdir ~/XextProto
    # cd ~/XextProto
    # wget http://cgit.freedesktop.org/xorg/proto/xextproto/snapshot/xextproto-7.3.0.tar.gz

    # # NOW I CAN'T INSTALL XextPro EITHER, so I'm trying to get xorg-macros
    # mkdir ~/XorgMacros
    # cd ~/XorgMacros
    # wget http://cgit.freedesktop.org/xorg/util/macros/snapshot/util-macros-1.18.0.tar.gz
    # cd util-macros-1.18.0
    # ./autogen.sh
    # ./configure && make && make install

# Boost Filesystem (All of boost actually)
mkdir ~/Boost
cd ~/Boost
wget http://sourceforge.net/projects/boost/files/boost/1.55.0/boost_1_55_0.tar.gz
tar xzvf boost_1_55_0.tar.gz
cd boost_1_55_0

# TinyXML
mkdir ~/TinyXML
cd ~/TinyXML
wget http://sourceforge.net/projects/tinyxml/files/latest/download
unzip tinyxml_2_6_2.zip
cd tinyxml_2_6_2

# Building Gource
# configure: error: Could not link against boost_system-mt !
# Answered here: https://groups.google.com/forum/#!topic/scribe-server/E8dUF4T3CZM
cd ~/Repositories/External/Gource
./configure --withboost=/usr/lib64 #Working

