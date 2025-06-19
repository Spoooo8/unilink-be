package com.unilink.user_service.repository.assessment;


import com.unilink.user_service.entity.assessment.Quiz;
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