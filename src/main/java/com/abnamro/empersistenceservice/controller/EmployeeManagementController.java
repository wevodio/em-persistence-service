package com.abnamro.empersistenceservice.controller;

import com.abnamro.empersistenceservice.generated.api.EmployeePersistenceServiceApi;
import com.abnamro.empersistenceservice.generated.model.*;
import com.abnamro.empersistenceservice.presenter.*;
import com.abnamro.empersistenceservice.usecase.ManageEmployeeUseCase;
import com.abnamro.empersistenceservice.usecase.ManageProjectUseCase;
import com.abnamro.empersistenceservice.usecase.ManageRoleUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmployeeManagementController implements EmployeePersistenceServiceApi {

    private ManageEmployeeUseCase manageEmployeeUseCase;
    private ManageRoleUseCase manageRoleUseCase;
    private ManageProjectUseCase manageProjectUseCase;

    @Override
    public ResponseEntity<GetEmployeeResultOk> apiEmployeesPost(CreateUpdateEmployeeRequest createUpdateEmployeeRequest) {
        var presenter = new JsonEmployeePresenter();
        manageEmployeeUseCase.createEmployee(createUpdateEmployeeRequest, presenter);
        return presenter.toResponseEntity();
    }

    @Override
    public ResponseEntity<SuccessResponse> apiEmployeesIdDelete(Integer id) {
        var presenter = new JsonGenericSuccessPresenter();
        manageEmployeeUseCase.removeEmployee(id, presenter);
        return presenter.toResponseEntity();
    }

    @Override
    public ResponseEntity<GetEmployeeResultOk> apiEmployeesIdPut(Integer id, CreateUpdateEmployeeRequest createUpdateEmployeeRequest) {
        var presenter = new JsonEmployeePresenter();
        manageEmployeeUseCase.updateEmployee(id, createUpdateEmployeeRequest, presenter);
        return presenter.toResponseEntity();
    }

    @Override
    public ResponseEntity<GetEmployeeResultOk> apiEmployeesIdGet(Integer id) {
        var presenter = new JsonEmployeePresenter();
        manageEmployeeUseCase.getEmployeeById(id, presenter);
        return presenter.toResponseEntity();
    }

    @Override
    public ResponseEntity<GetRoleResultOk> apiRolesIdGet(Integer id) {
        var presenter = new JsonRolePresenter();
        manageRoleUseCase.getRole(id, presenter);
        return presenter.toResponseEntity();
    }

    @Override
    public ResponseEntity<SuccessResponse> apiRolesIdDelete(Integer id) {
        var presenter = new JsonGenericSuccessPresenter();
        manageRoleUseCase.removeRole(id, presenter);
        return presenter.toResponseEntity();
    }

    @Override
    public ResponseEntity<GetRoleResultOk> apiRolesPost(CreateUpdateRoleRequest createUpdateRoleRequest) {
        var presenter = new JsonRolePresenter();
        manageRoleUseCase.createRole(createUpdateRoleRequest, presenter);
        return presenter.toResponseEntity();

    }

    @Override
    public ResponseEntity<GetProjectResultOk> apiProjectsIdGet(Integer id) {
        var presenter = new JsonProjectPresenter();
        manageProjectUseCase.getProject(id, presenter);
        return presenter.toResponseEntity();
    }
}
