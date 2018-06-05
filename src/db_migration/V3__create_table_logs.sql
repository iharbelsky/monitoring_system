CREATE TABLE monitoring.logs(
id INT PRIMARY KEY,
id_module INT,
text_log JSON,
FOREIGN KEY (id_module) REFERENCES monitoring.modules(id)
)