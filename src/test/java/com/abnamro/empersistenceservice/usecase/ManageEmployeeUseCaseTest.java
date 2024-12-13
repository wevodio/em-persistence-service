package com.abnamro.empersistenceservice.usecase;

import com.abnamro.empersistenceservice.entities.Employee;
import com.abnamro.empersistenceservice.entities.Role;
import com.abnamro.empersistenceservice.exception.ErrorMessageException;
import com.abnamro.empersistenceservice.generated.model.CreateUpdateEmployeeRequest;
import com.abnamro.empersistenceservice.presenter.JsonEmployeePresenter;
import com.abnamro.empersistenceservice.presenter.JsonGenericSuccessPresenter;
import com.abnamro.empersistenceservice.repository.EmployeeRepository;
import com.abnamro.empersistenceservice.repository.RoleRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.abnamro.empersistenceservice.utils.TestUtil.constructCreateUpdateEmployeeRequest;
import static com.abnamro.empersistenceservice.utils.TestUtil.constructEmployee;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManageEmployeeUseCaseTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleRepository roleRepository;

    private ManageEmployeeUseCase manageEmployeeUseCase;

    @BeforeEach
    void setup(){
        manageEmployeeUseCase = new ManageEmployeeUseCase(employeeRepository, roleRepository);
    }

    @Test
    void getEmployeeByIdSuccess() {
        when(employeeRepository.findById(1)).thenReturn(constructEmployee());
        var presenter = new JsonEmployeePresenter();
        manageEmployeeUseCase.getEmployeeById(1, presenter);
        assertThat(presenter.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getEmployeeByIdFailed() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        var presenter = new JsonEmployeePresenter();
        var error = assertThrows(ErrorMessageException.class,
                () -> manageEmployeeUseCase.getEmployeeById(1, presenter));

        assertThat(error.getErrorMessages().get(0)).isEqualTo("Employee not found.");
    }

    @Test
    void createEmployee() {
        var presenter = new JsonEmployeePresenter();
        when(roleRepository.findById(1)).thenReturn(constructRole());
        manageEmployeeUseCase.createEmployee(constructCreateUpdateEmployeeRequest(), presenter);
        assertThat(presenter.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createEmployeeWithNameWithoutSpace() {
        var presenter = new JsonEmployeePresenter();
        var createUpdateEmployeeRequest = constructCreateUpdateEmployeeRequest();
        createUpdateEmployeeRequest.setName("JOHNDOE");
        var error = assertThrows(ErrorMessageException.class,
                () -> manageEmployeeUseCase.createEmployee(createUpdateEmployeeRequest, presenter));
        assertThat(error.getErrorMessages().get(0)).isEqualTo("First name and surname should be provided, seperated by a space");
    }

    @Test
    void createEmployeeWithMoreThanOneSpace() {
        var presenter = new JsonEmployeePresenter();
        var createUpdateEmployeeRequest = constructCreateUpdateEmployeeRequest();
        createUpdateEmployeeRequest.setName("JO HN DOE");
        var error = assertThrows(ErrorMessageException.class,
                () -> manageEmployeeUseCase.createEmployee(createUpdateEmployeeRequest, presenter));
        assertThat(error.getErrorMessages().get(0)).isEqualTo("First name and surname should be provided, seperated by a space");
    }

    @Test
    void createEmployeeWithNoName() {
        var presenter = new JsonEmployeePresenter();
        var createUpdateEmployeeRequest = constructCreateUpdateEmployeeRequest();
        createUpdateEmployeeRequest.setName(null);
        var error = assertThrows(ErrorMessageException.class,
                () -> manageEmployeeUseCase.createEmployee(createUpdateEmployeeRequest, presenter));
        assertThat(error.getErrorMessages().get(0)).isEqualTo("First name and surname should be provided, seperated by a space");
    }

    @Test
    void createEmployeeFailed() {
        var presenter = new JsonEmployeePresenter();
        var createUpdateEmployeeRequest = constructCreateUpdateEmployeeRequest();
        when(roleRepository.findById(1)).thenReturn(constructRole());
        when(employeeRepository.save(any(Employee.class))).thenThrow(ErrorMessageException.class);
        var error = assertThrows(ErrorMessageException.class,
                () -> manageEmployeeUseCase.createEmployee(createUpdateEmployeeRequest, presenter));
        assertThat(error.getErrorMessages().get(0)).isEqualTo("Creation of a new employee failed.");
    }

    @NotNull
    private Optional<Role> constructRole() {
        return Optional.of(Role.builder()
                .id(1)
                .name("ADMIN")
                .build());
    }

    @Test
    void updateEmployee() {
        var createUpdateEmployeeRequest = constructCreateUpdateEmployeeRequest();
        var presenter = new JsonEmployeePresenter();
        when(employeeRepository.findById(1)).thenReturn(constructEmployee());
        when(roleRepository.findById(1)).thenReturn(constructRole());
        manageEmployeeUseCase.updateEmployee(1, createUpdateEmployeeRequest, presenter);
        assertThat(presenter.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateEmployeeNoRoleProdivded() {
        var createUpdateEmployeeRequest = constructCreateUpdateEmployeeRequest();
        createUpdateEmployeeRequest.setRoleId(null);
        var presenter = new JsonEmployeePresenter();
        var error = assertThrows(ErrorMessageException.class,
                () -> manageEmployeeUseCase.updateEmployee(1, createUpdateEmployeeRequest, presenter));
        assertThat(error.getErrorMessages().get(0)).isEqualTo("Role id is mandatory");
    }

    @Test
    void updateEmployeeWhenEmployeeNotFound() {
        var createUpdateEmployeeRequest = constructCreateUpdateEmployeeRequest();
        var presenter = new JsonEmployeePresenter();
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        var error = assertThrows(ErrorMessageException.class,
                () -> manageEmployeeUseCase.updateEmployee(1, createUpdateEmployeeRequest, presenter));
        assertThat(error.getErrorMessages().get(0)).isEqualTo("Employee not found.");
    }

    @Test
    void removeEmployee() {
        var presenter = new JsonGenericSuccessPresenter();
        when(employeeRepository.findById(1)).thenReturn(constructEmployee());
        manageEmployeeUseCase.removeEmployee(1, presenter);
        assertThat(presenter.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void removeEmployeeNotFound() {
        var presenter = new JsonGenericSuccessPresenter();
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        var error = assertThrows(ErrorMessageException.class,
                () -> manageEmployeeUseCase.removeEmployee(1, presenter));
        assertThat(error.getErrorMessages().get(0)).isEqualTo("Removing employee failed. Employee not found.");
    }

}