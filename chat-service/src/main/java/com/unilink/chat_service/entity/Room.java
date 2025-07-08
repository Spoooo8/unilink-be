package com.unilink.chat_service.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    private String id;
    private String roomName;
    private Long projectId;
    private String type;
    private List<Long> userIds = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
}


