package com.unilink.project_service.service.project;

import com.unilink.project_service.config.UserContextId;
import com.unilink.project_service.dto.UserResponse;
import com.unilink.project_service.dto.project.CollaboratorsDTO;
import com.unilink.project_service.dto.project.JoinProjectDTO;
import com.unilink.project_service.dto.project.RoleDropDownDTO;
import com.unilink.project_service.entity.project.Collaborate;
import com.unilink.project_service.entity.project.Project;
import com.unilink.project_service.repository.project.CollaborateRepository;
import com.unilink.project_service.repository.project.ProjectRepository;
import com.unilink.project_service.service.client.UserFeignClient;
import com.unilink.project_service.utils.TeamMemberRole;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollaborateService {
    @Autowired
    private CollaborateRepository collaborateRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private UserContextId userContext;



    public void joinProject(Long projectId, JoinProjectDTO joinProjectDTO) {
        Long userId = Long.valueOf(userContext.getUserId());
        Collaborate newCollaborator = new Collaborate();
        Project project = projectRepository.findById(projectId).orElseThrow();
        newCollaborator.setProject(project);
        newCollaborator.setUserId(userId);
        newCollaborator.setRequestDate(LocalDate.now());
        newCollaborator.setMessage(joinProjectDTO.getMessage());
        newCollaborator.setRole(TeamMemberRole.teamMember);
        collaborateRepository.save(newCollaborator);
    }


    public List<CollaboratorsDTO> getCollaborators(Long projectId){

        List<Collaborate> collaborators = collaborateRepository.findByProjectIdAndIsDeactivated(projectId, false);
        return
                collaborators.stream()
                .map(collaborator -> {

                    UserResponse user = userFeignClient.getUserById(collaborator.getUserId());
                   return new CollaboratorsDTO(
                        user.getId(),
                           collaborator.getId(),
                         user.getName(), user.getProjectRating(),user.getSkillScore()
                 );})
                .collect(Collectors.toList());

    }


    }

