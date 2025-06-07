package com.unilink.quiz_service.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorsDto {
    private Long id;
    private String name;
    private Double projectRating;
    private Double skillScore;
}
