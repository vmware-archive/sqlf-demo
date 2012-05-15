DROP database SQLFDEMO;
DROP database SQLFDEMO_TEST;
FLUSH PRIVILEGES;

CREATE database SQLFDEMO;
CREATE database SQLFDEMO_TEST;

USE SQLFDEMO;

source schema.sql;

USE SQLFDEMO_TEST;

source schema.sql;


