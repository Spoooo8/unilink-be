package com.unilink.user_service.dto.assessment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDTO {
    private Long quizId;
    private Integer score;
    private Boolean result;
}