DROP USER 'sqlfdemo'@'localhost';
CREATE USER 'sqlfdemo'@'localhost' IDENTIFIED BY 'sqlfdemo';
GRANT ALL PRIVILEGES ON *.* TO 'sqlfdemo'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;