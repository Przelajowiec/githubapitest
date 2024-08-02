package pl.harrier.githubapi.service;

import pl.harrier.githubapi.dto.RepositoryDTO;
import pl.harrier.githubapi.exception.UserNotFoundException;
import pl.harrier.githubapi.model.Branch;
import pl.harrier.githubapi.model.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    @Value("${github.api.url}")
    private String githubApiUrl;

    @Value("${github.api.token}")
    private String githubApiToken;

    private final RestTemplate restTemplate;

    public GithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepositoryDTO> getRepositories(String username) {
        try {
            String url = githubApiUrl + "/users/" + username + "/repos";
            HttpEntity<String> entity = new HttpEntity<>(createHeaders(githubApiToken));
            ResponseEntity<Repository[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Repository[].class);

            Repository[] repos = response.getBody();
            List<RepositoryDTO> result = new ArrayList<>();
            if (repos != null) {
                for (Repository repo : repos) {
                    if (!repo.isFork()) {
                        List<RepositoryDTO.BranchDTO> branches = getBranches(username, repo.getName()).stream()
                                .map(branch -> new RepositoryDTO.BranchDTO(branch.getName(), branch.getLastCommitSha()))
                                .collect(Collectors.toList());
                        result.add(new RepositoryDTO(repo.getName(), repo.getOwnerLogin(), branches));
                    }
                }
            }
            return result;
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not found: " + username);
        }
    }

    private List<Branch> getBranches(String owner, String repoName) {
        String url = githubApiUrl + "/repos/" + owner + "/" + repoName + "/branches";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(githubApiToken));
        ResponseEntity<Branch[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Branch[].class);

        Branch[] branches = response.getBody();
        return branches != null ? List.of(branches) : List.of();
    }

    private HttpHeaders createHeaders(String token) {
        return new HttpHeaders() {{
            String authHeader = "Bearer " + token;
            set("Authorization", authHeader);
        }};
    }
}