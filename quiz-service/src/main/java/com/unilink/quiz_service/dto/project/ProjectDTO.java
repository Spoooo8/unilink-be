package com.unilink.quiz_service.dto.project;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private Long hostId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;
    private String complexityLevel;
    private String visibility;
    private LocalDate applicationDeadline;
    private Integer teamSize;
    private Double progress;
    private Double rating;
    private String projectType;
    private Long statusId;
    private List<Long> skillIds;
}