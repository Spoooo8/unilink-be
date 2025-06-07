package com.unilink.quiz_service.repository;

import com.unilink.quiz_service.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

    Boolean existsByScoreAndIsDeactivated(Integer score, boolean b);

    Optional<Level> findByScoreAndIsDeactivated(Integer score, boolean b);

    Optional<Level> findByIdAndIsDeactivated(Long levelId, boolean b);

    List<Level> findByIsDeactivated(boolean b);
}