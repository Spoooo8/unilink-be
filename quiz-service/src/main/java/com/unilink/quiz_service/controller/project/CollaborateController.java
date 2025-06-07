package com.unilink.quiz_service.controller.project;

import com.unilink.quiz_service.dto.project.CollaboratorsDto;
import com.unilink.quiz_service.dto.project.JoinProjectDTO;
import com.unilink.quiz_service.dto.project.RoleDropDownDTO;
import com.unilink.quiz_service.service.project.CollaborateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/projects")
public class CollaborateController {

    @Autowired
    private CollaborateService collaborateService;

    @PostMapping("/{projectId}/join")
    public void joinProject(@PathVariable("projectId") Long projectId, @RequestBody JoinProjectDTO joinProjectDTO) {
        collaborateService.joinProject(projectId, joinProjectDTO);
    }

    @GetMapping("/roles/dropdown")
    public List<RoleDropDownDTO> getRolesDropDown(){
     return collaborateService.getRoleDropDownDTO();
    }

    @GetMapping("/{projectId}/collaborators")
    public List<CollaboratorsDto> getCollaborators(@PathVariable("projectId") Long projectId){
        return collaborateService.getCollaborators(projectId);
    }
}
