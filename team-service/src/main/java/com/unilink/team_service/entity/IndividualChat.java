package com.unilink.team_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "individual_chats", schema = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndividualChat {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private Integer userOneId;

    private Integer userTwoId;

    private Timestamp createdAt;

    private Timestamp lastMessageAt;

    private Boolean isActive;
}
