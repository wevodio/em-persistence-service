package com.abnamro.empersistenceservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedStoredProcedureQuery(
        name = "Role.deleteRoleAndReassignProjects",
        procedureName = "DeleteRoleAndReassignProjects",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "roleId", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "defaultEmployeeId", type = Long.class)
        }
)
public class Role {

    @Id
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<Employee> employees;


}
