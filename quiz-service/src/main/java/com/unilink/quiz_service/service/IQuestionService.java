package com.unilink.quiz_service.service;

import com.unilink.quiz_service.dto.QuestionDTO;
import com.unilink.quiz_service.dto.QuestionRequestDTO;
import com.unilink.quiz_service.dto.QuizResultDTO;
import com.unilink.quiz_service.dto.SubmitQuestionListDTO;

import java.util.List;

public interface IQuestionService {
    List<QuestionDTO> getQuestionsByQuizId(Long domainId);

    void addQuestion(QuestionRequestDTO questionRequestDTO);

    boolean updateQuestion(Long questionId, QuestionRequestDTO questionRequestDTO);

    boolean deleteQuestion(Long questionId);

    QuizResultDTO submitQuiz(SubmitQuestionListDTO submitQuestionListDTO);
}