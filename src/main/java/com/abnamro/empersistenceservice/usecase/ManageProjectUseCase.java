package com.abnamro.empersistenceservice.usecase;

import com.abnamro.empersistenceservice.exception.ErrorMessageException;
import com.abnamro.empersistenceservice.presenter.ProjectPresenter;
import com.abnamro.empersistenceservice.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManageProjectUseCase {

    private final ProjectRepository projectRepository;

    public void getProject(Integer projectId, ProjectPresenter presenter){
        projectRepository.findById(projectId).ifPresentOrElse(presenter::success, () -> {
            throw new ErrorMessageException("Project cant be found");
        });
    }

}
