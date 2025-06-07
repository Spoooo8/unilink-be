package com.unilink.team_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "calendar_events", schema = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEvent {

    @Id
    private Integer id;

    private Integer projectId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private Integer creatorId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Timestamp startTime;

    @Column(nullable = false)
    private Timestamp endTime;

    private String location;

    private Boolean isRecurring;

    private String recurrencePattern;

    private String status;

    private Timestamp notificationTime;
}
