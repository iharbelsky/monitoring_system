-- Add column description to table projects
ALTER TABLE monitoring.projects ADD COLUMN description VARCHAR(511) DEFAULT NULL;

-- Update tests projects
UPDATE monitoring.projects SET description='This test project internet-shop' WHERE name_project='internet-shop';
UPDATE monitoring.projects SET description='This test project films-library' WHERE name_project='films-library';