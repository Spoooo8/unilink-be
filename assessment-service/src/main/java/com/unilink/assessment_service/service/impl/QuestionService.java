package com.unilink.assessment_service.service.impl;

import com.unilink.assessment_service.dto.*;
import com.unilink.assessment_service.entity.Level;
import com.unilink.assessment_service.entity.Question;
import com.unilink.assessment_service.entity.Quiz;
import com.unilink.assessment_service.repository.LevelRepository;
import com.unilink.assessment_service.repository.QuestionRepository;
import com.unilink.assessment_service.repository.QuizRepository;
import com.unilink.assessment_service.service.IQuestionService;
import com.unilink.common.exceptions.FQResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("IQuestionService")
public class QuestionService implements IQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private LevelRepository levelRepository;


    @Override
    public List<QuestionDTO> getQuestionsByQuizId(Long quizId) {
        List<Question> questions = questionRepository.findByQuizIdAndIsDeactivated(quizId, false);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public void addQuestion(QuestionRequestDTO questionRequestDTO) {
        Quiz existingQuiz = quizRepository.findByIdAndIsDeactivated(questionRequestDTO.getQuizId(), false).orElseThrow(
                () -> new FQResourceNotFoundException("Quiz", "id", questionRequestDTO.getQuizId().toString()));
        Level existingLevel = levelRepository.findByIdAndIsDeactivated(questionRequestDTO.getLevelId(), false).orElseThrow(
                () -> new FQResourceNotFoundException("Level", "id", questionRequestDTO.getQuizId().toString()));
        Question newQuestion = new Question();
        newQuestion = modelMapper.map(questionRequestDTO, Question.class);
        newQuestion.setQuiz(existingQuiz);
        newQuestion.setLevel(existingLevel);
        questionRepository.save(newQuestion);
    }

    @Override
    public boolean updateQuestion(Long questionId,QuestionRequestDTO questionRequestDTO) {
        boolean isUpdated = false;
        Question existingQuestion = questionRepository.findByIdAndIsDeactivated(questionId, false).orElseThrow(
                () -> new FQResourceNotFoundException("Question", "id", questionId.toString())
        );
        if(existingQuestion!=null){
            existingQuestion.setQuestionTitle(questionRequestDTO.getQuestionTitle());
            existingQuestion.setOption1(questionRequestDTO.getOption1());
            existingQuestion.setOption2(questionRequestDTO.getOption2());
            existingQuestion.setOption3(questionRequestDTO.getOption3());
            existingQuestion.setOption4(questionRequestDTO.getOption4());
            existingQuestion.setRightAnswer(questionRequestDTO.getRightAnswer());

            Quiz existingQuiz = quizRepository.findByIdAndIsDeactivated(questionRequestDTO.getQuizId(), false).orElseThrow(
                    () -> new FQResourceNotFoundException("Quiz", "id", questionRequestDTO.getQuizId().toString()));
            Level existingLevel = levelRepository.findByIdAndIsDeactivated(questionRequestDTO.getLevelId(), false).orElseThrow(
                    () -> new FQResourceNotFoundException("Level", "id", questionRequestDTO.getQuizId().toString()));
            existingQuestion.setQuiz(existingQuiz);
            existingQuestion.setLevel(existingLevel);
            questionRepository.save(existingQuestion);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteQuestion(Long questionId) {
       boolean isDeleted = false;
        Question existingQuestion = questionRepository.findByIdAndIsDeactivated(questionId, false).orElseThrow(
                () -> new FQResourceNotFoundException("Question", "id", questionId.toString()));
        if(existingQuestion!=null){
            existingQuestion.setIsDeactivated(true);
            questionRepository.save(existingQuestion);
            isDeleted = true;
        }
       return isDeleted;
    }

    @Override
    public QuizResultDTO submitQuiz(SubmitQuestionListDTO submitQuestionListDTO) {
        int totalScore = 0;
        Long quizId = submitQuestionListDTO.getQuizId();

        for (SubmitQuestionDTO submitQuestionDTO : submitQuestionListDTO.getAnswer()) {
            Long questionId = submitQuestionDTO.getQuestionId();
            Long chosenOption = submitQuestionDTO.getChosenOption();

            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));

            if (question.getRightAnswer().equals(chosenOption)) {
                totalScore++;
            }
        }
        return new QuizResultDTO(quizId, totalScore);
    }
}
