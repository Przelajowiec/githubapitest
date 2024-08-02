package pl.harrier.githubapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Branch {
    private String name;
    private Commit commit;

    public String getLastCommitSha() {
        return commit != null ? commit.getSha() : null;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Commit {
        private String sha;
    }
}