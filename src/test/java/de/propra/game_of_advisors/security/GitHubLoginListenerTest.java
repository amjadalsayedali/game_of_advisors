package de.propra.game_of_advisors.security;

import de.propra.game_of_advisors.user.application.UserManagementService;
import de.propra.game_of_advisors.user.application.UserRepository;
import de.propra.game_of_advisors.user.domain.GitHubUserId;
import de.propra.game_of_advisors.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GitHubLoginListenerTest {

    @Test
    @DisplayName("registers user after success ful github login")
    void test_01() {
        TestUserRepository repository = new TestUserRepository();

        UserManagementService userManagementService = new UserManagementService(repository);

        GitHubLoginListener listener = new GitHubLoginListener(userManagementService);

        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_STUDENT"
                        )
                ),
                Map.of(
                        "id", 123456L,
                        "login", "karla-turing"
                ),
                "id"
        );

        OAuth2AuthenticationToken authentication =  new OAuth2AuthenticationToken(
                oAuth2User,
                oAuth2User.getAuthorities(),
                "github"
        );

        listener.onAuthenticationSuccess(
                new AuthenticationSuccessEvent(authentication)
        );

        assertThat(
                repository.findById(
                        new GitHubUserId(123456L)
                )
        ).isPresent();
    }

    private class TestUserRepository implements UserRepository {
        private final Map<GitHubUserId, User> users = new HashMap<>();

        @Override
        public Optional<User> findById(GitHubUserId githubUserid) {
            return Optional.ofNullable(users.get(githubUserid));
        }

        @Override
        public void save(User user) {
            users.put(
                    user.githubUserId(),
                    user
            );
        }
    }
}