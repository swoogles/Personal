# git log -p \
#   --pretty=format:'{%n "commit": "%H",%n "author": "%an <%ae>",%n "date": "%ad",%n "message": "%f", %n "body": "%B"},' \
#   $@ | \
#   perl -pe 'BEGIN{print "["}; END{print "]\n"}' | \
#   perl -pe 's/},]/}]/'

directory=" --git-dir ${1}.git "
echo $directory
FORMAT='{%n "commit": "%H",%n "author": "%an <%ae>",%n "date": "%ad",%n "message": "%f"%n},' 
FORMAT_OPTION=
# git $directory log | perl -pe 'BEGIN{print "["}; END{print "]\n"}' | perl -pe 's/},]/}]/' #Working without style
git $directory log --pretty=format:'{%n "commit": "%H",%n "author": "%an <%ae>",%n "date": "%ad",%n "message": "%f"%n},'  | perl -pe 'BEGIN{print "["}; END{print "]\n"}' | perl -pe 's/},]/}]/'
# git $FORMAT_OPTION "$directory" log $@ | perl -pe 'BEGIN{print "["}; END{print "]\n"}' | perl -pe 's/},]/}]/'
