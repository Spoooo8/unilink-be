package com.unilink.quiz_service.service.project;

import com.unilink.quiz_service.dto.project.MemberRoleDTO;
import com.unilink.quiz_service.entity.project.MemberRole;
import com.unilink.quiz_service.repository.project.MemberRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberRoleService {

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    public List<MemberRoleDTO> getMemberRolesByProjectId(Long projectId) {
        List<MemberRole> memberRoles = memberRoleRepository.findByProjectId(projectId);
        return memberRoles.stream()
                .map(memberRole -> {
                    MemberRoleDTO roleDTO = new MemberRoleDTO();
                    roleDTO.setId(memberRole.getId());
                    roleDTO.setRoleName(memberRole.getRoleName());
                    return roleDTO;
                })
                .collect(Collectors.toList());
    }
}
