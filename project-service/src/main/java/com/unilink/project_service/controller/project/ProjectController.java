package com.unilink.project_service.controller.project;

import com.unilink.project_service.dto.project.*;
import com.unilink.project_service.service.project.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ProjectController {
    Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @PostMapping("/add")
    public ProjectDTO addProject(@RequestBody ProjectDTO projectDto){
        return projectService.addProject(projectDto);
    }

    @GetMapping("/myProjects")
    public List<MyProjectCardDTO> getMyProjectCards(){
        return projectService.getMyProjectCard();
    }

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

    @GetMapping("/{projectId}/host")
    public HostIdDTO getHostId(@PathVariable("projectId") Long projectId){
        return projectService.getHostId(projectId);
    }
}