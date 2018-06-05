--INSERT TEST VALUE INTO projects
INSERT INTO monitoring.projects(name_project) VALUES('internet-shop');
INSERT INTO monitoring.projects(name_project) VALUES('films-library');

--INSERT TEST VALUE INTO modules
INSERT INTO monitoring.modules(name_module,id_project) VALUES('controller',1);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('service',1);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('DAO',1);

INSERT INTO monitoring.modules(name_module,id_project) VALUES('controller',2);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('service',2);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('DAO',2);

--INSERT TEST VALUE INTO
INSERT INTO monitoring.logs(id_module,text_log,created_at) VALUES(1,'{"text":"Build Success"}','05/06/2018');
INSERT INTO monitoring.logs(id_module,text_log,created_at) VALUES(2,'{"text":"Error"}','05/06/2018');
INSERT INTO monitoring.logs(id_module,text_log,created_at) VALUES(4,'{"text":"Build Success"}','05/06/2018');