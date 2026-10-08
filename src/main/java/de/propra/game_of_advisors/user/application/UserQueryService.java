package de.propra.game_of_advisors.user.application;

import de.propra.game_of_advisors.user.domain.GitHubUserId;
import de.propra.game_of_advisors.user.domain.UserRole;

public final class UserQueryService {

    private final UserRepository userRepository;

    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isAdvisor(GitHubUserId githubUserId) {
        return userRepository.findById(githubUserId)
                .map(user -> user.hasRole(UserRole.ADVISOR))
                .orElse(false);
    }
}
