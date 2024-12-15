DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS project;

CREATE TABLE role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE project (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    surname VARCHAR(255),
    project_id VARCHAR(255),
    role_id VARCHAR(255),

    CONSTRAINT fk_employee_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE SET NULL,
    CONSTRAINT fk_employee_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE SET NULL
);

CREATE INDEX prj_index ON employee(project_id);
CREATE INDEX role_index ON employee(role_id);



INSERT INTO role (name) VALUES
 ('ADMIN'),
 ('USER'),
 ('MANAGER');

INSERT INTO project (name) VALUES
  ('Project Odyssey'),
  ('Project Spectrum'),
  ('Project Media'),
  ('Project Finance Integration'),
  ('Project Migration');

-- create default employee
INSERT INTO employee (first_name, surname, project_id, role_id) VALUES
    ('Default', 'Employee', '1', '1');

CREATE ALIAS DELETE_ROLE_AND_REASSIGN_PROJECTS
FOR "com.abnamro.empersistenceservice.storedprocedure.RoleStoredProcedure.deleteRoleAndReassignProjects";
