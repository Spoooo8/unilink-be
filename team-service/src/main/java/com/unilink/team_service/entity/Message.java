package com.unilink.team_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages", schema = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private Integer senderId;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    private Timestamp sentAt;

    private Boolean isEdited;

    private Timestamp editedAt;

    private Boolean isDeleted;
}
