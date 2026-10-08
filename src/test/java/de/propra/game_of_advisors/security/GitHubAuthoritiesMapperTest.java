package de.propra.game_of_advisors.security;

import de.propra.game_of_advisors.user.application.UserQueryService;
import de.propra.game_of_advisors.user.application.UserRepository;
import de.propra.game_of_advisors.user.domain.GitHubUserId;
import de.propra.game_of_advisors.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class GitHubAuthoritiesMapperTest {

    @Test
    @DisplayName("assigns student role to authenticated GitHub user")
    void test_01() {
        AdminUsers adminUsers = new AdminUsers(
                new AdminProperties(List.of())
        );

        TestUserRepository repository = new TestUserRepository();

        UserQueryService userQueryService = new UserQueryService(repository);

        GitHubAuthoritiesMapper mapper = new GitHubAuthoritiesMapper(adminUsers, userQueryService);

        OAuth2UserAuthority githubUser = new OAuth2UserAuthority(
                        Map.of(
                                "id", 123456L,
                                "login", "student"
                        )
                );

        Collection<? extends GrantedAuthority> authorities = mapper.mapAuthorities(List.of(githubUser));

        assertThat(authorities)
                .extracting(GrantedAuthority::getAuthority)
                .contains("ROLE_STUDENT")
                .doesNotContain("ROLE_ADMIN");
    }

    @Test
    @DisplayName("assigns admin role to configured GitHub user")
    void test_02() {
        AdminUsers adminUsers = new AdminUsers(
                new AdminProperties(
                        List.of(123456L)
                )
        );

        TestUserRepository repository = new TestUserRepository();

        UserQueryService userQueryService = new UserQueryService(repository);

        GitHubAuthoritiesMapper mapper = new GitHubAuthoritiesMapper(adminUsers, userQueryService);

        OAuth2UserAuthority githubUser = new OAuth2UserAuthority(
                Map.of(
                        "id", 123456L,
                        "login", "administrator"
                )
        );

        Collection<? extends GrantedAuthority> authorities = mapper.mapAuthorities(List.of(githubUser));

        assertThat(authorities)
        .extracting(GrantedAuthority::getAuthority)
                .contains(
                        "ROLE_STUDENT",
                        "ROLE_ADMIN");
    }

    @Test
    @DisplayName("assigns advisor role to stored advisor")
    void test_03() {
        GitHubUserId userId = new GitHubUserId(123456L);

        User user = new User(userId);
        user.grantAdvisorRole();

        TestUserRepository repository = new TestUserRepository();

        repository.save(user);

        UserQueryService userQueryService = new UserQueryService(repository);

        GitHubAuthoritiesMapper mapper = new GitHubAuthoritiesMapper(
                new AdminUsers(
                        new AdminProperties(List.of())
                ),
                userQueryService
        );

        OAuth2UserAuthority githubUser = new OAuth2UserAuthority(
                Map.of(
                        "id", 123456L,
                        "login", "karla"
                )
        );

        Collection<? extends GrantedAuthority> authorities = mapper.mapAuthorities(List.of(githubUser));

        assertThat(authorities)
                .extracting(GrantedAuthority::getAuthority)
                .contains(
                        "ROLE_STUDENT",
                        "ROLE_ADVISOR"
                );
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