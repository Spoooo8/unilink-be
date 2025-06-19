package com.unilink.user_service.service.assessment;


import com.unilink.common.exceptions.UnilinkBadRequestException;
import com.unilink.common.exceptions.UnilinkResourceNotFoundException;
import com.unilink.user_service.dto.assessment.LevelDTO;
import com.unilink.user_service.dto.assessment.LevelRequestDTO;
import com.unilink.user_service.entity.assessment.Level;
import com.unilink.user_service.repository.assessment.LevelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelService  {
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private ModelMapper modelMapper;


    public List<LevelDTO> getLevels() {
        List<Level> levels = levelRepository.findByIsDeactivated(false);
        return levels.stream().map(level -> modelMapper.map(level, LevelDTO.class))
                .toList();

    }


    public void addLevel(LevelRequestDTO levelRequestDTO) {
        Boolean levelExists = levelRepository.existsByScoreAndIsDeactivated(levelRequestDTO.getScore(), false);
        if(levelExists){
            throw new UnilinkBadRequestException("level already exists");
        }
        Level newLevel = new Level();
        newLevel = modelMapper.map(levelRequestDTO, Level.class);
        levelRepository.save(newLevel);
    }


    public boolean updateLevel(Long levelId, LevelRequestDTO levelRequestDTO) {
        boolean isUpdated = false;
        Level existingLevel = levelRepository.findByIdAndIsDeactivated(levelId, false).orElseThrow(
                () -> new UnilinkResourceNotFoundException("Level"));
        if(existingLevel!=null){
            modelMapper.map(levelRequestDTO, existingLevel);
            levelRepository.save(existingLevel);
            isUpdated = true;
        }
        return isUpdated;
    }


    public boolean deleteLevel(Long levelId) {
        boolean isDeleted = false;
        Level existingLevel = levelRepository.findByIdAndIsDeactivated(levelId, false).orElseThrow(
                () -> new UnilinkResourceNotFoundException("Level"));
        if(existingLevel!=null){
            existingLevel.setIsDeactivated(true);
            levelRepository.save(existingLevel);
            isDeleted = true;
        }
        return isDeleted;
    }
}