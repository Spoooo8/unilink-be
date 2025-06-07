package com.unilink.quiz_service.service.project;

import com.unilink.quiz_service.dto.project.CollaboratorsDto;
import com.unilink.quiz_service.dto.project.JoinProjectDTO;
import com.unilink.quiz_service.dto.project.RoleDropDownDTO;
import com.unilink.quiz_service.entity.project.Collaborate;
import com.unilink.quiz_service.entity.project.MemberRole;
import com.unilink.quiz_service.entity.project.Project;
import com.unilink.quiz_service.entity.users.Users;
import com.unilink.quiz_service.repository.project.CollaborateRepository;
import com.unilink.quiz_service.repository.project.MemberRoleRepository;
import com.unilink.quiz_service.repository.project.ProjectRepository;
import com.unilink.quiz_service.repository.project.RoleRepository;
import com.unilink.quiz_service.repository.users.UsersRepository;
import com.unilink.quiz_service.utils.TeamMemberRole;
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
    private UsersRepository usersRepository;

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

    public List<CollaboratorsDto> getCollaborators(Long projectId){

        List<Collaborate> collaborators = collaborateRepository.findByProjectIdAndIsDeactivated(projectId, false);
        return collaborators.stream()
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

