find -name "*.cpp" \
  -o -name "*.h"  \
  -o -name "*.py"  \
  -o -name "*.scala"  > cscope.files
cscope -b -q -k
