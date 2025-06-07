package com.unilink.team_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chat_rooms", schema = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    private Integer id;

    private String name;

    private String chatType;

    private Timestamp createdAt;

    private Integer createdBy;

    private Boolean isPrivate;

    @Column(columnDefinition = "text")
    private String description;

    private Integer participantsCount;

    private Integer projectId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
