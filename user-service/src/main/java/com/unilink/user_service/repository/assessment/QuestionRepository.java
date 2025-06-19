package com.unilink.user_service.repository.assessment;


import com.unilink.user_service.entity.assessment.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByIdAndIsDeactivated(Long questionId, boolean b);

    List<Question> findByQuizIdAndIsDeactivated(Long quizId, boolean b);
}
