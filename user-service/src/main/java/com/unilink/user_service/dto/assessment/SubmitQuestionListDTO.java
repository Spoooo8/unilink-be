package com.unilink.user_service.dto.assessment;

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