package com.unilink.chat_service.dto;

import com.unilink.chat_service.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private String id;
    private String roomName;
    private Long projectId;
    private String type;
    private List<Long> userIds = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
}
