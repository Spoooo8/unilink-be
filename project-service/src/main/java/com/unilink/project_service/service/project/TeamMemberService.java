package com.unilink.project_service.service.project;

import com.unilink.common.dto.BaseDTO;
import com.unilink.common.dto.ResponseDTO;
import com.unilink.common.exceptions.UnilinkResourceNotFoundException;
import com.unilink.project_service.dto.UserResponse;
import com.unilink.project_service.dto.project.CollaboratorsDTO;
import com.unilink.project_service.dto.project.ProfileDTO;
import com.unilink.project_service.dto.project.ProjectProgressDTO;
import com.unilink.project_service.dto.project.UserProfileResponse;
import com.unilink.project_service.entity.project.Collaborate;
import com.unilink.project_service.entity.project.Project;
import com.unilink.project_service.entity.project.Team;
import com.unilink.project_service.entity.project.TeamMember;
import com.unilink.project_service.repository.project.CollaborateRepository;
import com.unilink.project_service.repository.project.ProjectRepository;
import com.unilink.project_service.repository.project.TeamMemberRepository;
import com.unilink.project_service.repository.project.TeamRepository;
import com.unilink.project_service.service.client.UserFeignClient;
import com.unilink.project_service.utils.TeamMemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamMemberService {
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CollaborateRepository collaborateRepository;

    @Autowired
    private UserFeignClient userFeignClient;


    public ResponseDTO acceptTeam(Long collaboratorId, Long projectId) {
        Collaborate collaborate = collaborateRepository.findByIdAndIsDeactivated(collaboratorId, false);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new UnilinkResourceNotFoundException("project not found"));

        TeamMember teamMember = new TeamMember();
        teamMember.setUserId(collaborate.getUserId());
        teamMember.setTeamMemberRole(TeamMemberRole.teamMember);
        Team team = teamRepository.findById(project.getTeam().getId()).orElseThrow();
        teamMember.setTeam(team);

        teamMemberRepository.save(teamMember);
        collaborate.setIsDeactivated(true);
        collaborateRepository.save(collaborate);
        return new ResponseDTO("200", "OK");
    }

    public List<CollaboratorsDTO> getAcceptedTeam(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new UnilinkResourceNotFoundException("project not found"));
        List<TeamMember> teamMemberList = teamMemberRepository.findByTeamId(project.getTeam().getId());

        return teamMemberList.stream()
                .map(collaborator -> {

                    UserResponse user = userFeignClient.getUserById(collaborator.getUserId());
                    return new CollaboratorsDTO(
                            user.getId(),
                           null,
                            user.getName(),
                            user.getProjectRating(),
                            user.getSkillScore()
                    );
                })
                .collect(Collectors.toList());
    }

    public ProfileDTO getProfile(Long userid) {
        // Fetch user profile from the user service
        UserProfileResponse userProfileResponse = userFeignClient.getUserProfileById(userid);

        // Initialize ProfileDTO and map all common fields
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(userProfileResponse.getName());
        profileDTO.setAboutMe(userProfileResponse.getAboutMe());
        profileDTO.setDesignation(userProfileResponse.getDesignation());
        profileDTO.setSkillsScore(userProfileResponse.getSkillsScore());
        profileDTO.setProjectCount(userProfileResponse.getProjectCount());
        profileDTO.setSocialLinks(userProfileResponse.getSocialLinks());
        profileDTO.setPortfolioUrl(userProfileResponse.getPortfolioUrl());
        profileDTO.setGithubUrl(userProfileResponse.getGithubUrl());
        profileDTO.setMentorStatus(userProfileResponse.getMentorStatus());
        profileDTO.setSkills(userProfileResponse.getSkills());

        List<ProjectProgressDTO> projectProgressList = getProjectProgressByUserId(userid);

        profileDTO.setProjectProgressDTO(projectProgressList);

        return profileDTO;
    }

    public List<ProjectProgressDTO> getProjectProgressByUserId(Long userId) {
        // Step 1: Find all team memberships for the user
        List<TeamMember> teamMembers = teamMemberRepository.findByUserId(userId);

        if (teamMembers.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> teamIds = teamMembers.stream()
                .map(teamMember -> teamMember.getTeam().getId())
                .collect(Collectors.toList());

        List<Project> projects = projectRepository.findAllByTeamIdIn(teamIds);


        return projects.stream().map(project -> {
            double progress = calculateProgressPercentage(project.getStartDate(), project.getEndDate());
            return new ProjectProgressDTO(project.getId(), project.getTitle(), (int) progress);
        }).collect(Collectors.toList());
    }


    private double calculateProgressPercentage(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return 0.0;
        }

        LocalDate currentDate = LocalDate.now();

        long totalDuration = ChronoUnit.DAYS.between(startDate, endDate);
        long elapsedDuration = ChronoUnit.DAYS.between(startDate, currentDate);

        if (elapsedDuration <= 0) {
            return 0.0;
        } else if (elapsedDuration >= totalDuration) {
            return 100.0;
        } else {
            return ((double) elapsedDuration / totalDuration) * 100;
        }
    }

}