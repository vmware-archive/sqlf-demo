# ******************************************************************
# Start SQLFire launch sqlf
# ******************************************************************

# cd into the data directory (SQLFire)
cd [...]

# launch SQLFire
./startServer.sh

# log in
sqlf

# ******************************************************************
# Create application DB and its tables
# ******************************************************************

# connect
connect client 'localhost:1528';

# run ddl with db sync options
run 'schema-with-dbsync.sql';

# exit
exit;


