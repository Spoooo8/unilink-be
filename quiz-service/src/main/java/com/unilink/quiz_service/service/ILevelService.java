package com.unilink.quiz_service.service;

import com.unilink.quiz_service.dto.LevelDTO;
import com.unilink.quiz_service.dto.LevelRequestDTO;

import java.util.List;

public interface ILevelService {
    List<LevelDTO> getLevels();

    void addLevel(LevelRequestDTO levelRequestDTO);

    boolean updateLevel(Long levelId, LevelRequestDTO levelRequestDTO);

    boolean deleteLevel(Long levelId);


}