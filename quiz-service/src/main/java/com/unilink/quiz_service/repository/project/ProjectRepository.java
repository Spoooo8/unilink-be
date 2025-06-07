package com.unilink.quiz_service.repository.project;

import com.unilink.quiz_service.entity.project.Project;
import com.unilink.quiz_service.utils.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByIsDeactivated(boolean active);
    Optional<Project> findByIdAndIsDeactivated(Long id, boolean active);
    List<Project> findByUserIdAndIsDeactivated(Long userId, boolean isDeactivated);

}
