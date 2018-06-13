--DELETE ALL DATA
DELETE FROM monitoring.logs;
DELETE FROM monitoring.modules;
DELETE FROM monitoring.projects;

--RESTART SEQUENCES
alter sequence monitoring.logs_id_seq restart;
alter sequence monitoring.modules_id_seq restart;
alter sequence monitoring.projects_id_seq restart;
