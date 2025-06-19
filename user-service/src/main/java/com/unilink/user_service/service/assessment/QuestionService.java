package com.unilink.user_service.service.assessment;


import com.unilink.common.config.UserContext;
import com.unilink.common.exceptions.UnilinkBadRequestException;
import com.unilink.common.exceptions.UnilinkResourceNotFoundException;
import com.unilink.user_service.dto.assessment.*;
import com.unilink.user_service.entity.assessment.Level;
import com.unilink.user_service.entity.assessment.Question;
import com.unilink.user_service.entity.assessment.Quiz;
import com.unilink.user_service.entity.user.Users;
import com.unilink.user_service.repository.assessment.LevelRepository;
import com.unilink.user_service.repository.assessment.QuestionRepository;
import com.unilink.user_service.repository.assessment.QuizRepository;
import com.unilink.user_service.repository.user.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService   {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserContext userContext;


    public List<QuestionDTO> getQuestionsByQuizId(Long quizId) {
        List<Question> questions = questionRepository.findByQuizIdAndIsDeactivated(quizId, false);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());

    }


    public void addQuestion(QuestionRequestDTO questionRequestDTO) {
        Quiz existingQuiz = quizRepository.findByIdAndIsDeactivated(questionRequestDTO.getQuizId(), false).orElseThrow(
                () -> new UnilinkResourceNotFoundException("Quiz"));
        Level existingLevel = levelRepository.findByIdAndIsDeactivated(questionRequestDTO.getLevelId(), false).orElseThrow(
                () -> new UnilinkResourceNotFoundException("Level"));
        Question newQuestion = new Question();
        newQuestion = mapToEntity(questionRequestDTO);
        newQuestion.setQuiz(existingQuiz);
        newQuestion.setLevel(existingLevel);
        questionRepository.save(newQuestion);
    }


    public boolean updateQuestion(Long questionId,QuestionRequestDTO questionRequestDTO) {
        boolean isUpdated = false;
        Question existingQuestion = questionRepository.findByIdAndIsDeactivated(questionId, false).orElseThrow(
                () -> new UnilinkResourceNotFoundException("Question")
        );
        if(existingQuestion!=null){
            existingQuestion.setQuestionTitle(questionRequestDTO.getQuestionTitle());
            existingQuestion.setOption1(questionRequestDTO.getOption1());
            existingQuestion.setOption2(questionRequestDTO.getOption2());
            existingQuestion.setOption3(questionRequestDTO.getOption3());
            existingQuestion.setOption4(questionRequestDTO.getOption4());
            existingQuestion.setRightAnswer(questionRequestDTO.getRightAnswer());

            Quiz existingQuiz = quizRepository.findByIdAndIsDeactivated(questionRequestDTO.getQuizId(), false).orElseThrow(
                    () -> new UnilinkResourceNotFoundException("Quiz"));
            Level existingLevel = levelRepository.findByIdAndIsDeactivated(questionRequestDTO.getLevelId(), false).orElseThrow(
                    () -> new UnilinkResourceNotFoundException("Level"));
            existingQuestion.setQuiz(existingQuiz);
            existingQuestion.setLevel(existingLevel);
            questionRepository.save(existingQuestion);
            isUpdated = true;
        }
        return isUpdated;
    }


    public boolean deleteQuestion(Long questionId) {
       boolean isDeleted = false;
        Question existingQuestion = questionRepository.findByIdAndIsDeactivated(questionId, false).orElseThrow(
                () -> new UnilinkResourceNotFoundException("Question"));
        if(existingQuestion!=null){
            existingQuestion.setIsDeactivated(true);
            questionRepository.save(existingQuestion);
            isDeleted = true;
        }
       return isDeleted;
    }


    public QuizResultDTO submitQuiz(SubmitQuestionListDTO submitQuestionListDTO) {
        int totalScore = 0;
        Long quizId = submitQuestionListDTO.getQuizId();

        List<SubmitQuestionDTO> submittedAnswers = submitQuestionListDTO.getAnswer();
        if (submittedAnswers == null || submittedAnswers.isEmpty()) {
            throw new IllegalArgumentException("Answer list cannot be empty.");
        }

        for (SubmitQuestionDTO submitQuestionDTO : submittedAnswers) {
            Long questionId = submitQuestionDTO.getQuestionId();
            Integer chosenOption = submitQuestionDTO.getChosenOption();

            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

            if (question.getRightAnswer() != null && question.getRightAnswer().equals(chosenOption)) {
                totalScore++;
            }
        }

        Optional<Users> existingUser = usersRepository.findById(Long.valueOf(userContext.getUserId()));
        if(existingUser.isEmpty()){
            throw new UnilinkBadRequestException("no user found ");
        }

        existingUser.get().setSkillsScore((double) totalScore);
        usersRepository.save(existingUser.get());

        return new QuizResultDTO(quizId, totalScore);
    }



    public Question mapToEntity(QuestionRequestDTO dto) {
        Question question = new Question();
        question.setQuestionTitle(dto.getQuestionTitle());
        question.setOption1(dto.getOption1());
        question.setOption2(dto.getOption2());
        question.setOption3(dto.getOption3());
        question.setOption4(dto.getOption4());
        question.setRightAnswer(dto.getRightAnswer());

        // Fetch Quiz and Level entities using their IDs
        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + dto.getQuizId()));
        Level level = levelRepository.findById(dto.getLevelId())
                .orElseThrow(() -> new RuntimeException("Level not found with id: " + dto.getLevelId()));

        question.setQuiz(quiz);
        question.setLevel(level);

        return question;
    }

}