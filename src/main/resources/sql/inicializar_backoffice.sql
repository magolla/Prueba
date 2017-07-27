
INSERT INTO d2d_bouser (id,creationDate,email,name,pass,active) VALUES (1,'2017-06-03 00:00:00','admin@doctodoc.com.ar','Admin','$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y',1);

INSERT INTO d2d_role (id,name,description) VALUES (1,'ROLE_ADMIN','Admin');
INSERT INTO d2d_role (id,name,description) VALUES (2,'ROLE_SYSPRO','Sysprops');
INSERT INTO d2d_role (id,name,description) VALUES (3,'ROLE_LOGS','Logs');
INSERT INTO d2d_role (id,name,description) VALUES (4,'ROLE_REPORTS','Reports');
INSERT INTO d2d_role (id,name,description) VALUES (5,'ROLE_EDITOR','Editor');

INSERT INTO d2d_bouser_role (USER_ID,ROLE_ID) VALUES (1,1);

