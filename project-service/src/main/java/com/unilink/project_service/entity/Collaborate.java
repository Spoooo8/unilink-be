package com.unilink.project_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "project", name = "collaborate")
public class Collaborate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private LocalDateTime requestDate;
    private String status;
    private String message;
    private String roleRequested;

}
