package com.unilink.quiz_service.controller;

import com.unilink.common.dto.ResponseDTO;
import com.unilink.quiz_service.constants.QuizConstants;
import com.unilink.quiz_service.dto.*;
import com.unilink.quiz_service.service.ILevelService;
import com.unilink.quiz_service.service.IQuestionService;
import com.unilink.quiz_service.service.IQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/assessment")
public class QuizController {
    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IQuizService quizService;

    @Autowired
    private ILevelService levelService;

    @GetMapping("/quiz")
    public List<QuizResponseDTO> getAllInternalQuiz() {
        return quizService.getAllInternalQuiz();
    }

    @PostMapping("/quiz")
    public ResponseEntity<ResponseDTO> addQuiz(@RequestBody QuizRequestDTO quizDomainDTO) {
         quizService.addQuiz(quizDomainDTO);
         return  ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(new ResponseDTO(QuizConstants.STATUS_201, QuizConstants.Quiz_201));
    }

        @PutMapping("/quiz/{quizId}")
        public ResponseEntity<ResponseDTO> updateQuiz(@PathVariable("quizId") Long quizId, @RequestBody QuizRequestDTO quizDomainDTO) {
            boolean isUpdated = quizService.updateQuiz(quizId, quizDomainDTO);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(QuizConstants.STATUS_200, QuizConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(QuizConstants.STATUS_417, QuizConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/quiz/{quizId}")
    public ResponseEntity<ResponseDTO> deleteQuiz(@PathVariable("quizId") Long quizId) {
        boolean isDeleted = quizService.deleteQuiz(quizId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(QuizConstants.STATUS_200, QuizConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(QuizConstants.STATUS_417, QuizConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/questions")
    public List<QuestionDTO> getQuestionsByQuizId(@RequestParam("quizId") Long quizId) {
        return questionService.getQuestionsByQuizId(quizId);
    }

    @PostMapping("/questions")
    public ResponseEntity<ResponseDTO> addQuestion(@RequestBody QuestionRequestDTO questionRequestDTO) {
        questionService.addQuestion(questionRequestDTO);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(QuizConstants.STATUS_201, QuizConstants.Quiz_201));

    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<ResponseDTO> updateQuestion(@PathVariable("questionId") Long questionId, @RequestBody QuestionRequestDTO questionRequestDTO) {
       boolean isUpdated = questionService.updateQuestion(questionId,questionRequestDTO);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(QuizConstants.STATUS_200, QuizConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(QuizConstants.STATUS_417, QuizConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<ResponseDTO> deleteQuestion(@PathVariable("questionId") Long questionId) {
        boolean isDeleted = questionService.deleteQuestion(questionId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(QuizConstants.STATUS_200, QuizConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(QuizConstants.STATUS_417, QuizConstants.MESSAGE_417_DELETE));
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
                .body(new ResponseDTO(QuizConstants.STATUS_201, QuizConstants.Quiz_201));
    }

    @PutMapping("/levels/{levelId}")
    public ResponseEntity<ResponseDTO> updateLevel(@PathVariable("levelId") Long levelId, @RequestBody LevelRequestDTO levelRequestDTO) {
        boolean isUpdated = levelService.updateLevel(levelId, levelRequestDTO);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(QuizConstants.STATUS_200, QuizConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(QuizConstants.STATUS_417, QuizConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/levels/{levelId}")
    public ResponseEntity<ResponseDTO> deleteLevel(@PathVariable("levelId") Long levelId) {
        boolean isDeleted =  levelService.deleteLevel(levelId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(QuizConstants.STATUS_200, QuizConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(QuizConstants.STATUS_417, QuizConstants.MESSAGE_417_DELETE));
        }
    }

    @PostMapping(("/quiz/submit"))
    public ResponseEntity<QuizResultDTO> submitQuiz(@RequestBody SubmitQuestionListDTO submitQuestionListDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( questionService.submitQuiz(submitQuestionListDTO));
    }
}
