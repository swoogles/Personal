# echo "cats"
git symbolic-ref HEAD 2>/dev/null | cut -d"/" -f 3
