package com.unilink.project_service.repository.project;

import com.unilink.project_service.entity.project.SkillRequired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRequiredRepository extends JpaRepository<SkillRequired, Long> {
    List<SkillRequired> findByProjectIdAndIsDeactivated(Long id, boolean active);
}
