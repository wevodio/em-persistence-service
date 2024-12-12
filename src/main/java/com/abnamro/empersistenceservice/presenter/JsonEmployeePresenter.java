package com.abnamro.empersistenceservice.presenter;

import com.abnamro.empersistenceservice.entities.Employee;
import com.abnamro.empersistenceservice.generated.model.GetEmployeeResultOk;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonEmployeePresenter implements EmployeePresenter {

    private ResponseEntity<GetEmployeeResultOk> responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();

    @Override
    public void success(Employee employee) {
        responseEntity = ResponseEntity.ok(constructGetEmployeeResultOk(employee));
    }

    @Override
    public void failed() {
        responseEntity = ResponseEntity.badRequest().build();
    }

    public ResponseEntity<GetEmployeeResultOk> toResponseEntity() {
        return responseEntity;
    }

    private GetEmployeeResultOk constructGetEmployeeResultOk(Employee employee) {
        var getEmployeeResultOk = new GetEmployeeResultOk();
        getEmployeeResultOk.setId(employee.getId());
        getEmployeeResultOk.setName(employee.getFirstName().concat(" ").concat(employee.getSurname()));
        getEmployeeResultOk.setRoleId(employee.getRole().getId());
        return getEmployeeResultOk;
    }
}
