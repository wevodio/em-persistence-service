package com.abnamro.empersistenceservice.controller;

import com.abnamro.empersistenceservice.generated.model.CreateUpdateEmployeeRequest;
import com.abnamro.empersistenceservice.presenter.EmployeePresenter;
import com.abnamro.empersistenceservice.presenter.GenericSuccessPresenter;
import com.abnamro.empersistenceservice.repository.EmployeeRepository;
import com.abnamro.empersistenceservice.usecase.ManageEmployeeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.abnamro.empersistenceservice.utils.TestUtil.constructCreateUpdateEmployeeRequest;
import static com.abnamro.empersistenceservice.utils.TestUtil.constructEmployee;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class EmployeeManagementControllerTest {

    @Mock
    private ManageEmployeeUseCase manageEmployeeUseCase;
    private EmployeeManagementController controller;


    @BeforeEach
    void setup() {
        controller = new EmployeeManagementController(manageEmployeeUseCase);
    }

    @Test
    void apiEmployeesIdDelete() {
        doAnswer(invocation -> {
            var presenter = (GenericSuccessPresenter)invocation.getArgument(1);
            presenter.success("Employee deleted successfully");
            return null;
        }).when(manageEmployeeUseCase).removeEmployee(anyInt(), any(GenericSuccessPresenter.class));

        var getEmployeeResultOkResponseEntity = controller.apiEmployeesIdDelete(1);
        assertThat(getEmployeeResultOkResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void apiEmployeesIdGet() {
        doAnswer(invocation -> {
            var presenter = (EmployeePresenter)invocation.getArgument(1);
            presenter.success(constructEmployee().get());
            return null;
        }).when(manageEmployeeUseCase).getEmployeeById(anyInt(), any(EmployeePresenter.class));

        var getEmployeeResultOkResponseEntity = controller.apiEmployeesIdGet(1);
        assertThat(getEmployeeResultOkResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void apiEmployeesIdPut() {
        doAnswer(invocation -> {
            var presenter = (EmployeePresenter)invocation.getArgument(2);
            presenter.success(constructEmployee().get());
            return null;
        }).when(manageEmployeeUseCase).updateEmployee(anyInt(), any(CreateUpdateEmployeeRequest.class), any(EmployeePresenter.class));

        var result = controller.apiEmployeesIdPut(1, constructCreateUpdateEmployeeRequest());
        assertThat(result.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void apiEmployeesIdPost() {
        doAnswer(invocation -> {
            var presenter = (EmployeePresenter)invocation.getArgument(1);
            presenter.success(constructEmployee().get());
            return null;
        }).when(manageEmployeeUseCase).createEmployee(any(CreateUpdateEmployeeRequest.class), any(EmployeePresenter.class));

        var result = controller.apiEmployeesPost(constructCreateUpdateEmployeeRequest());
        assertThat(result.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }




}