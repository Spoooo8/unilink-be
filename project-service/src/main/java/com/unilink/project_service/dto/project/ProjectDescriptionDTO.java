package com.unilink.project_service.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDescriptionDTO {
    private Long id;
    private String title;
    private String projectType;
    private String description;
    private String startDate;
    private Double duration;
}