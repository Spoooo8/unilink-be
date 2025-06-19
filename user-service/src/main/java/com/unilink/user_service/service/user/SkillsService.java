package com.unilink.user_service.service.user;

import com.unilink.common.dto.ApiResponse;
import com.unilink.common.exceptions.UnilinkResourceNotFoundException;
import com.unilink.user_service.dto.user.SkillDto;
import com.unilink.user_service.entity.user.Skill;
import com.unilink.user_service.repository.user.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillsService {
    ApiResponse response = new ApiResponse();
    @Autowired
    private SkillRepository skillsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ApiResponse addSkill(SkillDto request) {
        Skill newSkill = new Skill();
        newSkill.setName(request.getName());
        skillsRepository.save(newSkill);
        return response;
    }

    // Method to get a skill by ID
    public SkillDto getSkillById(Long id) {
        Skill skill = skillsRepository.findById(id)
                .orElseThrow(() -> new UnilinkResourceNotFoundException("Skill not found with id: " + id));
        return modelMapper.map(skill, SkillDto.class);
    }

    // Method to get all skills
    public List<SkillDto> getAllSkills() {
        List<Skill> skills = skillsRepository.findAll();
        return skills.stream()
                .map(skill -> modelMapper.map(skill, SkillDto.class))
                .collect(Collectors.toList());
    }
}
