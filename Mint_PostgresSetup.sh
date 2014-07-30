sudo useradd srpostgres

sudo -u postgres createuser -s $USER
sudo -u postgres createuser -s srpostgres

sudo service postgresql restart
sudo -u srpostgres dropdb -U srpostgres smilereminder
sudo -u srpostgres createdb -U srpostgres smilereminder
sudo -u srpostgres psql smilereminder srpostgres -c "CREATE EXTENSION postgis; ALTER DATABASE smilereminder SET bytea_output TO 'escape';"
cd ~/NetBeansProjects/smilereminder3/dbSmileReminder/
sudo -u srpostgres psql smilereminder srpostgres -f sr_schema_data.sql
cat db_market* | psql smilereminder srpostgres
