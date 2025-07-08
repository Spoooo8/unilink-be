package com.unilink.user_service.controller;

import com.unilink.common.dto.ApiResponse;
import com.unilink.user_service.dto.user.SkillDto;
import com.unilink.user_service.service.user.SkillsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
public class SkillController {
    Logger logger = LoggerFactory.getLogger(SkillController.class);

    @Autowired
    private SkillsService skillsService;

    @GetMapping("/all")
    public List<SkillDto> getAllSkills() {
        logger.info("in get all skills");
        return skillsService.getAllSkills();
    }

    @GetMapping("/{id}")
    public SkillDto getSkillById(@PathVariable("id") Long id){
        logger.info("in get skill by id");
        return skillsService.getSkillById(id);
    }

    @PostMapping("/add")
    public ApiResponse addSkill(@RequestBody SkillDto skill){
        logger.info("in add skill");
        return skillsService.addSkill(skill);
    }
}
