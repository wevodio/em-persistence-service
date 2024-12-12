package com.abnamro.empersistenceservice.repository;

import com.abnamro.empersistenceservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Procedure(name = "Role.deleteRoleAndReassignProjects")
    void deleteAndReassignProjects(@Param("roleId") Long roleId, @Param("defaultEmployeeId") Long defaultEmployeeId);

}
