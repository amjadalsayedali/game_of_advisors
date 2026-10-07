package de.propra.game_of_advisors.user;

public record GitHubUserId(long value) {
    public GitHubUserId {
        if (value <= 0) {
            throw new IllegalArgumentException(
                    "GitHub user Id must be positive"
            );
        }
    }
}
