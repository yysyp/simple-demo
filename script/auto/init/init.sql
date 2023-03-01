#CREATE DATABASE IF NOT EXISTS `testdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
#grant all PRIVILEGES on testdb.* to test@'%' identified by 'test';
#flush privileges;
use testdb;

CREATE TABLE IF NOT EXISTS student_details (
 roll_no int(10) NOT NULL,
 registration_no int(10) NOT NULL AUTO_INCREMENT,
 first_name varchar(50) NOT NULL DEFAULT '',
 last_name varchar(50) DEFAULT '',
 class varchar(10) NOT NULL DEFAULT '',
 marks int(3) NOT NULL DEFAULT '0',
 gender varchar(6) NOT NULL DEFAULT 'male',
 PRIMARY KEY(registration_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

INSERT INTO `student_details` (`roll_no`,`registration_no`,`first_name`,`last_name`,`class`,`marks`,`gender`) VALUES (1,100,'aaa','s','1',1,'1');
INSERT INTO `student_details` (`roll_no`,`registration_no`,`first_name`,`last_name`,`class`,`marks`,`gender`) VALUES (2,101,'bbb','x','1',2,'1');
