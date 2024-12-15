package com.abnamro.empersistenceservice.presenter;

import com.abnamro.empersistenceservice.entities.Project;

public interface ProjectPresenter {

    void success(Project project);

    void failed();

}
