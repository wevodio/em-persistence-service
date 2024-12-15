package com.abnamro.empersistenceservice.usecase;

import com.abnamro.empersistenceservice.entities.Role;
import com.abnamro.empersistenceservice.exception.ErrorMessageException;
import com.abnamro.empersistenceservice.generated.model.CreateUpdateRoleRequest;
import com.abnamro.empersistenceservice.presenter.GenericSuccessPresenter;
import com.abnamro.empersistenceservice.presenter.RolePresenter;
import com.abnamro.empersistenceservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ManageRoleUseCase {

    private RoleRepository roleRepository;

    @Transactional
    public void createRole(CreateUpdateRoleRequest createUpdateRoleRequest, RolePresenter rolePresenter){
        var role = Role.builder()
                .name(createUpdateRoleRequest.getName())
                .build();
        roleRepository.save(role);
        rolePresenter.success(role);
    }

    @Transactional
    public void getRole(Integer roleId, RolePresenter rolePresenter){
        roleRepository.findById(roleId)
                .ifPresentOrElse(rolePresenter::success, () -> {
                    throw new ErrorMessageException("Role not found.");
                });
    }

    @Transactional
    public void removeRole(Integer roleId, GenericSuccessPresenter presenter){
        roleRepository.deleteAndReassignProjects(roleId, 1);
        presenter.success("Role deleted successfully");
    }
}
