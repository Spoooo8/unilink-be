package com.unilink.team_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.math.BigDecimal;

@Entity
@Table(name = "tasks", schema = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    private Integer id;

    private Integer projectId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private Integer assignedTo;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private String status;

    private String priority;

    private Timestamp dueDate;

    private Timestamp completedAt;

    private Integer estimatedHours;

    private Integer actualHours;

    @Column(columnDefinition = "text")
    private String dependencies;

    @Column(precision = 5, scale = 2)
    private BigDecimal progress;
}
