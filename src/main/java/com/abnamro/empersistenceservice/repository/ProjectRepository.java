package com.abnamro.empersistenceservice.repository;

import com.abnamro.empersistenceservice.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
