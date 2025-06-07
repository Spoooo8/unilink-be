package com.unilink.team_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chat_participants", schema = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatParticipant {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private Integer userId;

    private Timestamp joinedAt;

    private Boolean isAdmin;

    private Boolean isActive;
}
