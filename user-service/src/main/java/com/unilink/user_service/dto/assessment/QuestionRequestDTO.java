package com.unilink.user_service.dto.assessment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDTO {
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Integer rightAnswer;
    private Long quizId;
    private Long levelId;
}