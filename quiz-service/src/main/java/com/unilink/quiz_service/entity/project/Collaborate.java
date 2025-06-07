package com.unilink.quiz_service.entity.project;

import com.unilink.common.entity.BaseEntity;
import com.unilink.quiz_service.utils.TeamMemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_collaborate", schema = "projects")
public class Collaborate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDate requestDate;
    private String status;
    private String message;
    private String roleRequested;
    private TeamMemberRole role;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

}
