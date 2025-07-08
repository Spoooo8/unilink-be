package com.unilink.project_service.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorsDTO {
    private Long id;
    private Long collaborateId;
    private String name;
    private String projectRating;
    private String skillScore;
}