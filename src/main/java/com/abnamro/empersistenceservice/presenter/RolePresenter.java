package com.abnamro.empersistenceservice.presenter;

import com.abnamro.empersistenceservice.entities.Role;

public interface RolePresenter {

    void success(Role role);

    void failed();

}
