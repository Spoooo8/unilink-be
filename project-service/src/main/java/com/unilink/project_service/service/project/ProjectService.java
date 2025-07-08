package com.unilink.project_service.service.project;

import com.unilink.common.dto.ApiResponse;
import com.unilink.common.exceptions.UnilinkResourceNotFoundException;
import com.unilink.project_service.config.UserContextId;
import com.unilink.project_service.dto.project.*;
import com.unilink.project_service.entity.project.Project;
import com.unilink.project_service.entity.project.SkillRequired;
import com.unilink.project_service.entity.project.Team;
import com.unilink.project_service.entity.project.TeamMember;
import com.unilink.project_service.repository.project.ProjectRepository;
import com.unilink.project_service.repository.project.SkillRequiredRepository;
import com.unilink.project_service.repository.project.TeamMemberRepository;
import com.unilink.project_service.repository.project.TeamRepository;
import com.unilink.project_service.utils.ProjectStatus;
import com.unilink.project_service.utils.TeamMemberRole;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectService {
    Logger logger = LoggerFactory.getLogger(ProjectService.class);
    ApiResponse response = new ApiResponse();
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SkillRequiredRepository skillRequiredRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserContextId userContext;

    @Autowired
    private TeamMemberRepository teamMemberRepository;


    public ProjectDTO addProject(ProjectDTO request) {
        Project newProject = new Project();
        newProject.setUserId(Long.valueOf(userContext.getUserId()));
        newProject.setProjectStatus(ProjectStatus.Ongoing);
        newProject.setTitle(request.getTitle());
        newProject.setRepoLink(request.getRepoLink());
        newProject.setDescription(request.getDescription());
        newProject.setImageUrl(request.getImageUrl());
        newProject.setStartDate(request.getStartDate());
        newProject.setEndDate(request.getEndDate());
        newProject.setApplicationDeadline(request.getApplicationDeadline());
        newProject.setComplexityLevel(request.getComplexityLevel());
        newProject.setProjectVisibility(request.getProjectVisibility());

        // First, create and save the team
        Team newTeam = new Team();
        newTeam.setTeamSize(request.getTeamSize());
        newTeam.setTeamCode(generateUniqueTeamCode());
        teamRepository.save(newTeam);

        newProject.setTeam(newTeam);

        TeamMember newTeamMember = new TeamMember();
        newTeamMember.setTeam(newTeam);
        newTeamMember.setUserId(Long.valueOf(userContext.getUserId()));
        newTeamMember.setTeamMemberRole(TeamMemberRole.host);
        teamMemberRepository.save(newTeamMember);

        // ✅ Save the project first
        projectRepository.save(newProject);

        // Now, save the required skills
        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
            for (Integer skillId : request.getSkillIds()) {
                SkillRequired skillRequired = new SkillRequired();
                skillRequired.setProject(newProject); // Now newProject is persistent and has an ID
                skillRequired.setSkillId(skillId);
                skillRequired.setProficiencyLevel("Basic");

                skillRequiredRepository.save(skillRequired);
            }
        }

        return request;
    }

    public List<MyProjectCardDTO> getMyProjectCard() {
        List<TeamMember> member = teamMemberRepository.findByUserId(Long.valueOf(userContext.getUserId()));
        List<Long> teamIds = member.stream()
                .map(m -> m.getTeam().getId())
                .collect(Collectors.toList());
        List<Project> myProjectCard =  projectRepository.findAllByTeamIdIn(teamIds);
        List<MyProjectCardDTO> myProjectCardDTOs = myProjectCard.stream()
                .map(project -> {
                    MyProjectCardDTO dto = new MyProjectCardDTO();
                    dto.setId(project.getId());
                    dto.setDescription(project.getDescription());
                    dto.setTitle(project.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());

        return myProjectCardDTOs;
    }

    private String generateUniqueTeamCode() {
        // Generate UUID and take first 8 characters
        return "TEAM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<ProjectResponse> getLayoutProjectCard() {
        List<TeamMember> teamMembers = teamMemberRepository.findByUserId(Long.valueOf(userContext.getUserId()));
        Set<Long> teamIds = teamMembers.stream().map(member -> member.getTeam().getId()).collect(Collectors.toSet());
        List<Project> projects = projectRepository.findByTeamIdNotInAndIsDeactivated(teamIds,false);
        return projects.stream()
                .map(project -> {
                    ProjectResponse projectResponse = modelMapper.map(project, ProjectResponse.class);
                    List<SkillRequired> skillRequired = skillRequiredRepository.findByProjectIdAndIsDeactivated(project.getId(), false);
//                    List<Skill> skills = skillRequired.stream().map(SkillRequired::getSkill).toList();
//                    List<String> skillNames = skills.stream().map(
//                            Skill::getName
//                    ).toList();
//                    projectResponse.setSkillRequired(skillNames);
                    projectResponse.setSkillRequired(null);
                    projectResponse.setApplicationDeadline(project.getApplicationDeadline());
                    return projectResponse;
                })
                .collect(Collectors.toList());
    }


    public ProjectDescriptionDTO getProjectDescriptionById(Long id) {
        Project project = projectRepository.findByIdAndIsDeactivated(id, false).orElseThrow(
                () -> new ResolutionException("Project not found with id: " + id)
        );
        ProjectDescriptionDTO projectDescriptionDTO = new ProjectDescriptionDTO();
        projectDescriptionDTO.setId(project.getId());
        projectDescriptionDTO.setTitle(project.getTitle());
        projectDescriptionDTO.setHostName(null);
        projectDescriptionDTO.setDescription(project.getDescription());
        projectDescriptionDTO.setApplicationDeadline(String.valueOf(project.getApplicationDeadline()));
        projectDescriptionDTO.setStartDate(String.valueOf(project.getStartDate()));
        projectDescriptionDTO.setEndDate(String.valueOf(project.getEndDate()));
        projectDescriptionDTO.setDuration(null);
        projectDescriptionDTO.setSkills(null);
        projectDescriptionDTO.setComplexityLevel(String.valueOf(project.getComplexityLevel()));
        projectDescriptionDTO.setTeamSize(project.getTeam().getTeamSize()
        );

        return projectDescriptionDTO;
    }

    public HostIdDTO getHostId(Long projectId) {
        HostIdDTO hostIdDTO = new HostIdDTO();
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new UnilinkResourceNotFoundException("project not found"));
        TeamMember teamMember = teamMemberRepository.findByUserIdAndTeamId(Long.valueOf(userContext.getUserId()),project.getTeam().getId());
        hostIdDTO.setProjectId(projectId);
        hostIdDTO.setUserId(Long.valueOf(userContext.getUserId()));
        hostIdDTO.setHostId(project.getUserId());
        if (teamMember != null) {
            hostIdDTO.setTeamMemberId(teamMember.getId());
        } else {
            hostIdDTO.setTeamMemberId(null);

        }
        return hostIdDTO;
    }
}