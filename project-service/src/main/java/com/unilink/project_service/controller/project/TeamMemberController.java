package com.unilink.project_service.controller.project;

import com.unilink.common.dto.BaseDTO;
import com.unilink.common.dto.ResponseDTO;
import com.unilink.project_service.dto.project.CollaboratorsDTO;
import com.unilink.project_service.dto.project.ProfileDTO;
import com.unilink.project_service.repository.project.TeamRepository;
import com.unilink.project_service.service.project.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TeamMemberController {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping("{projectId}/accept/{collaboratorId}")
    public ResponseDTO postAcceptedTeam(@PathVariable Long collaboratorId, @PathVariable Long projectId) {
        return teamMemberService.acceptTeam(collaboratorId, projectId);
    }

    @GetMapping("/{projectId}/members")
    public List<CollaboratorsDTO> getCollaborators(@PathVariable Long projectId) {
        return teamMemberService.getAcceptedTeam(projectId);
    }

    @GetMapping("/user/profile/{userid}")
    public ProfileDTO getProfile(@PathVariable Long userid) {
        return teamMemberService.getProfile(userid);

    }
}