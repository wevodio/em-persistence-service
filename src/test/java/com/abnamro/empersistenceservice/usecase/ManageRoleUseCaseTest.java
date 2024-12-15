package com.abnamro.empersistenceservice.usecase;

import com.abnamro.empersistenceservice.entities.Role;
import com.abnamro.empersistenceservice.exception.ErrorMessageException;
import com.abnamro.empersistenceservice.presenter.JsonEmployeePresenter;
import com.abnamro.empersistenceservice.presenter.JsonGenericSuccessPresenter;
import com.abnamro.empersistenceservice.presenter.JsonRolePresenter;
import com.abnamro.empersistenceservice.presenter.RolePresenter;
import com.abnamro.empersistenceservice.repository.RoleRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Optional;

import static com.abnamro.empersistenceservice.utils.TestUtil.constructCreateUpdateEmployeeRequest;
import static com.abnamro.empersistenceservice.utils.TestUtil.constructCreateUpdateRoleRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManageRoleUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    private ManageRoleUseCase manageRoleUseCase;

    @BeforeEach
    void init(){
        manageRoleUseCase = new ManageRoleUseCase(roleRepository);
    }

    @Test
    void createRole() {
        var presenter = new JsonRolePresenter();
        manageRoleUseCase.createRole(constructCreateUpdateRoleRequest(), presenter);
        assertThat(presenter.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getRoleSuccess() {
        when(roleRepository.findById(anyInt())).thenReturn(
                Optional.of(Role.builder()
                        .id(1)
                        .name("Admin")
                        .build()));
        var jsonRolePresenter = new JsonRolePresenter();
        manageRoleUseCase.getRole(1, jsonRolePresenter);
        assertThat(jsonRolePresenter.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getRoleFailed() {
        when(roleRepository.findById(anyInt())).thenReturn(
                Optional.empty());
        var jsonRolePresenter = new JsonRolePresenter();
        var error = assertThrows(ErrorMessageException.class,
                () -> manageRoleUseCase.getRole(1, jsonRolePresenter));
        assertThat(error.getErrorMessages().get(0)).isEqualTo("Role not found.");
    }

    @Test
    void removeRole(){
        var presenter = new JsonGenericSuccessPresenter();
        manageRoleUseCase.removeRole(1, presenter);
        assertThat(presenter.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}