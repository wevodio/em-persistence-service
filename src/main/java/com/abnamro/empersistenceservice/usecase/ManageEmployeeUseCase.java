package com.abnamro.empersistenceservice.usecase;

import com.abnamro.empersistenceservice.entities.Employee;
import com.abnamro.empersistenceservice.entities.Role;
import com.abnamro.empersistenceservice.exception.ErrorMessageException;
import com.abnamro.empersistenceservice.generated.model.CreateUpdateEmployeeRequest;
import com.abnamro.empersistenceservice.presenter.EmployeePresenter;
import com.abnamro.empersistenceservice.presenter.GenericSuccessPresenter;
import com.abnamro.empersistenceservice.repository.EmployeeRepository;
import com.abnamro.empersistenceservice.repository.ProjectRepository;
import com.abnamro.empersistenceservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ManageEmployeeUseCase {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;

    public void getEmployeeById(Integer id, EmployeePresenter employeePresenter){
        employeeRepository.findById(id)
                .ifPresentOrElse(employeePresenter::success, () -> {
                    throw new ErrorMessageException("Employee not found.");
                });
    }

    @Transactional
    public void createEmployee(CreateUpdateEmployeeRequest createUpdateEmployeeRequest, EmployeePresenter employeePresenter){
        validateRequest(createUpdateEmployeeRequest);
        try {
            Employee employee = constructEmployee(createUpdateEmployeeRequest);
            employeeRepository.save(employee);
            employeePresenter.success(employee);
        } catch (Exception exception){
            log.error(exception.getMessage(), exception);
            throw new ErrorMessageException("Creation of a new employee failed.");
        }
    }

    private Employee constructEmployee(CreateUpdateEmployeeRequest createUpdateEmployeeRequest) {
        var names = createUpdateEmployeeRequest.getName().split(" ");
        var employee = Employee.builder()
                .firstName(names[0])
                .surname(names[1])
                .build();

        roleRepository.findById(createUpdateEmployeeRequest.getRoleId())
                .ifPresentOrElse(role -> {
                    role.getEmployees().add(employee);
                    employee.setRole(role);
                }, () -> {
                    throw new ErrorMessageException("Role doesnt exist.");
                });

        createUpdateEmployeeRequest.getProjectId()
                .flatMap(projectId -> projectRepository.findById(projectId))
                .ifPresent(project -> {
                    project.getEmployees().add(employee);
                    employee.setProject(project);
                });

        return employee;
    }

    public void updateEmployee(Integer employeeId, CreateUpdateEmployeeRequest createUpdateEmployeeRequest, EmployeePresenter presenter){
        validateRequest(createUpdateEmployeeRequest);
        employeeRepository.findById(employeeId).ifPresentOrElse(employee -> {
            populateAndPersistEmployee(createUpdateEmployeeRequest, employee);
            presenter.success(employee);
        }, () -> {
            throw new ErrorMessageException("Employee not found.");
        });
    }

    private void populateAndPersistEmployee(CreateUpdateEmployeeRequest createUpdateEmployeeRequest, Employee employee) {
        var names = createUpdateEmployeeRequest.getName().split(" ");
        employee.setFirstName(names[0]);
        employee.setSurname(names[1]);
        Role role = roleRepository.findById(createUpdateEmployeeRequest.getRoleId())
                .orElseThrow(() -> new ErrorMessageException("Role doesnt exist."));
        employee.setRole(role);
        employeeRepository.save(employee);
    }

    public void removeEmployee(Integer employeeId, GenericSuccessPresenter presenter){
        employeeRepository.findById(employeeId)
                .ifPresentOrElse(employeeRepository::delete, () -> {
            throw new ErrorMessageException("Removing employee failed. Employee not found.");
        });
        presenter.success("Employee deleted successfully");

    }

    private void validateRequest(CreateUpdateEmployeeRequest createUpdateEmployeeRequest){
        if(StringUtils.isBlank(createUpdateEmployeeRequest.getName())
                || !createUpdateEmployeeRequest.getName().contains(" ")
                || createUpdateEmployeeRequest.getName().split(" ").length != 2){
            throw new ErrorMessageException("First name and surname should be provided, seperated by a space");
        }
        if(createUpdateEmployeeRequest.getRoleId() == null){
            throw new ErrorMessageException("Role id is mandatory");
        }
    }

}
