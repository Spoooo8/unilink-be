package com.unilink.quiz_service.controller.project;

import com.unilink.quiz_service.controller.user.UsersController;
import com.unilink.quiz_service.dto.project.*;
import com.unilink.quiz_service.service.project.ProjectService;
import com.unilink.quiz_service.utils.ProjectStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/projects")
public class ProjectController {
    Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private ProjectService projectService;
    @GetMapping("/layout/cards")
    public List<ProjectResponse> getLayoutProjectCards() {
        logger.info("in get all users");
        return projectService.getLayoutProjectCard();
    }

    @GetMapping("/{id}")
    public ProjectDescriptionDTO getProjectDescriptionById(@PathVariable("id") Long id){
        logger.info("in get project description by id");
        return projectService.getProjectDescriptionById(id);
    }

    @GetMapping("/ongoing/titles")
    public List<OngoingTitlesDTO> getProjectTitlesById(){
        return projectService.getProjectTitles();
    }

    @GetMapping("/myProjectCard")
    public List<MyProjectCardDTO> getMyProjectCards(){
        return projectService.getMyProjectCard();
    }

    @PostMapping("/add")
    public ProjectDTO addProject(@RequestBody ProjectDTO projectDto){
        return projectService.addProject(projectDto);
    }

    @GetMapping("/skill/dropdown")
    public List<SkillDropdownDTO> getSkillDropdownList(){
        return projectService.getSkillDropdownList();
    }


}
