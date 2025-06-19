package com.unilink.project_service.repository.project;

import com.unilink.project_service.entity.project.Collaborate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollaborateRepository extends JpaRepository<Collaborate, Integer> {
    Collaborate findByIdAndIsDeactivated(Long collaborateId, boolean b);

    List<Collaborate> findByProjectIdAndIsDeactivated(Long projectId, boolean b);
}