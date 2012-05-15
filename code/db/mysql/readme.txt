# cd into the data directory (MySQL)
cd [...]

# launch MySQL
sudo mysqld_safe

# login as root
mysql -u root [-p]

# init setup script
source setup.sql;

# setup user, rights
source user-local.sql;

# shutdown, when finished
sudo mysqladmin shutdown



