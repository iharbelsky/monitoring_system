--Create schema
CREATE SCHEMA monitoring;

--Create table (projects, modules, logs)
CREATE TABLE monitoring.projects(
  id INT PRIMARY KEY,
  name_project VARCHAR(255)
);

CREATE TABLE monitoring.modules(
  id INT PRIMARY KEY,
  name_module VARCHAR(255),
  id_project INT,
  FOREIGN KEY (id_project) REFERENCES monitoring.projects(id)
);

CREATE TABLE monitoring.logs(
id INT PRIMARY KEY,
id_module INT,
text_log JSON,
created_at DATE,
FOREIGN KEY (id_module) REFERENCES monitoring.modules(id)
);

--Create sequences (projects, modules, logs)
CREATE SEQUENCE monitoring.projects_id_seq;
ALTER TABLE monitoring.projects ALTER COLUMN id SET DEFAULT nextval('monitoring.projects_id_seq');

CREATE SEQUENCE monitoring.modules_id_seq;
ALTER TABLE monitoring.modules ALTER COLUMN id SET DEFAULT nextval('monitoring.modules_id_seq');

CREATE SEQUENCE monitoring.logs_id_seq;
ALTER TABLE monitoring.logs ALTER COLUMN id SET DEFAULT nextval('monitoring.logs_id_seq');


--Delete all data (projects, modules, logs)
DELETE FROM monitoring.logs;
DELETE FROM monitoring.modules;
DELETE FROM monitoring.projects;

--Restart sequences
alter sequence monitoring.logs_id_seq restart;
alter sequence monitoring.modules_id_seq restart;
alter sequence monitoring.projects_id_seq restart;

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
INSERT INTO monitoring.modules(name_module,id_project) VALUES('REPO',2);

--INSERT TEST VALUE INTO
INSERT INTO monitoring.logs(id_module,text_log,created_at) VALUES(1,'{"text":"Build Success"}','05/06/2018');
INSERT INTO monitoring.logs(id_module,text_log,created_at) VALUES(2,'{"text":"Error"}','05/06/2018');
INSERT INTO monitoring.logs(id_module,text_log,created_at) VALUES(4,'{"text":"Build Success"}','05/06/2018');