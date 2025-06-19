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
@RequestMapping("/api/version-control")
public class VersionControlController {

    @Autowired
    private VersionControlService versionControlService;

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

    @GetMapping("/history")
    public List<String> getHistory(@RequestParam String remoteUrl) throws GitAPIException, IOException, IOException {
        return versionControlService.getCommitHistory(remoteUrl);
    }

    @PostMapping("/clone")
    public ResponseEntity<String> cloneRepository(
            @RequestParam String remoteUrl) throws GitAPIException, IOException {
            versionControlService.cloneRepository( remoteUrl);
            return ResponseEntity.ok("✅ Repository cloned successfully.");
    }
}

