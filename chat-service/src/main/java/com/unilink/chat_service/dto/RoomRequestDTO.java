package com.unilink.chat_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDTO {
    private String type;
    private Long projectId;
    private String roomName;
   private List<Long> userIds;
}
