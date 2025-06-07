package com.unilink.quiz_service.service.users;

import com.unilink.quiz_service.dto.BaseDTO;
import com.unilink.quiz_service.dto.project.CollaboratorsDto;
import com.unilink.quiz_service.entity.project.Collaborate;
import com.unilink.quiz_service.entity.project.MemberRole;
import com.unilink.quiz_service.entity.project.Team;
import com.unilink.quiz_service.entity.project.TeamMember;
import com.unilink.quiz_service.entity.users.Users;
import com.unilink.quiz_service.repository.project.CollaborateRepository;
import com.unilink.quiz_service.repository.project.MemberRoleRepository;
import com.unilink.quiz_service.repository.project.TeamMemberRepository;
import com.unilink.quiz_service.repository.project.TeamRepository;
import com.unilink.quiz_service.repository.users.UsersRepository;
import com.unilink.quiz_service.utils.TeamMemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamMemberService {
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CollaborateRepository collaborateRepository;

    @Autowired
    private UsersRepository usersRepository;

    public BaseDTO acceptTeam(Long collaboratorId) {
        Collaborate collaborate = collaborateRepository.findByIdAndIsDeactivated(collaboratorId, false);
        TeamMember teamMember = new TeamMember();
        teamMember.setUserId(collaborate.getUserId());
        teamMember.setTeamMemberRole(TeamMemberRole.teamMember);
        Team team = teamRepository.findById(Long.valueOf(7)).orElseThrow();
        teamMember.setTeam(team);
        MemberRole role = memberRoleRepository.findById(Long.valueOf(1)).orElseThrow();
        teamMember.setMemberRole(role);
        teamMemberRepository.save(teamMember);
        collaborate.setIsDeactivated(true);
        collaborateRepository.save(collaborate);
        return new BaseDTO();
    }

    public List<CollaboratorsDto> getAcceptedTeam(Long TeamId) {
        List<TeamMember> teamMemberList = teamMemberRepository.findByTeamId(TeamId);

        return teamMemberList.stream()
                .map(collaborator -> {

                    Users user = usersRepository.findById(collaborator.getUserId()).orElseThrow(
                            () -> new RuntimeException("User not found for collaborator: " + collaborator.getId())
                    );
                    return new CollaboratorsDto(
                            collaborator.getId(),
                            user.getName(),
                            user.getProjectRating(),
                            user.getSkillsScore()
                    );})
                .collect(Collectors.toList());
    }
}