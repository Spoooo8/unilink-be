package com.unilink.project_service.controller.project;

import com.unilink.common.dto.BaseDTO;
import com.unilink.project_service.dto.project.CollaboratorsDTO;
import com.unilink.project_service.repository.project.TeamRepository;
import com.unilink.project_service.service.project.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamMemberController {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping("{collaboratorId}/accept/team")
    public BaseDTO postAcceptedTeam(@PathVariable Long collaboratorId){
        return teamMemberService.acceptTeam(collaboratorId);
    }

    @GetMapping("/{teamId}/members")
    public List<CollaboratorsDTO> getCollaborators(@PathVariable Long teamId){
        return teamMemberService.getAcceptedTeam(teamId);
    }
}
