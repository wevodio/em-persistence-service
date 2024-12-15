package com.abnamro.empersistenceservice.mapper;

import com.abnamro.empersistenceservice.entities.Employee;
import com.abnamro.empersistenceservice.generated.model.GetRoleResultOkEmployeesInner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "name", expression = "java(employee.getFirstName() + \" \" + employee.getSurname())")
    GetRoleResultOkEmployeesInner toGetRoleResultOkEmployeesInner(Employee employee);

}
