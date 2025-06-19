package com.unilink.project_service.service.project;

import com.unilink.project_service.dto.UserResponse;
import com.unilink.project_service.dto.project.CollaboratorsDTO;
import com.unilink.project_service.dto.project.JoinProjectDTO;
import com.unilink.project_service.dto.project.RoleDropDownDTO;
import com.unilink.project_service.entity.project.Collaborate;
import com.unilink.project_service.entity.project.MemberRole;
import com.unilink.project_service.entity.project.Project;
import com.unilink.project_service.repository.project.CollaborateRepository;
import com.unilink.project_service.repository.project.MemberRoleRepository;
import com.unilink.project_service.repository.project.ProjectRepository;
import com.unilink.project_service.repository.project.RoleRepository;
import com.unilink.project_service.service.client.UserFeignClient;
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
    private RoleRepository roleRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserFeignClient userFeignClient;



    public void joinProject(Long projectId, JoinProjectDTO joinProjectDTO) {
        Collaborate newCollaborator = new Collaborate();
        Project project = projectRepository.findById(projectId).orElseThrow();
        newCollaborator.setProject(project);
        newCollaborator.setUserId(1L);
        newCollaborator.setRequestDate(LocalDate.now());
        newCollaborator.setMessage(joinProjectDTO.getMessage());
//        MemberRole roleRequested = memberRoleRepository.findById(2L).orElseThrow();
//        newCollaborator.setRoleRequested(roleRequested.getRoleName());
//       newCollaborator.setRole(TeamMemberRole.teamMember);
        collaborateRepository.save(newCollaborator);
    }
    public List<RoleDropDownDTO> getRoleDropDownDTO(){
        List<MemberRole> roles = memberRoleRepository.findAll();
        return roles.stream()
                .map(skill -> modelMapper.map(skill, RoleDropDownDTO.class))
                .collect(Collectors.toList());
    }

    public List<CollaboratorsDTO> getCollaborators(Long projectId){

        List<Collaborate> collaborators = collaborateRepository.findByProjectIdAndIsDeactivated(projectId, false);
        return
                collaborators.stream()
                .map(collaborator -> {

                        UserResponse user = userFeignClient.getUserById(1L);
                   return new CollaboratorsDTO(
                        collaborator.getId(),
                         user.getName(), 2.0,2.0
                 );})
                .collect(Collectors.toList());

    }


    }

