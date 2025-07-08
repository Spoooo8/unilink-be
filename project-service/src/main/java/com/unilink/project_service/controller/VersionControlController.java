package com.unilink.project_service.controller;

import com.unilink.project_service.dto.CommitRequestDTO;
import com.unilink.project_service.service.VersionControlService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class VersionControlController {

    @Autowired
    private VersionControlService versionControlService;

    @PostMapping("{projectId}/clone")
    public ResponseEntity<String> cloneRepository(@PathVariable("projectId") Long projectId) throws GitAPIException, IOException {
        versionControlService.cloneRepository(projectId);
        return ResponseEntity.ok("✅ Repository cloned successfully.");
    }

    @GetMapping("{projectId}/history")
    public List<String> getHistory(@PathVariable("projectId") Long projectId) throws GitAPIException, IOException, IOException {
        return versionControlService.getCommitHistory(projectId);
    }

    @PostMapping("/init/{projectId}")
    public void initRepo(@PathVariable String projectId) throws GitAPIException {
        versionControlService.initRepository(projectId);
    }

    @PostMapping("/commit/{projectId}")
    public void commit(@PathVariable String projectId, @RequestBody CommitRequestDTO request) throws GitAPIException, IOException {
        versionControlService.commitFile(projectId, request.getFilePath(), request.getContent(), request.getMessage(), request.getAuthor());
    }

    @PostMapping("/branch/{projectId}/{branchName}")
    public void createBranch(@PathVariable String projectId, @PathVariable String branchName) throws GitAPIException, IOException {
        versionControlService.createBranch(projectId, branchName);
    }




}

