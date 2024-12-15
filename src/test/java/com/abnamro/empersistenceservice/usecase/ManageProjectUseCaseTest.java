package com.abnamro.empersistenceservice.usecase;

import com.abnamro.empersistenceservice.entities.Project;
import com.abnamro.empersistenceservice.exception.ErrorMessageException;
import com.abnamro.empersistenceservice.presenter.JsonProjectPresenter;
import com.abnamro.empersistenceservice.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManageProjectUseCaseTest {

    @Mock
    private ProjectRepository projectRepository;
    private ManageProjectUseCase manageProjectUseCase;


    @BeforeEach
    void init(){
        manageProjectUseCase = new ManageProjectUseCase(projectRepository);
    }

    @Test
    void testGetProjectSuccess(){
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(Project.builder()
                        .id(1)
                        .name("Project Sunrise")
                        .build()));
        var jsonProjectPresenter = new JsonProjectPresenter();
        manageProjectUseCase.getProject(1, jsonProjectPresenter);
        assertThat(jsonProjectPresenter.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetProjectFailed(){
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        var jsonProjectPresenter = new JsonProjectPresenter();
        var error = assertThrows(ErrorMessageException.class,
                () -> manageProjectUseCase.getProject(1, jsonProjectPresenter));

        assertThat(error.getErrorMessages().get(0)).isEqualTo("Project cant be found");
    }



}