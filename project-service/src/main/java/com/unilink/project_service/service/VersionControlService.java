package com.unilink.project_service.service;

import com.unilink.common.exceptions.UnilinkBadRequestException;
import com.unilink.common.exceptions.UnilinkResourceNotFoundException;
import com.unilink.project_service.entity.project.Project;
import com.unilink.project_service.repository.project.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VersionControlService {
    private static final Logger logger = LoggerFactory.getLogger(VersionControlService.class);

    @Autowired
    private ProjectRepository projectRepository;

    public void cloneRepository(Long projectId) throws GitAPIException, IOException {
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new UnilinkResourceNotFoundException("project not found"));
        String remoteUrl = project.getRepoLink();
        if (remoteUrl == null || remoteUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Remote URL cannot be null or empty");
        }

        String repoPath = getRepoBasePath(remoteUrl);
        File repoDir = new File(repoPath);

        if (repoDir.exists()) {
            throw new UnilinkBadRequestException("Repository directory already exists for: " + repoPath);
        }
        if (!repoDir.mkdirs()) {
            throw new UnilinkBadRequestException("Failed to create directory: " + repoDir.getAbsolutePath());
        }

        try (Git git = Git.cloneRepository()
                .setURI(remoteUrl)
                .setDirectory(repoDir)
                .call()) {
            logger.info("Cloned repository from {} to {}", remoteUrl, repoDir.getAbsolutePath());
        }
    }

    public List<String> getCommitHistory(Long projectId) throws IOException, GitAPIException {
        List<String> history = new ArrayList<>();
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new UnilinkResourceNotFoundException("project not found"));
        String remoteUrl = project.getRepoLink();
        File repoBase = new File(getRepoBasePath(remoteUrl));
        File gitDir = new File(repoBase, ".git");

        if (!gitDir.exists()) {
            System.out.println("Cloning repository to " + repoBase.getAbsolutePath());

            Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(repoBase)
                    .setCloneAllBranches(true)
                    .call();
        }

        // Open the repository
        try (Repository repository = new FileRepositoryBuilder()
                .setGitDir(gitDir)
                .readEnvironment()
                .findGitDir()
                .build();
             Git git = new Git(repository)) {

            ObjectId head = repository.resolve("HEAD");
            if (head == null) {
                throw new IllegalStateException("Repository has no HEAD — probably no commits exist.");
            }

            Iterable<RevCommit> commits = git.log().call(); // .all() not needed unless you want history from all refs

            for (RevCommit commit : commits) {
                history.add(commit.getFullMessage()
                        + " by " + commit.getAuthorIdent().getName()
                        + " at " + Instant.ofEpochSecond(commit.getCommitTime()));
            }
        }

        return history;
    }


    // Extracts repo name from URL
    private String extractRepoName(String remoteUrl) {
        if (remoteUrl == null || remoteUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Remote URL cannot be null or empty");
        }
        String cleanedUrl = remoteUrl.endsWith(".git") ? remoteUrl.substring(0, remoteUrl.length() - 4) : remoteUrl;
        return cleanedUrl.substring(cleanedUrl.lastIndexOf('/') + 1);
    }

    // Constructs base path like: ~/Documents/unilink/{repo-name}
    private String getRepoBasePath(String remoteUrl) {
        String repoName = extractRepoName(remoteUrl);
        String userHome = System.getProperty("user.home");
        return userHome + File.separator + "Documents" + File.separator + "unilink" + File.separator + repoName;
    }

    public void initRepository(String remoteUrl) throws GitAPIException {
        String repoPath = getRepoBasePath(remoteUrl);
        File repoDir = new File(repoPath);

        try (Git git = Git.init().setDirectory(repoDir).call()) {
            System.out.println("Repository initialized at: " + repoDir.getAbsolutePath());
        }
    }

    public void commitFile(String remoteUrl, String filePath, String content, String commitMessage, String author) throws GitAPIException, IOException {
        if (filePath == null || filePath.trim().isEmpty() || filePath.contains("..")) {
            throw new IllegalArgumentException("Invalid file path: " + filePath);
        }
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        if (commitMessage == null || commitMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("Commit message cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }

        String repoPath = getRepoBasePath(remoteUrl) + File.separator + ".git";
        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        try (Repository repository = builder.setGitDir(new File(repoPath)).readEnvironment().findGitDir().build();
             Git git = new Git(repository)) {

            File file = new File(repository.getWorkTree(), filePath);
            Path filePathObj = file.toPath();
            Path parentDir = filePathObj.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }

            Files.write(filePathObj, content.getBytes());
            logger.info("Wrote content to file: {}", file.getAbsolutePath());

            git.add().addFilepattern(filePath).call();
            git.commit()
                    .setMessage(commitMessage)
                    .setAuthor(author, author + "@student.collab")
                    .call();
            logger.info("Committed file {} for repo {} by author {}", filePath, remoteUrl, author);
        }
    }

    public void createBranch(String remoteUrl, String branchName) throws GitAPIException, IOException {
        String repoPath = getRepoBasePath(remoteUrl) + File.separator + ".git";
        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        try (Repository repository = builder.setGitDir(new File(repoPath)).readEnvironment().findGitDir().build();
             Git git = new Git(repository)) {
            git.branchCreate()
                    .setName(branchName)
                    .call();
        }
    }


    public void pushToRemote(String remoteUrl, String username, String token) throws IOException, GitAPIException {
        String repoPath = getRepoBasePath(remoteUrl) + File.separator + ".git";

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try (Repository repository = builder.setGitDir(new File(repoPath))
                .readEnvironment().findGitDir().build();
             Git git = new Git(repository)) {

            git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, token))
                    .call();

            logger.info("Pushed changes to remote repository: {}", remoteUrl);
        }
    }
}
