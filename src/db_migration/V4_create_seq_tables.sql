CREATE SEQUENCE monitoring.projects_id_seq;
ALTER TABLE monitoring.projects ALTER COLUMN id SET DEFAULT nextval('monitoring.projects_id_seq');

CREATE SEQUENCE monitoring.modules_id_seq;
ALTER TABLE monitoring.modules ALTER COLUMN id SET DEFAULT nextval('monitoring.modules_id_seq');

CREATE SEQUENCE monitoring.logs_id_seq;
ALTER TABLE monitoring.logs ALTER COLUMN id SET DEFAULT nextval('monitoring.logs_id_seq');