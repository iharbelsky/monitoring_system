--INSERT TEST VALUE INTO projects
INSERT INTO monitoring.projects(name_project, description) VALUES('internet-shop', 'This is internet-shop');
INSERT INTO monitoring.projects(name_project, description) VALUES('films-library', 'This is films-library');

--INSERT TEST VALUE INTO modules
INSERT INTO monitoring.modules(name_module,id_project) VALUES('controller',1);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('service',1);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('DAO',1);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('controller',2);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('service',2);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('DAO',2);
INSERT INTO monitoring.modules(name_module,id_project) VALUES('REPO',2);