package com.unilink.quiz_service.service.impl;

import com.unilink.common.exceptions.FQBadRequestException;
import com.unilink.common.exceptions.FQResourceNotFoundException;
import com.unilink.quiz_service.dto.LevelDTO;
import com.unilink.quiz_service.dto.LevelRequestDTO;
import com.unilink.quiz_service.entity.Level;
import com.unilink.quiz_service.repository.LevelRepository;
import com.unilink.quiz_service.service.ILevelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("ILevelService")
public class LevelService implements ILevelService {
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<LevelDTO> getLevels() {
        List<Level> levels = levelRepository.findByIsDeactivated(false);
        return levels.stream().map(level -> modelMapper.map(level, LevelDTO.class))
                .toList();

    }

    @Override
    public void addLevel(LevelRequestDTO levelRequestDTO) {
        Boolean levelExists = levelRepository.existsByScoreAndIsDeactivated(levelRequestDTO.getScore(), false);
        if(levelExists){
            throw new FQBadRequestException("level already exists");
        }
        Level newLevel = new Level();
        newLevel = modelMapper.map(levelRequestDTO, Level.class);
        levelRepository.save(newLevel);
    }

    @Override
    public boolean updateLevel(Long levelId, LevelRequestDTO levelRequestDTO) {
        boolean isUpdated = false;
        Level existingLevel = levelRepository.findByIdAndIsDeactivated(levelId, false).orElseThrow(
                () -> new FQResourceNotFoundException("Level", "Score", levelRequestDTO.getScore().toString()));
        if(existingLevel!=null){
            modelMapper.map(levelRequestDTO, existingLevel);
            levelRepository.save(existingLevel);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteLevel(Long levelId) {
        boolean isDeleted = false;
        Level existingLevel = levelRepository.findByIdAndIsDeactivated(levelId, false).orElseThrow(
                () -> new FQResourceNotFoundException("Level", "Id",levelId.toString()));
        if(existingLevel!=null){
            existingLevel.setIsDeactivated(true);
            levelRepository.save(existingLevel);
            isDeleted = true;
        }
        return isDeleted;
    }
}