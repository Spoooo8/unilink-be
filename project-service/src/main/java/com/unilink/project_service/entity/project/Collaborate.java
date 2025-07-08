package com.unilink.project_service.entity.project;

import com.unilink.common.entity.BaseEntity;
import com.unilink.project_service.utils.TeamMemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
        private String message;
        private TeamMemberRole role;
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "project_id")
        private Project project;

    }