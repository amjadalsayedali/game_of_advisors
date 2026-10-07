package de.propra.game_of_advisors.security;

import de.propra.game_of_advisors.user.GitHubUserId;
import org.jspecify.annotations.NonNull;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public final class GitHubIdentity {

    private GitHubIdentity() {
    }

    public static GitHubUserId from(OAuth2User user) {
        return fromAttributes(user.getAttributes());
    }

    public static @NonNull GitHubUserId fromAttributes(Map<String, Object> attributes) {
        Object id = attributes.get("id");

        if (!(id instanceof Number number)) {
            throw new IllegalArgumentException(
                    "OAuth2 user has no numeric GitHub ID"
            );
        }
        return new GitHubUserId(number.longValue());
    }
}
