package com.unilink.chat_service.controller;

import com.netflix.discovery.converters.Auto;
import com.unilink.chat_service.dto.PeopleDropDownDTO;
import com.unilink.chat_service.dto.RoomRequestDTO;
import com.unilink.chat_service.entity.Message;
import com.unilink.chat_service.entity.Room;
import com.unilink.chat_service.repository.RoomRepository;
import com.unilink.chat_service.service.chat.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat/rooms")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomRequestDTO roomRequestDTO) {
        Room room = new Room();
        room.setProjectId(roomRequestDTO.getProjectId());
        room.setType(roomRequestDTO.getType());
        room.setRoomName(roomRequestDTO.getRoomName());
        room.setUserIds(roomRequestDTO.getUserIds() != null ? roomRequestDTO.getUserIds() : new ArrayList<>());
        room.setMessages(new ArrayList<>());
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found!");
        }
        return ResponseEntity.ok(room);
    }

    @PostMapping("/{roomId}/add-user/{userId}")
    public ResponseEntity<?> addUserToRoom(@PathVariable String roomId, @PathVariable Long userId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found!");
        }

        if (!room.getUserIds().contains(userId)) {
            room.getUserIds().add(userId);
            roomRepository.save(room);
        }

        return ResponseEntity.ok(room);
    }


    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getUserRooms(@PathVariable Long projectId) {

        return ResponseEntity.ok(roomService.getUserRooms(projectId));
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Message> messages = room.getMessages();
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);

        List<Message> paginatedMessages = messages.subList(start, end);
        return ResponseEntity.ok(paginatedMessages);
    }

    @GetMapping("/{projectId}/people")
    public ResponseEntity<List<PeopleDropDownDTO>> getPeopleDropdown(@PathVariable Long projectId) {
        return roomService.getPeopleDropdown(projectId);
    }
}
