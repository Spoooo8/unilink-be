package com.unilink.project_service.dto.project;

import com.unilink.project_service.utils.ComplexityLevel;
import com.unilink.project_service.utils.ProjectVisibility;
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
    private String title;
    private String repoLink;
    private String description;
    private String imageUrl;
    private List<Integer> skillIds;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate applicationDeadline;
    private ComplexityLevel complexityLevel;
    private ProjectVisibility projectVisibility;
    private Integer teamSize;

}