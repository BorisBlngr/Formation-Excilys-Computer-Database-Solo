  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  CREATE USER 'admincdb'@'172.19.0.1' IDENTIFIED BY 'qwerty1234';
  CREATE USER 'admincdb'@'172.19.0.4' IDENTIFIED BY 'qwerty1234';

  GRANT ALL PRIVILEGES ON `computer-database-db-test`.* TO 'admincdb'@'172.19.0.1' WITH GRANT OPTION;
  GRANT ALL PRIVILEGES ON `computer-database-db-test`.* TO 'admincdb'@'172.19.0.4' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
