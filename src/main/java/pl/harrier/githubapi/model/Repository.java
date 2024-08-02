package pl.harrier.githubapi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {
    private String name;

    @JsonProperty("owner")
    private Owner owner;

    private boolean fork;

    private List<Branch> branches;

    public String getOwnerLogin() {
        return owner != null ? owner.getLogin() : null;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Owner {
        private String login;
    }
}
