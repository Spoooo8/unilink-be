package com.unilink.project_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "project", name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer hostId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDate endDate;
    private String complexityLevel;
    private String visibility;
    private LocalDateTime applicationDeadline;
    private Integer teamSize;
    private Integer progress;
    private Integer rating;
    private String projectType;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    // Getters and setters...
}
