package com.unilink.quiz_service.controller.project;

import com.unilink.quiz_service.dto.project.MemberRoleDTO;
import com.unilink.quiz_service.service.project.MemberRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/teams")
public class MemberRoleController {

    @Autowired
    private MemberRoleService memberRoleService;

    @GetMapping("/project/{projectId}/role")
    public List<MemberRoleDTO> getMemberRolesByProjectId(@PathVariable Long projectId) {
        return memberRoleService.getMemberRolesByProjectId(projectId);
    }
}
