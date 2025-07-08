package com.unilink.user_service.service.assessment;

import com.unilink.common.exceptions.UnilinkBadRequestException;
import com.unilink.common.exceptions.UnilinkResourceNotFoundException;
import com.unilink.user_service.dto.assessment.QuizRequestDTO;
import com.unilink.user_service.dto.assessment.QuizResponseDTO;
import com.unilink.user_service.entity.assessment.Question;
import com.unilink.user_service.entity.assessment.Quiz;
import com.unilink.user_service.repository.assessment.QuestionRepository;
import com.unilink.user_service.repository.assessment.QuizRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService  {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private QuestionRepository questionRepository;

    public List<QuizResponseDTO> getAllInternalQuiz() {
        List<Quiz> domains = quizRepository.findByIsDeactivatedAndIsExternalHost(false, false);
        return domains.stream()
                .map(domain -> new QuizResponseDTO(domain.getId(), domain.getName(), domain.getTimer(), domain.getIsExternalHost()))
                .collect(Collectors.toList());
    }


    public void addQuiz(QuizRequestDTO quizDTO) {
        if(quizDTO.getIsExternalHost()){
            Quiz newQuiz = new Quiz();
            newQuiz = modelMapper.map(quizDTO, Quiz.class);
            quizRepository.save(newQuiz);
        }else{
            Boolean quizExists = quizRepository.existsByNameIgnoreCaseAndIsDeactivated(quizDTO.getName(), false);
            if(quizExists){
                throw new UnilinkBadRequestException("quiz name already exists");
            }
            Quiz newQuiz = new Quiz();
            newQuiz = modelMapper.map(quizDTO, Quiz.class);
            quizRepository.save(newQuiz);
        }

    }


    public boolean updateQuiz(Long quizId, QuizRequestDTO quizDTO) {
        boolean isUpdated = false;
        Quiz existingQuiz = quizRepository.findByIdAndIsDeactivated(quizId, false).orElseThrow(
                () -> new UnilinkResourceNotFoundException("Quiz"));
        if(existingQuiz!=null){
            modelMapper.map(quizDTO, existingQuiz);
            quizRepository.save(existingQuiz);
            isUpdated = true;
        }
        return isUpdated;
    }


    public boolean deleteQuiz(Long quizId) {
        boolean isDeleted = false;
        Quiz existingQuiz = quizRepository.findByIdAndIsDeactivated(quizId, false).orElseThrow(
                () -> new UnilinkResourceNotFoundException("Quiz"));
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
