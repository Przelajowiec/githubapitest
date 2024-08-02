package pl.harrier.githubapi.controller;


import pl.harrier.githubapi.dto.RepositoryDTO;
import pl.harrier.githubapi.service.GithubService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {
    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<List<RepositoryDTO>> getRepositories(
            @PathVariable String username,
            @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader) {

        if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            List<RepositoryDTO> repositories = githubService.getRepositories(username);
            return ResponseEntity.ok(repositories);
        } else {
            return ResponseEntity.status(406).build();  // Not Acceptable
        }
    }
}