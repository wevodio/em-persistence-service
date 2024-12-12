DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS project;

CREATE TABLE role (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE project (
    id INT PRIMARY KEY,
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

INSERT INTO role (id, name) VALUES
 (1, 'ADMIN'),
 (2, 'USER'),
 (3, 'MANAGER');

INSERT INTO project (id, name) VALUES
  (1, 'Project Odyssey'),
  (2, 'Project Spectrum'),
  (3, 'Project Media'),
  (4, 'Project Finance Integration'),
  (5, 'Project Migration');
