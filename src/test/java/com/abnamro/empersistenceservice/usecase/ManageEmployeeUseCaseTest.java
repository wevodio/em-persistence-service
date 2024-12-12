package com.abnamro.empersistenceservice.usecase;

import com.abnamro.empersistenceservice.entities.Employee;
import com.abnamro.empersistenceservice.entities.Role;
import com.abnamro.empersistenceservice.exception.ErrorMessageException;
import com.abnamro.empersistenceservice.generated.model.CreateUpdateEmployeeRequest;
import com.abnamro.empersistenceservice.presenter.JsonEmployeePresenter;
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

    @NotNull
    private Optional<Employee> constructEmployee() {
        return Optional.of(Employee.builder()
                .firstName("John")
                .surname("Doe")
                .id(1)
                .role(Role.builder().id(1).build())
                .build());
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
    }

    @Test
    void removeEmployee() {
    }

    private CreateUpdateEmployeeRequest constructCreateUpdateEmployeeRequest(){
        var createUpdateEmployeeRequest = new CreateUpdateEmployeeRequest();
        createUpdateEmployeeRequest.setName("John Doe");
        createUpdateEmployeeRequest.setRoleId(1);
        return createUpdateEmployeeRequest;
    }
}