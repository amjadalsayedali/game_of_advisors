package de.propra.game_of_advisors.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "game-of-advisors.security")
public record AdminProperties(
        List<Long> adminGithubIds
) {
    public AdminProperties {
        adminGithubIds = adminGithubIds == null ? List.of() : List.copyOf(adminGithubIds);
    }
}
