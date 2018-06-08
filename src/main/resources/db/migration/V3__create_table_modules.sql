CREATE TABLE monitoring.modules(
  id INT PRIMARY KEY,
  name_module VARCHAR(255),
  id_project INT,
  FOREIGN KEY (id_project) REFERENCES monitoring.projects(id)
)