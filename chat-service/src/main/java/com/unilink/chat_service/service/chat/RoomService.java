package com.unilink.chat_service.service.chat;

import com.unilink.chat_service.config.UserContextId;
import com.unilink.chat_service.dto.CollaboratorsDTO;
import com.unilink.chat_service.dto.PeopleDropDownDTO;
import com.unilink.chat_service.dto.RoomDTO;
import com.unilink.chat_service.dto.UserResponse;
import com.unilink.chat_service.entity.Room;
import com.unilink.chat_service.repository.RoomRepository;
import com.unilink.chat_service.service.client.ProjectFeignClient;
import com.unilink.chat_service.service.client.UserFeignClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private ProjectFeignClient projectFeignClient;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private UserContextId userContext;

    public ResponseEntity<List<PeopleDropDownDTO>> getPeopleDropdown(Long projectId) {
        List<CollaboratorsDTO> collaboratorsDTO = projectFeignClient.getCollaborators(projectId);

        List<PeopleDropDownDTO> dropdownList = collaboratorsDTO.stream()
                .map(user -> {
                    PeopleDropDownDTO dd = new PeopleDropDownDTO();
                    dd.setUserId(user.getId());
                    dd.setName(user.getName());
                    return dd;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dropdownList);
    }





    public List<RoomDTO> getUserRooms(Long projectId) {
        Long userId = Long.valueOf(userContext.getUserId());
        List<Room> userRooms = roomRepository.findByProjectIdAndUserIdsContaining(projectId, userId);

        return userRooms.stream().map(room -> {
            RoomDTO dto = new RoomDTO();
            dto.setId(room.getId());
            dto.setProjectId(room.getProjectId());
            dto.setType(room.getType());
            dto.setUserIds(room.getUserIds());
            dto.setMessages(room.getMessages());

            // Check for individual chat
            if ("individual".equalsIgnoreCase(room.getType())) {

                // ✅ Get the other user's ID (excluding current user)
                Long otherUserId = room.getUserIds().stream()
                        .filter(id -> !id.equals(userId))
                        .findFirst()
                        .orElse(null);

                if (otherUserId != null) {
                    try {
                        UserResponse userResponse = userFeignClient.getUserById(otherUserId);
                        dto.setRoomName(userResponse.getName());
                    } catch (Exception e) {
                        System.out.println("❌ Failed to fetch user name: " + e.getMessage());
                        dto.setRoomName("Unknown User");
                    }
                } else {
                    dto.setRoomName("Unknown User");
                }

            } else {
                dto.setRoomName(room.getRoomName()); // For group chats
            }

            return dto;
        }).collect(Collectors.toList());
    }



}
