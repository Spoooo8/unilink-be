package com.unilink.user_service.controller;

import com.unilink.common.constants.Constants;
import com.unilink.common.dto.ResponseDTO;
import com.unilink.user_service.dto.assessment.*;
import com.unilink.user_service.service.assessment.LevelService;
import com.unilink.user_service.service.assessment.QuestionService;
import com.unilink.user_service.service.assessment.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessment")
public class QuizController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private LevelService levelService;

    @GetMapping
    public List<QuizResponseDTO> getAllInternalQuiz() {
        return quizService.getAllInternalQuiz();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addQuiz(@RequestBody QuizRequestDTO quizDomainDTO) {
         quizService.addQuiz(quizDomainDTO);
         return  ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(new ResponseDTO(Constants.STATUS_201, Constants.Quiz_201));
    }

        @PutMapping("{assessmentId}")
        public ResponseEntity<ResponseDTO> updateQuiz(@PathVariable("assessmentId") Long assessmentId, @RequestBody QuizRequestDTO quizDomainDTO) {
            boolean isUpdated = quizService.updateQuiz(assessmentId, quizDomainDTO);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(Constants.STATUS_417, Constants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/{assessmentId}")
    public ResponseEntity<ResponseDTO> deleteQuiz(@PathVariable("assessmentId") Long assessmentId) {
        boolean isDeleted = quizService.deleteQuiz(assessmentId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(Constants.STATUS_417, Constants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/{assessmentId}/questions")
    public List<QuestionDTO> getQuestionsByQuizId(@PathVariable("assessmentId") Long assessmentId) {
        return questionService.getQuestionsByQuizId(assessmentId);
    }

    @PostMapping("/questions")
    public ResponseEntity<ResponseDTO> addQuestion(@RequestBody QuestionRequestDTO questionRequestDTO) {
        questionService.addQuestion(questionRequestDTO);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(Constants.STATUS_201, Constants.Quiz_201));

    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<ResponseDTO> updateQuestion(@PathVariable("questionId") Long questionId, @RequestBody QuestionRequestDTO questionRequestDTO) {
       boolean isUpdated = questionService.updateQuestion(questionId,questionRequestDTO);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(Constants.STATUS_417, Constants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<ResponseDTO> deleteQuestion(@PathVariable("questionId") Long questionId) {
        boolean isDeleted = questionService.deleteQuestion(questionId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(Constants.STATUS_417, Constants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/levels")
    public List<LevelDTO> getLevels() {
        return levelService.getLevels();
    }

    @PostMapping("/levels")
    public ResponseEntity<ResponseDTO> addLevel(@RequestBody LevelRequestDTO levelRequestDTO) {
        levelService.addLevel(levelRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(Constants.STATUS_201, Constants.Quiz_201));
    }

    @PutMapping("/levels/{levelId}")
    public ResponseEntity<ResponseDTO> updateLevel(@PathVariable("levelId") Long levelId, @RequestBody LevelRequestDTO levelRequestDTO) {
        boolean isUpdated = levelService.updateLevel(levelId, levelRequestDTO);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(Constants.STATUS_417, Constants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/levels/{levelId}")
    public ResponseEntity<ResponseDTO> deleteLevel(@PathVariable("levelId") Long levelId) {
        boolean isDeleted =  levelService.deleteLevel(levelId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(Constants.STATUS_417, Constants.MESSAGE_417_DELETE));
        }
    }

    @PostMapping(("/submit"))
    public ResponseEntity<QuizResultDTO> submitQuiz(@RequestBody SubmitQuestionListDTO submitQuestionListDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( questionService.submitQuiz(submitQuestionListDTO));
    }
}
