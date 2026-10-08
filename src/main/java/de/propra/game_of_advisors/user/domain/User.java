package de.propra.game_of_advisors.user.domain;

import java.util.EnumSet;
import java.util.Set;

public class User {

    private final GitHubUserId gitHubUserId;
    private final Set<UserRole> roles;


    public User(GitHubUserId gitHubUserId) {
        this.gitHubUserId = gitHubUserId;
        this.roles = EnumSet.of(UserRole.STUDENT);
    }

    public GitHubUserId getGitHubUserId() {
        return gitHubUserId;
    }

    public Set<UserRole> roles() {
        return Set.copyOf(roles);
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public void grantAdvisorRole() {
        roles.add(UserRole.ADVISOR);
    }
}
