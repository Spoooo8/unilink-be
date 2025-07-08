package com.unilink.chat_service.repository;

import com.unilink.chat_service.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {

    List<Room> findByUserIdsContaining(Long userId);

    List<Room> findByProjectId(Long projectId);

    List<Room> findByProjectIdAndUserIdsContaining(Long projectId, Long userId);
}
