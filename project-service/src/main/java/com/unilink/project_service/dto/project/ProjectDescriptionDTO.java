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
public class ProjectDescriptionDTO {
    private Long id;
    private String title;
    private String hostName;
    private String description;
    private String applicationDeadline;
    private String startDate;
    private String endDate;
    private String duration;
    private List<String> skills;
    private String complexityLevel;
    private Integer teamSize;
}