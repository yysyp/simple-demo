

create user 'test'@'%' identified by '123456';
GRANT ALL PRIVILEGES ON *.* TO 'test'@'%';
flush privileges;
ALTER USER 'test'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
flush privileges;

use testdb;


CREATE TABLE IF NOT EXISTS `Employee` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
  `EmpId` mediumint,
  `EmpName` varchar(255) default NULL,
  `EmpBOD` varchar(255),
  `EmpJoiningDate` varchar(255),
  `PrevExperience` mediumint default NULL,
  `Salary` mediumint default NULL,
  `Address` varchar(255) default NULL,
  PRIMARY KEY (`id`)
) AUTO_INCREMENT=1;

INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (1,"Laith Perry","Mar 29, 2020","Jan 31, 2019",2,64072,"990-9029 Duis Street");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (2,"Rudyard Coleman","Sep 14, 2019","Oct 15, 2018",1,98374,"7834 Tempus Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (3,"Adam Anthony","Jun 1, 2019","Apr 11, 2020",3,44817,"Ap #608-2097 Ultrices Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (4,"Malcolm Weeks","Dec 5, 2019","Jul 1, 2019",5,13820,"P.O. Box 710, 6880 Lacinia. St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (5,"Reuben Montgomery","Mar 22, 2019","Apr 24, 2019",1,87591,"P.O. Box 620, 1484 Adipiscing Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (6,"Porter Wooten","Dec 19, 2019","Aug 30, 2020",5,60392,"543-3259 Ipsum Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (7,"Amal Ortiz","Dec 7, 2018","May 5, 2019",3,49310,"155 Magna Street");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (8,"Jerome Lewis","May 8, 2019","Jun 20, 2020",6,78689,"4654 Vel Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (9,"Wesley Church","May 24, 2019","Aug 13, 2019",9,16118,"P.O. Box 327, 5041 Metus. Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (10,"Ferdinand Nichols","Apr 21, 2020","Aug 26, 2020",4,51307,"Ap #500-5583 Ipsum. Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (11,"Chandler Curry","Aug 16, 2020","Dec 18, 2018",2,34767,"3362 Inceptos Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (12,"Caleb Patterson","Aug 3, 2020","Jul 5, 2020",3,29678,"7244 Semper Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (13,"Bruce Salas","Oct 17, 2019","Apr 15, 2020",1,45594,"P.O. Box 587, 6844 Sem. Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (14,"Ferdinand Herman","Sep 16, 2019","Mar 2, 2019",2,66991,"Ap #436-613 Feugiat Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (15,"Judah Graves","Feb 19, 2020","Jul 9, 2020",8,15595,"Ap #765-977 Senectus Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (16,"Stewart Tate","Jun 2, 2019","Apr 1, 2019",8,14861,"P.O. Box 665, 8517 Enim. Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (17,"Vance Kim","Mar 9, 2020","Jan 6, 2019",4,88918,"P.O. Box 286, 6066 Orci Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (18,"Caleb Parrish","Apr 15, 2019","Nov 6, 2019",4,38084,"947-6623 Auctor Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (19,"Jesse Clay","Jan 16, 2020","Feb 22, 2020",2,15332,"4789 Rhoncus. St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (20,"Lee Leblanc","Nov 30, 2019","Apr 21, 2020",8,68228,"P.O. Box 179, 2688 Aliquam St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (21,"Xenos Powers","Apr 2, 2019","Aug 19, 2020",6,83031,"Ap #795-9590 Consectetuer St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (22,"Forrest Wiley","Mar 6, 2020","Mar 1, 2020",1,13814,"P.O. Box 327, 6168 Aliquam Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (23,"Burton Leon","Jul 3, 2020","Mar 16, 2019",3,98234,"3167 Rutrum. Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (24,"Benjamin Mcgowan","May 14, 2020","May 15, 2019",2,24450,"P.O. Box 732, 4057 Malesuada Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (25,"Melvin Cherry","Mar 24, 2020","Jun 27, 2019",4,18501,"Ap #900-8183 Tellus, Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (26,"Wallace Mcfadden","Jan 15, 2019","Sep 3, 2019",3,99971,"Ap #395-6277 Scelerisque, Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (27,"Timon Graham","Nov 25, 2019","Jan 15, 2020",10,65079,"P.O. Box 576, 9201 Vitae Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (28,"Gary Holloway","Aug 16, 2020","Aug 20, 2020",3,45830,"Ap #544-6653 Luctus, Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (29,"Murphy Richard","Oct 13, 2020","Mar 10, 2019",1,86270,"582-3044 Lectus. Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (30,"Denton Diaz","Oct 26, 2018","Nov 21, 2019",6,70424,"689-9739 Lorem Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (31,"Erich Reilly","Dec 12, 2018","Mar 31, 2020",5,32165,"P.O. Box 220, 1359 Primis Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (32,"Asher Perez","Jul 4, 2019","Jan 18, 2020",5,64260,"Ap #811-7962 Erat St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (33,"Tanner Sanchez","Sep 29, 2020","Apr 28, 2020",8,47765,"P.O. Box 378, 7803 Rhoncus Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (34,"Ross Barnett","Jun 27, 2020","Aug 6, 2019",3,62991,"4024 Mollis. St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (35,"Ezra Figueroa","Sep 30, 2020","Dec 30, 2019",7,10995,"430-4729 Donec Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (36,"Honorato Adams","Jan 16, 2019","Oct 16, 2019",4,79729,"799-8948 Gravida. Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (37,"Hasad Lambert","Jun 4, 2020","Feb 22, 2019",2,88785,"P.O. Box 587, 8278 Montes, Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (38,"William Orr","Feb 3, 2020","Mar 3, 2019",5,55231,"P.O. Box 512, 1368 Duis Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (39,"Byron Young","Oct 13, 2020","Mar 21, 2020",5,35949,"647-9143 Nulla. Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (40,"Lars Flowers","Aug 13, 2019","Feb 25, 2020",5,32105,"Ap #905-1038 Non Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (41,"Mufutau Daniel","Aug 8, 2020","Sep 28, 2019",1,48805,"7816 Risus. St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (42,"Alec Kim","Mar 28, 2020","Sep 5, 2020",4,70604,"271-2754 Proin Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (43,"Lewis Cortez","Apr 7, 2020","Jun 19, 2019",4,34782,"397-3421 Justo Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (44,"Felix Singleton","Jul 19, 2020","Nov 25, 2019",9,55003,"P.O. Box 116, 1593 Urna Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (45,"Tyler Phillips","Dec 10, 2018","Aug 26, 2020",10,70896,"Ap #359-1684 Nascetur Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (46,"Joshua Barker","Oct 28, 2019","Mar 5, 2019",6,94198,"Ap #968-6141 Sed St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (47,"Wallace Guerra","Jul 3, 2020","Jun 23, 2019",9,66693,"6426 Duis St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (48,"Richard Jacobs","Aug 31, 2020","Mar 6, 2020",2,81254,"P.O. Box 929, 7683 Nunc Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (49,"Abel Wise","Dec 20, 2019","Aug 26, 2020",6,80236,"414 Bibendum Street");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (50,"Allen Mckay","Aug 8, 2020","Mar 3, 2019",2,49610,"141-1578 In Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (51,"Tyler Hopper","Aug 26, 2019","Aug 1, 2020",3,97754,"699 Nam Street");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (52,"Quamar Nichols","Jan 9, 2020","Jul 25, 2020",10,76290,"6008 Lorem Street");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (53,"Clinton Nelson","Jan 26, 2019","Jan 1, 2019",9,76673,"4436 Aptent Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (54,"Garrison Pittman","Jan 22, 2020","Feb 18, 2020",3,93335,"7226 Gravida. St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (55,"Amery Chase","Jun 2, 2020","Feb 14, 2019",3,30917,"Ap #727-4383 Suspendisse Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (56,"Wesley Clemons","Mar 17, 2020","Nov 18, 2019",6,69385,"8368 A Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (57,"Ryder Wheeler","Sep 21, 2019","Feb 22, 2019",6,66367,"P.O. Box 427, 3968 Elit Street");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (58,"Bradley Sharpe","Jul 8, 2019","Feb 12, 2019",5,20603,"Ap #943-1700 Porttitor Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (59,"Clarke Harrington","Mar 22, 2020","May 10, 2020",7,94598,"646 Enim. Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (60,"Carlos Rodriquez","Sep 6, 2020","Jul 24, 2020",3,79534,"112-7434 Tempus Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (61,"Perry Cardenas","Oct 12, 2019","Jan 9, 2019",4,96319,"1625 Adipiscing Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (62,"Kyle Hull","Nov 30, 2019","Feb 13, 2020",2,66204,"Ap #751-3495 Nunc St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (63,"Reece Sykes","Dec 15, 2019","Nov 18, 2019",9,42877,"424-698 Malesuada. Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (64,"Wing Albert","Aug 11, 2019","Oct 26, 2019",9,94334,"4309 Tristique Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (65,"Jin Barton","Aug 15, 2019","Oct 1, 2020",8,13755,"4666 Risus. Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (66,"Fuller Andrews","Aug 10, 2019","Sep 5, 2019",3,74274,"P.O. Box 575, 4918 Sagittis Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (67,"Cameron Henderson","Sep 7, 2019","Jun 12, 2019",10,47413,"Ap #855-7942 Laoreet, Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (68,"Gil Bell","Jun 23, 2019","Sep 22, 2020",2,13258,"P.O. Box 384, 123 Aliquam St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (69,"Gray Blackburn","Jun 16, 2019","Mar 31, 2020",8,31577,"4146 Enim. St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (70,"Oren Dickson","Jul 19, 2020","Sep 30, 2019",5,63426,"1746 Pellentesque St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (71,"Guy Dawson","May 17, 2019","Jun 21, 2019",9,26500,"265-2125 Amet Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (72,"Graiden Mitchell","Apr 29, 2019","Jun 16, 2019",8,84700,"262-4404 Tellus Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (73,"Quentin Hanson","Nov 29, 2018","Feb 23, 2020",6,15229,"112-1572 Vel, Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (74,"Castor Romero","Mar 2, 2019","Oct 5, 2019",7,36180,"5719 At, Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (75,"Xander Ruiz","Feb 4, 2019","Jan 17, 2020",2,20180,"Ap #632-920 In Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (76,"Tate Malone","Jan 30, 2020","Mar 2, 2019",2,92365,"920-8103 Ullamcorper, Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (77,"Hakeem Jenkins","Oct 25, 2018","Sep 10, 2020",3,30377,"995-8939 Et Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (78,"Keane Bush","Jan 1, 2019","Jun 28, 2020",2,66187,"Ap #311-9108 Vehicula St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (79,"Matthew Shelton","Jun 5, 2020","Apr 22, 2020",7,89384,"8542 Amet, Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (80,"Lance Douglas","Jan 13, 2020","Jan 31, 2020",8,28479,"2982 Hendrerit Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (81,"Luke Guy","May 15, 2020","May 2, 2020",8,24839,"2565 A Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (82,"Daniel Sampson","Apr 20, 2019","Apr 14, 2020",10,82987,"9383 Non Avenue");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (83,"Stewart Horne","Jan 2, 2020","Jul 12, 2020",6,23961,"Ap #164-6060 Mauris. St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (84,"Jacob Huff","Mar 9, 2020","Feb 10, 2020",4,10020,"P.O. Box 949, 321 Placerat, St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (85,"Zachery Cummings","Mar 15, 2020","Feb 11, 2020",7,37420,"P.O. Box 859, 4158 Adipiscing St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (86,"Ciaran Cannon","Dec 27, 2018","Feb 16, 2020",3,95978,"P.O. Box 593, 2074 Nullam St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (87,"Allistair Carney","Dec 9, 2018","Jul 19, 2019",10,11434,"317 Quisque Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (88,"Zahir Whitfield","Aug 23, 2020","Oct 30, 2019",6,11874,"P.O. Box 814, 8279 Consectetuer Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (89,"Charles Yates","Oct 25, 2019","Apr 6, 2019",9,99765,"935-7110 Cursus Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (90,"Honorato Galloway","Feb 3, 2019","Dec 18, 2018",1,71561,"Ap #671-7340 Rutrum St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (91,"Jin Combs","Mar 2, 2020","Mar 14, 2019",10,64478,"1235 Sapien, Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (92,"Clinton Hartman","Apr 9, 2019","Aug 8, 2019",6,46889,"Ap #212-590 Semper Ave");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (93,"Travis Doyle","Sep 30, 2020","Jul 23, 2019",6,24886,"Ap #932-1675 Nec Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (94,"Nero Castillo","Oct 6, 2019","Sep 14, 2020",6,97467,"797 Parturient St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (95,"Micah Garza","Dec 10, 2019","Jul 29, 2020",10,95799,"P.O. Box 918, 6214 Nunc Rd.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (96,"Cruz Bradshaw","Jul 13, 2019","Aug 23, 2020",8,86579,"765 Elementum Road");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (97,"Caldwell Marshall","Mar 17, 2019","Apr 10, 2020",10,80762,"Ap #416-9401 Nibh. St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (98,"Barclay Garrison","Aug 29, 2020","Oct 4, 2020",5,34194,"3045 Nisi St.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (99,"Dominic Cole","Oct 14, 2019","Sep 22, 2020",8,14707,"P.O. Box 132, 7773 Nunc Av.");
INSERT INTO `Employee` (`EmpId`,`EmpName`,`EmpBOD`,`EmpJoiningDate`,`PrevExperience`,`Salary`,`Address`) VALUES (100,"Eric Weiss","Mar 14, 2019","Apr 29, 2019",3,92947,"P.O. Box 844, 4902 Cum St.");


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
