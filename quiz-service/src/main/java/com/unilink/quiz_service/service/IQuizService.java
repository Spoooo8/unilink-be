package com.unilink.quiz_service.service;

import com.unilink.quiz_service.dto.QuizRequestDTO;
import com.unilink.quiz_service.dto.QuizResponseDTO;

import java.util.List;

public interface IQuizService {
    List<QuizResponseDTO> getAllInternalQuiz();

    void addQuiz(QuizRequestDTO quizDomainDTO);

    boolean updateQuiz(Long quizId,QuizRequestDTO quizDomainDTO);

    boolean deleteQuiz(Long domainId);
}