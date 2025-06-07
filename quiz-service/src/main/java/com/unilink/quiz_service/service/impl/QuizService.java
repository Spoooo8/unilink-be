package com.unilink.quiz_service.service.impl;

import com.unilink.common.exceptions.FQBadRequestException;
import com.unilink.common.exceptions.FQResourceNotFoundException;
import com.unilink.quiz_service.dto.QuizRequestDTO;
import com.unilink.quiz_service.dto.QuizResponseDTO;
import com.unilink.quiz_service.entity.Question;
import com.unilink.quiz_service.entity.Quiz;
import com.unilink.quiz_service.repository.QuestionRepository;
import com.unilink.quiz_service.repository.QuizRepository;
import com.unilink.quiz_service.service.IQuizService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("IQuizService")
public class QuizService implements IQuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<QuizResponseDTO> getAllInternalQuiz() {
        List<Quiz> domains = quizRepository.findByIsDeactivatedAndIsExternalHost(false, false);
        return domains.stream()
                .map(domain -> new QuizResponseDTO(domain.getId(), domain.getName(), domain.getIcon(), domain.getColor(), domain.getTimer(), domain.getIsExternalHost()))
                .collect(Collectors.toList());
    }

    @Override
    public void addQuiz(QuizRequestDTO quizDTO) {
        if(quizDTO.getIsExternalHost()){
            Quiz newQuiz = new Quiz();
            newQuiz = modelMapper.map(quizDTO, Quiz.class);
            quizRepository.save(newQuiz);
        }else{
            Boolean quizExists = quizRepository.existsByNameIgnoreCaseAndIsDeactivated(quizDTO.getName(), false);
            if(quizExists){
                throw new FQBadRequestException("quiz name already exists");
            }
            Quiz newQuiz = new Quiz();
            newQuiz = modelMapper.map(quizDTO, Quiz.class);
            quizRepository.save(newQuiz);
        }

    }

    @Override
    public boolean updateQuiz(Long quizId, QuizRequestDTO quizDTO) {
        boolean isUpdated = false;
        Quiz existingQuiz = quizRepository.findByIdAndIsDeactivated(quizId, false).orElseThrow(
                () -> new FQResourceNotFoundException("Quiz", "id", quizId.toString()));
        if(existingQuiz!=null){
            modelMapper.map(quizDTO, existingQuiz);
            quizRepository.save(existingQuiz);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteQuiz(Long quizId) {
        boolean isDeleted = false;
        Quiz existingQuiz = quizRepository.findByIdAndIsDeactivated(quizId, false).orElseThrow(
                () -> new FQResourceNotFoundException("Quiz", "id", quizId.toString()));
        if(existingQuiz!=null){
            existingQuiz.setIsDeactivated(true);
            quizRepository.save(existingQuiz);
            List<Question> questions = questionRepository.findByQuizIdAndIsDeactivated(quizId, false);
            questions.forEach(question -> {
                question.setIsDeactivated(true);
                questionRepository.save(question);
            });
            isDeleted = true;
        }
        return isDeleted;
    }

}
