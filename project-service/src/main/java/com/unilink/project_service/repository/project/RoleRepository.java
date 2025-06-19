package com.unilink.project_service.repository.project;

import com.unilink.project_service.entity.project.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
}