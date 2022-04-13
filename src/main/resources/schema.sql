/*================================================================================================*/
/**
* http://www.h2database.com/html/datatypes.html#bigint_type
* TODO: Postgresql user should be public.user or "user" to avoid conficts.
* CREATE DATABASE IF NOT EXISTS mypocdemo;
* use mypocdemo
*
**/


/*-----------------------------------Remember Me---------------------------------------------*/
DROP TABLE IF EXISTS persistent_logins;
create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null);

/*---------------------------------- sequence system_config etc -----------------------------------*/
DROP TABLE IF EXISTS my_sequence;
CREATE TABLE my_sequence (
  seq_name VARCHAR(50) NOT NULL,
  min_val BIGINT DEFAULT '1' NOT NULL,
  max_val BIGINT NOT NULL,
  cur_val BIGINT NOT NULL,
  increment INT DEFAULT '1' NOT NULL,
  PRIMARY KEY (seq_name)
) ;


DROP TABLE IF EXISTS reuse_serial_number;
CREATE TABLE reuse_serial_number (
  id SERIAL,
  serial_number VARCHAR(31) NOT NULL,
  type VARCHAR(31),
  PRIMARY KEY (id)
) ;

ALTER TABLE reuse_serial_number ADD CONSTRAINT reuse_serial_number_UNIQUE UNIQUE(serial_number);


/**
system_config
**/

DROP TABLE IF EXISTS system_config;
CREATE TABLE system_config (
id SERIAL,
category VARCHAR(31) DEFAULT '',
item_key VARCHAR(31) DEFAULT '',
item_value VARCHAR(255) DEFAULT '',
item_index INT DEFAULT 0,
flag BIGINT DEFAULT 0,
ext VARCHAR(1023) DEFAULT '',
pid BIGINT DEFAULT 0,
version INT,
deleted         SMALLINT DEFAULT 0,
create_time     TIMESTAMP(6) NULL,
create_by       BIGINT,
update_time     TIMESTAMP(6) NULL,
update_by       BIGINT,
PRIMARY KEY (id)
) ;

/**
store_file
**/
DROP TABLE IF EXISTS store_file;
CREATE TABLE store_file (
id SERIAL,
name VARCHAR(127) DEFAULT '',
type VARCHAR(31) DEFAULT '',
description VARCHAR(255) DEFAULT '',
content BYTEA,
version INT,
deleted         SMALLINT DEFAULT 0,
create_time     TIMESTAMP(6) NULL,
create_by       BIGINT,
update_time     TIMESTAMP(6) NULL,
update_by       BIGINT,
PRIMARY KEY (id)
) ;


/*---------------------------------- user -- role -- permission -----------------------------------*/
/**
user <-N:N-> role <-N:N-> permission
**/

DROP TABLE IF EXISTS role;
CREATE TABLE role (
id SERIAL ,
parent_id BIGINT DEFAULT 0 ,
name VARCHAR(31) DEFAULT '',
description VARCHAR(100) DEFAULT '',
version INT,
deleted         SMALLINT DEFAULT 0,
create_time     TIMESTAMP(6) NULL,
create_by       BIGINT,
update_time     TIMESTAMP(6) NULL,
update_by       BIGINT,
PRIMARY KEY (id)
) ;



DROP TABLE IF EXISTS permission;
CREATE TABLE permission (
id SERIAL ,
parent_id BIGINT DEFAULT 0,
name VARCHAR(31) DEFAULT '',
type VARCHAR(31) DEFAULT '',
description VARCHAR(100) DEFAULT '',
version INT,
deleted         SMALLINT DEFAULT 0,
create_time     TIMESTAMP(6) NULL,
create_by       BIGINT,
update_time     TIMESTAMP(6) NULL,
update_by       BIGINT,
PRIMARY KEY (id)
) ;



DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission (
id SERIAL,
role_id BIGINT,
permission_id BIGINT,
create_time TIMESTAMP NULL,
create_by BIGINT,
update_time TIMESTAMP NULL,
update_by BIGINT,
PRIMARY KEY (id)
/* ,
CONSTRAINT FK_role_permission_role_id FOREIGN KEY (role_id) REFERENCES role (id),
CONSTRAINT FK_role_permission_permission_id FOREIGN KEY (permission_id) REFERENCES permission (id)
*/
) ;


/*Password:123456 '$2a$10$kABhSPzSXqDf4nX5qHPA4eNzR.JVM3sI/FrAM0rQfrsfAehtPo8sK' */
DROP TABLE IF EXISTS public.user;
CREATE TABLE public.user (
id SERIAL ,
user_name       VARCHAR(31) DEFAULT '',
password VARCHAR(255) DEFAULT '',
first_name      VARCHAR(31) DEFAULT '',
last_name VARCHAR(31) DEFAULT '',
sex             VARCHAR(31) DEFAULT '',
department      VARCHAR(31) DEFAULT '',
phone VARCHAR(31) DEFAULT '',
email VARCHAR(31) DEFAULT '',
company         VARCHAR(31) DEFAULT '',
specialty VARCHAR(31) DEFAULT '',
salute          VARCHAR(31) DEFAULT '',
tutor VARCHAR(31) DEFAULT '',
faculty         VARCHAR(31) DEFAULT '',
student_number  VARCHAR(31) DEFAULT '',
task_name VARCHAR(31) DEFAULT '',
task_type       VARCHAR(31) DEFAULT '',
project_code    VARCHAR(31) DEFAULT '',
disabled        VARCHAR(31) DEFAULT 'false',
lock_status     VARCHAR(31) DEFAULT 'false',
db_name         VARCHAR(31) DEFAULT '',
birthday        TIMESTAMP(6) NULL,
last_login_time TIMESTAMP(6) NULL,
last_login_ip   VARCHAR(31) DEFAULT '',
version INT,
deleted         SMALLINT DEFAULT 0,
create_time     TIMESTAMP(6) NULL,
create_by       BIGINT,
update_time     TIMESTAMP(6) NULL,
update_by       BIGINT,
PRIMARY KEY (id)
) ;

ALTER TABLE public.user ADD CONSTRAINT userName_UNIQUE UNIQUE(user_name);
ALTER TABLE public.user ADD CONSTRAINT phone_UNIQUE UNIQUE(phone);
ALTER TABLE public.user ADD CONSTRAINT email_UNIQUE UNIQUE(email);




DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
id SERIAL,
user_id BIGINT,
role_id BIGINT,
create_time TIMESTAMP NULL,
create_by BIGINT,
update_time TIMESTAMP NULL,
update_by BIGINT,
PRIMARY KEY (id)
/* ,
CONSTRAINT FK_user_role_role_id FOREIGN KEY (role_id) REFERENCES role (id),
CONSTRAINT FK_user_role_user_id FOREIGN KEY (user_id) REFERENCES public.user (id)
*/
) ;



/*================================================================================================*/