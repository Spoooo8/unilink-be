package com.unilink.assessment_service.service;

import com.unilink.assessment_service.dto.LevelDTO;
import com.unilink.assessment_service.dto.LevelRequestDTO;
import com.unilink.assessment_service.dto.QuestionDTO;
import com.unilink.common.dto.ResponseDTO;

import java.util.List;

public interface ILevelService {
    List<LevelDTO> getLevels();

    void addLevel(LevelRequestDTO levelRequestDTO);

    boolean updateLevel(Long levelId, LevelRequestDTO levelRequestDTO);

    boolean deleteLevel(Long levelId);


}