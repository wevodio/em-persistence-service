package com.abnamro.empersistenceservice.controller;

import com.abnamro.empersistenceservice.generated.model.CreateUpdateEmployeeRequest;
import com.abnamro.empersistenceservice.generated.model.GetEmployeeResultOk;
import com.abnamro.empersistenceservice.presenter.JsonEmployeePresenter;
import com.abnamro.empersistenceservice.usecase.ManageEmployeeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class TestController {

    private ManageEmployeeUseCase manageEmployeeUseCase;

    @GetMapping("/api/test")
    public String testEndpoint() {
        return "Test endpoint works!";
    }

    @PostMapping("/api/test/employee")
    public ResponseEntity<GetEmployeeResultOk> createEmployee(@RequestBody CreateUpdateEmployeeRequest createUpdateEmployeeRequest) {
        var presenter = new JsonEmployeePresenter();
        manageEmployeeUseCase.createEmployee(createUpdateEmployeeRequest, presenter);
        return presenter.toResponseEntity();
    }
}

