package com.unilink.project_service.repository.project;

import com.unilink.project_service.entity.project.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
    List<MemberRole> findByProjectId(Long projectId);
}