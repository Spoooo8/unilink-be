package com.unilink.chat_service.controller;

import com.unilink.chat_service.config.UserContextId;
import com.unilink.chat_service.dto.MessageRequest;
import com.unilink.chat_service.entity.Message;
import com.unilink.chat_service.entity.Room;
import com.unilink.chat_service.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {
    @Autowired
    private RoomRepository roomRepository;

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            MessageRequest request
    ) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found!"));

        // Build message
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSenderId(request.getUserId()); // Store user ID, not name
        message.setSenderName(request.getSenderName());
        message.setTimeStamp(LocalDateTime.now());

        // Add message to room history
        room.getMessages().add(message);
        roomRepository.save(room);

        return message;
    }
}
