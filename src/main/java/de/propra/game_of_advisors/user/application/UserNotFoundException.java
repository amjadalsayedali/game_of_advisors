package de.propra.game_of_advisors.user.application;

import de.propra.game_of_advisors.user.domain.GitHubUserId;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(GitHubUserId gitHubUserId) {
        super(
                "User with GitHub ID %d not found."
                        .formatted(gitHubUserId.value())
        );
    }
}
