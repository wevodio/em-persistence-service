package com.abnamro.empersistenceservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.abnamro.empersistenceservice.utils.TestUtil.constructCreateUpdateEmployeeRequest;
import static com.abnamro.empersistenceservice.utils.TestUtil.constructEmployee;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    private EmployeeMapper employeeMapper;

    @BeforeEach
    void init(){
        employeeMapper = new EmployeeMapperImpl();
    }

    @Test
    void testEmployeeMapper(){
        var getRoleResultOkEmployeesInner = employeeMapper.toGetRoleResultOkEmployeesInner(constructEmployee().get());
        assertThat(getRoleResultOkEmployeesInner.getName()).isEqualTo("John Doe");
    }

}