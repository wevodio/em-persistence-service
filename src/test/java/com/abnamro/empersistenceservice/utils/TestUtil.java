package com.abnamro.empersistenceservice.utils;

import com.abnamro.empersistenceservice.entities.Employee;
import com.abnamro.empersistenceservice.entities.Role;
import com.abnamro.empersistenceservice.generated.model.CreateUpdateEmployeeRequest;

import java.util.Optional;

public class TestUtil {

    public static CreateUpdateEmployeeRequest constructCreateUpdateEmployeeRequest(){
        var createUpdateEmployeeRequest = new CreateUpdateEmployeeRequest();
        createUpdateEmployeeRequest.setName("John Doe");
        createUpdateEmployeeRequest.setRoleId(1);
        return createUpdateEmployeeRequest;
    }

    public static Optional<Employee> constructEmployee() {
        return Optional.of(Employee.builder()
                .firstName("John")
                .surname("Doe")
                .id(1)
                .role(Role.builder().id(1).build())
                .build());
    }

}
