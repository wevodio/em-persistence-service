package com.abnamro.empersistenceservice.controller;

import com.abnamro.empersistenceservice.generated.api.EmployeePersistenceServiceApi;
import com.abnamro.empersistenceservice.generated.model.CreateUpdateEmployeeRequest;
import com.abnamro.empersistenceservice.generated.model.GetEmployeeResultOk;
import com.abnamro.empersistenceservice.generated.model.SuccessResponse;
import com.abnamro.empersistenceservice.presenter.JsonEmployeePresenter;
import com.abnamro.empersistenceservice.presenter.JsonGenericSuccessPresenter;
import com.abnamro.empersistenceservice.usecase.ManageEmployeeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmployeeManagementController implements EmployeePersistenceServiceApi {

    private ManageEmployeeUseCase manageEmployeeUseCase;

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


}
