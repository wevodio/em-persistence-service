package com.abnamro.empersistenceservice.presenter;

import com.abnamro.empersistenceservice.entities.Project;
import com.abnamro.empersistenceservice.entities.Role;
import com.abnamro.empersistenceservice.generated.model.GetRoleResultOk;
import com.abnamro.empersistenceservice.generated.model.GetRoleResultOkEmployeesInner;
import com.abnamro.empersistenceservice.mapper.EmployeeMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class JsonRolePresenter implements RolePresenter {

    private ResponseEntity<GetRoleResultOk> responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();

    @Override
    public void success(Role role) {
        responseEntity = ResponseEntity.ok(constructGetRoleResultOk(role));
    }

    @Override
    public void failed() {
        responseEntity = ResponseEntity.badRequest().build();
    }

    public ResponseEntity<GetRoleResultOk> toResponseEntity() {
        return responseEntity;
    }

    private GetRoleResultOk constructGetRoleResultOk(Role role) {
        var getRoleResultOk = new GetRoleResultOk();
        getRoleResultOk.setId(role.getId());
        getRoleResultOk.setName(role.getName());
        getRoleResultOk.setEmployees(convertToEmployeesList(role));
        return getRoleResultOk;
    }

    private List<GetRoleResultOkEmployeesInner> convertToEmployeesList(Role role) {
        if(role.getEmployees() != null){
            return role.getEmployees().stream()
                    .map(Mappers.getMapper(EmployeeMapper.class)::toGetRoleResultOkEmployeesInner)
                    .toList();
        } else {
            return Collections.emptyList();
        }

    }
}
