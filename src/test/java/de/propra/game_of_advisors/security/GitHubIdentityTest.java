package de.propra.game_of_advisors.security;

import de.propra.game_of_advisors.user.domain.GitHubUserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GitHubIdentityTest {

    @Test
    @DisplayName("extracts numeric GitHub Id")
    void test_01() {
        DefaultOAuth2User user = new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                Map.of(
                       "id", 123456789L,
                       "login" , "karla-turing"
                ),
                "id"
        );

        GitHubUserId result = GitHubIdentity.from(user);

        assertThat(result)
                .isEqualTo(new GitHubUserId(123456789L));
    }

    @Test
    @DisplayName("rejects user without numeric GitHub Id")
    void test_02() {
        DefaultOAuth2User user = new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                Map.of("login", "karla-turing"),
                "login"
        );

        assertThatThrownBy(() -> GitHubIdentity.from(user))
        .isInstanceOf(IllegalArgumentException.class);
    }

}