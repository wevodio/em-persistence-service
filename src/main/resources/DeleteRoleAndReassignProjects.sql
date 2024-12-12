

DELIMITER $$

CREATE PROCEDURE DeleteRoleAndReassignProjects(
    IN roleId BIGINT,
    IN defaultEmployeeId BIGINT
)
BEGIN
    -- Step 1: Check if the default employee exists
    IF NOT EXISTS (SELECT 1 FROM Employee WHERE id = defaultEmployeeId) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Default employee does not exist.';
END IF;

    -- Step 2: Reassign projects of employees with the given role to the default employee
UPDATE Project
SET employee_id = defaultEmployeeId
WHERE employee_id IN (SELECT id FROM Employee WHERE role_id = roleId);

-- Step 3: Delete employees associated with the role
DELETE FROM Employee WHERE role_id = roleId;

-- Step 4: Delete the role
DELETE FROM Role WHERE id = roleId;
END$$

DELIMITER ;
