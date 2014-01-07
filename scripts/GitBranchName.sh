# echo "cats"
if [ -e .git ];
then
  git symbolic-ref HEAD 2>/dev/null | cut -d"/" -f 3
fi
