package com.unilink.user_service.dto.assessment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponseDTO {
    private Long id;
    private String name;
    private Time timer;
    private Boolean isExternalHost;


}
