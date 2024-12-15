package com.abnamro.empersistenceservice.storedprocedure;

import java.sql.*;

public class RoleStoredProcedure {

    public static void deleteRoleAndReassignProjects(Connection conn, int roleId, int defaultEmployeeId) throws SQLException {
        try (PreparedStatement reassignProjectsStmt = conn.prepareStatement(
                "UPDATE employee SET project_id = (SELECT project_id FROM employee WHERE role_id = ?) WHERE id = ?")) {
            reassignProjectsStmt.setInt(1, roleId);
            reassignProjectsStmt.setInt(2, defaultEmployeeId);
            reassignProjectsStmt.executeUpdate();
        }

        try (PreparedStatement deleteEmployeesStmt = conn.prepareStatement(
                "DELETE FROM employee WHERE role_id = ?")) {
            deleteEmployeesStmt.setInt(1, roleId);
            deleteEmployeesStmt.executeUpdate();
        }

        try (PreparedStatement deleteRoleStmt = conn.prepareStatement(
                "DELETE FROM role WHERE id = ?")) {
            deleteRoleStmt.setInt(1, roleId);
            deleteRoleStmt.executeUpdate();
        }
    }
}
