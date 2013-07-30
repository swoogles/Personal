targetHost="$1"
cat ~/.ssh/id_rsa.pub | ssh $USER@$targetHost 'cat >> .ssh/authorized_keys'
