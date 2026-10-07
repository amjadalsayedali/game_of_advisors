package de.propra.game_of_advisors.security;

import de.propra.game_of_advisors.user.GitHubUserId;
import org.springframework.security.oauth2.core.user.OAuth2User;

public final class GitHubIdentity {

    private GitHubIdentity() {
    }

    public static GitHubUserId from(OAuth2User user) {
        Object id = user.getAttributes().get("id");

        if (!(id instanceof Number number)) {
            throw new IllegalArgumentException(
                    "OAuth2 user has no numeric GitHub ID"
            );
        }
    return new GitHubUserId(number.longValue());
    }
}
