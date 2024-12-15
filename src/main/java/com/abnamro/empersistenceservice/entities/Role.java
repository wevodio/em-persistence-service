package com.abnamro.empersistenceservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NamedStoredProcedureQuery(
        name = "Role.deleteRoleAndReassignProjects",
        procedureName = "DELETEROLEANDREASSIGNPROJECTS",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "roleId", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "defaultEmployeeId", type = Long.class)
        }
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy="role", cascade = CascadeType.PERSIST)
    private Set<Employee> employees;


}
