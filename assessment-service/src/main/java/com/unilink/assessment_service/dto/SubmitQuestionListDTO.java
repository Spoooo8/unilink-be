package com.unilink.assessment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubmitQuestionListDTO {
    private Long QuizId;
    private List<SubmitQuestionDTO> answer;
}