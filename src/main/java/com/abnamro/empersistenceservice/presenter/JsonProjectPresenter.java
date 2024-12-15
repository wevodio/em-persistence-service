package com.abnamro.empersistenceservice.presenter;

import com.abnamro.empersistenceservice.entities.Employee;
import com.abnamro.empersistenceservice.entities.Project;
import com.abnamro.empersistenceservice.generated.model.GetProjectResultOk;
import com.abnamro.empersistenceservice.generated.model.GetRoleResultOkEmployeesInner;
import com.abnamro.empersistenceservice.mapper.EmployeeMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class JsonProjectPresenter implements ProjectPresenter {

    private ResponseEntity<GetProjectResultOk> responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();

    @Override
    public void success(Project project) {
        responseEntity = ResponseEntity.ok(constructGetEmployeeResultOk(project));
    }

    @Override
    public void failed() {
        responseEntity = ResponseEntity.badRequest().build();
    }

    public ResponseEntity<GetProjectResultOk> toResponseEntity() {
        return responseEntity;
    }

    private GetProjectResultOk constructGetEmployeeResultOk(Project project) {
        var getProjectResultOk = new GetProjectResultOk();
        getProjectResultOk.setId(project.getId());
        getProjectResultOk.setName(project.getName());
        getProjectResultOk.setEmployees(convertToEmployeesList(project));
        return getProjectResultOk;
    }

    private List<GetRoleResultOkEmployeesInner> convertToEmployeesList(Project project) {
        if(project.getEmployees() != null){
            return project.getEmployees().stream()
                    .map(Mappers.getMapper(EmployeeMapper.class)::toGetRoleResultOkEmployeesInner)
                    .toList();
        } else {
            return Collections.emptyList();
        }

    }


}
