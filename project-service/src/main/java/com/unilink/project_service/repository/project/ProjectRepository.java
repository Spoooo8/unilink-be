package com.unilink.project_service.repository.project;

import com.unilink.project_service.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByIsDeactivated(boolean active);
    Optional<Project> findByIdAndIsDeactivated(Long id, boolean active);
    List<Project> findByUserIdAndIsDeactivated(Long userId, boolean isDeactivated);

    List<Project> findAllByTeamIdIn(List<Long> teamIds);

    List<Project> findByTeamIdNotInAndIsDeactivated(Set<Long> teamIds, boolean b);
}