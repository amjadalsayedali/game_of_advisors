package de.propra.game_of_advisors.security;

import de.propra.game_of_advisors.user.domain.GitHubUserId;

public class AdminUsers {
    private final AdminProperties properties;

    public AdminUsers(AdminProperties properties) {
        this.properties = properties;
    }

    public boolean isAdmin(GitHubUserId gitHubUserId) {
        return properties.adminGithubIds()
                .contains(gitHubUserId.value());
    }
}
