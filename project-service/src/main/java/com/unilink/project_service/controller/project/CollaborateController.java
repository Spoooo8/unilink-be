package com.unilink.project_service.controller.project;

import com.unilink.project_service.dto.project.CollaboratorsDTO;
import com.unilink.project_service.dto.project.JoinProjectDTO;
import com.unilink.project_service.dto.project.RoleDropDownDTO;
import com.unilink.project_service.service.project.CollaborateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CollaborateController {

    @Autowired
    private CollaborateService collaborateService;

    @PostMapping("/{projectId}/join")
    public void joinProject(@PathVariable("projectId") Long projectId, @RequestBody JoinProjectDTO joinProjectDTO) {
        collaborateService.joinProject(projectId, joinProjectDTO);
    }

    @GetMapping("/{projectId}/collaborators")
    public List<CollaboratorsDTO> getCollaborators(@PathVariable("projectId") Long projectId){
        return collaborateService.getCollaborators(projectId);
    }


}