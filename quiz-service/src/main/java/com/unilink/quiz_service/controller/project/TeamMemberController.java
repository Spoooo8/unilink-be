package com.unilink.quiz_service.controller.project;

import com.unilink.quiz_service.dto.BaseDTO;
import com.unilink.quiz_service.dto.project.CollaboratorsDto;
import com.unilink.quiz_service.repository.project.TeamRepository;
import com.unilink.quiz_service.service.users.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/teams")
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
    public List<CollaboratorsDto> getCollaborators(@PathVariable Long teamId){
        return teamMemberService.getAcceptedTeam(teamId);
    }
}
