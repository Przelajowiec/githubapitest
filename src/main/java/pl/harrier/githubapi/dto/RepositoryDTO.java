package pl.harrier.githubapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RepositoryDTO {
    private String name;
    private String ownerLogin;
    private List<BranchDTO> branches;

    @Data
    @AllArgsConstructor
    public static class BranchDTO {
        private String name;
        private String lastCommitSha;
    }
}