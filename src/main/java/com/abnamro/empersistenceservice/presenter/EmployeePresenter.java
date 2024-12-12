package com.abnamro.empersistenceservice.presenter;

import com.abnamro.empersistenceservice.entities.Employee;

public interface EmployeePresenter {

    void success(Employee employee);

    void failed();

}
