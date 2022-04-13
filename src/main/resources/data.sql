/* user */
/*
admin/123456
*/
INSERT INTO public.user (id,user_name,password,first_name,last_name,sex,department,phone,email,company,specialty,salute,tutor,faculty,student_number,task_name,task_type,project_code,disabled,lock_status,db_name,birthday,last_login_time,last_login_ip,version,deleted,create_time,create_by,update_time,update_by) VALUES (1,'admin','$2a$10$kABhSPzSXqDf4nX5qHPA4eNzR.JVM3sI/FrAM0rQfrsfAehtPo8sK','admin','admin','male','1','123456','admin@simpledemo.com','c','c','MR',NULL,NULL,NULL,NULL,NULL,NULL,'false','false',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO "user" (id,user_name,password,first_name,last_name,sex,department,phone,email,company,specialty,salute,tutor,faculty,student_number,task_name,task_type,project_code,disabled,lock_status,db_name,birthday,last_login_time,last_login_ip,version,deleted,create_time,create_by,update_time,update_by) VALUES (2,'patrick','$2a$10$kABhSPzSXqDf4nX5qHPA4eNzR.JVM3sI/FrAM0rQfrsfAehtPo8sK','yp','s','male','2','1111','patrick@simpledemo.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'false','false',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL);


INSERT INTO role (id,parent_id,name,description,deleted,create_time,create_by,update_time,update_by) VALUES (1,0,'ROLE_GUEST','guest',0,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role (id,parent_id,name,description,deleted,create_time,create_by,update_time,update_by) VALUES (2,1,'ROLE_USER','user',0,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role (id,parent_id,name,description,deleted,create_time,create_by,update_time,update_by) VALUES (3,2,'ROLE_ADMIN','admin',0,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);


INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (1,0,'VIEW','PAGE','view page x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (2,1,'EDIT','PAGE','edit page x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (3,0,'REGISTER','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (4,0,'SAMPLE','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (5,0,'DEHYDRATE','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (6,0,'EMBED','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (7,0,'SLICE','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (8,0,'DYE','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (9,0,'MOUNT','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (10,0,'DISTRIBUTE','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (11,0,'DIAGNOSE','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (12,0,'REPORT','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (13,0,'ARCHIVE','ACTION','module x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (14,0,'NORMAL','ACTION','flow x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (15,0,'FROZEN','ACTION','flow x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (16,0,'CELL','ACTION','flow x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (17,0,'MOLECULE','ACTION','flow x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO permission (id,parent_id,name,type,description,create_time,create_by,update_time,update_by) VALUES (18,0,'CONSULT','ACTION','flow x','2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);


INSERT INTO user_role (id,user_id,role_id,create_time,create_by,update_time,update_by) VALUES (1,1,3,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO user_role (id,user_id,role_id,create_time,create_by,update_time,update_by) VALUES (2,2,2,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);


INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (1,3,1,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (2,3,2,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (3,3,3,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (4,3,4,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (5,3,5,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (6,3,6,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (7,3,7,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (8,3,8,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (9,3,9,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (10,3,10,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (11,3,11,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (12,3,12,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (13,3,13,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (14,3,14,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (15,3,15,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (16,3,16,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (17,3,17,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (18,3,18,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (19,2,1,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (20,2,2,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (21,2,3,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (22,2,4,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (23,2,5,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (24,2,6,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (25,2,7,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (26,2,8,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (27,2,9,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (28,2,10,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (29,2,11,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (30,2,12,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (31,2,13,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (32,2,14,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (33,2,15,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (34,2,16,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);
INSERT INTO role_permission (id,role_id,permission_id,create_time,create_by,update_time,update_by) VALUES (35,2,17,'2019-01-01 00:00:00',1,'2019-01-01 00:00:00',1);


/*---------------------------------- business entity begin     -----------------------------------*/


/*---------------------------------- business entity end     -----------------------------------*/

COMMIT;
