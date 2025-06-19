package com.unilink.project_service.service.project;

import com.unilink.common.config.UserContext;
import com.unilink.common.dto.ApiResponse;
import com.unilink.project_service.dto.project.*;
import com.unilink.project_service.entity.project.Project;
import com.unilink.project_service.entity.project.Team;
import com.unilink.project_service.repository.project.ProjectRepository;
import com.unilink.project_service.repository.project.SkillRequiredRepository;
import com.unilink.project_service.repository.project.TeamRepository;
import com.unilink.project_service.utils.ProjectStatus;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectService {
    Logger logger = LoggerFactory.getLogger(ProjectService.class);
    ApiResponse response = new ApiResponse();
    @Autowired
    private ProjectRepository projectRepository;
//    @Autowired
//    private UsersRepository usersRepository;
    @Autowired
    private SkillRequiredRepository skillRequiredRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TeamRepository teamRepository;
//    @Autowired
//    private SkillRepository skillRepository;
    @Autowired
    private UserContext userContext;


    public List<ProjectResponse> getLayoutProjectCard() {
        List<Project> projects = projectRepository.findByIsDeactivated(false);
        return null;
//        return projects.stream()
//                .map(project -> {
//                    ProjectResponse projectResponse = modelMapper.map(project, ProjectResponse.class);
//                    Users user = usersRepository.findById(project.getUserId()).orElseThrow(
//                            () -> new ResolutionException("User not found for project: " + project.getId())
//                    );
//                    projectResponse.setHostImage(user.getProfilePicture());
//                    List<SkillRequired> skillRequired = skillRequiredRepository.findByProjectIdAndIsDeactivated(project.getId(), false);
//                    List<Skill> skills = skillRequired.stream().map(SkillRequired::getSkill).toList();
//                    List<String> skillNames = skills.stream().map(
//                            Skill::getName
//                    ).toList();
//                    projectResponse.setSkillRequired(skillNames);
//                    projectResponse.setDescription(project.getDescription());
//                    return projectResponse;
//                })
//                .collect(Collectors.toList());
    }

    public List<MyProjectCardDTO> getMyProjectCard() {
        List<Project> myProjectCard = projectRepository.findByUserIdAndIsDeactivated(Long.valueOf(userContext.getUserId()), false);
        Long userIds =1L;
//        Optional<Users> user = usersRepository.findById(userIds);
//        List<MyProjectCardDTO> myProjectCardDTOs = myProjectCard.stream()
//                .map(project -> {
//                    MyProjectCardDTO dto = modelMapper.map(project, MyProjectCardDTO.class);
//                    dto.setImages(Collections.singletonList(user.get().getProfilePicture()));
//                    dto.setHostedBy(user.get().getName());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//
//        return myProjectCardDTOs;
        return null;
    }

    public ProjectDescriptionDTO getProjectDescriptionById(Long id) {
        Project project = projectRepository.findByIdAndIsDeactivated(id, false).orElseThrow(
                () -> new ResolutionException("Project not found with id: " + id)
        );
        LocalDate startDate = project.getStartDate();
        LocalDate endDate = project.getEndDate();
        Double durationInMonths = (double) ChronoUnit.MONTHS.between(startDate, endDate);
        ProjectDescriptionDTO projectDescriptionDTO = modelMapper.map(project, ProjectDescriptionDTO.class);
        projectDescriptionDTO.setDuration(durationInMonths);
        projectDescriptionDTO.setTitle(project.getTitle());
        projectDescriptionDTO.setProjectType(project.getProjectType());
//        Users user = usersRepository.findById(project.getUserId()).orElseThrow(
//                () -> new ResolutionException("User not found for project: " + project.getId())
//        );

        return projectDescriptionDTO;
    }

    public ProjectDTO addProject(ProjectDTO projectDto) {
        Project project = modelMapper.map(projectDto, Project.class);

        Team team = new Team();
        team.setTeamSize(projectDto.getTeamSize());
        teamRepository.save(team);

        project.setTeam(team);
        project.setUserId(1L); // Example fixed user, adjust as needed
        project.setProjectStatus(ProjectStatus.Ongoing);
        projectRepository.save(project);

        // Handle skill requirements
//        if (projectDto.getSkillIds() != null && !projectDto.getSkillIds().isEmpty()) {
//            for (Long skillId : projectDto.getSkillIds()) {
//                Skill skill = skillRepository.findById(skillId)
//                        .orElseThrow(() -> new RuntimeException("Skill not found with id: " + skillId));
//
//                SkillRequired skillRequired = new SkillRequired();
//                skillRequired.setProject(project);
//                skillRequired.setSkill(skill);
//                skillRequired.setProficiencyLevel("Basic");  // or get from DTO if available
//
//                skillRequiredRepository.save(skillRequired);
//            }
//        }


        return projectDto;
    }


    public List<OngoingTitlesDTO> getProjectTitles() {
        List<Project> projects = projectRepository.findAll();
        List<OngoingTitlesDTO> titles = projects.stream()
                .map(project -> new OngoingTitlesDTO(project.getId(),project.getTitle())) // Assuming 'getTitle()' method exists in 'Project'
                .collect(Collectors.toList());

        return titles;
    }

    public List<SkillDropdownDTO> getSkillDropdownList() {
//        List<Skill> skills = skillRepository.findByIsDeactivated(false);
//        return skills.stream()
//                .map(skill -> modelMapper.map(skill, SkillDropdownDTO.class))
//                .collect(Collectors.toList());
        return null;
    }
}