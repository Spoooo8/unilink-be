package com.unilink.assessment_service.repository;

import com.unilink.assessment_service.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByIsDeactivatedAndIsExternalHost(boolean b, boolean b1);

    Boolean existsByNameIgnoreCaseAndIsDeactivated(String name, boolean b);

    Optional<Quiz> findByIdAndIsDeactivated(Long quizId, boolean b);
}