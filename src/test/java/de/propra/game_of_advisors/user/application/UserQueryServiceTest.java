package de.propra.game_of_advisors.user.application;

import de.propra.game_of_advisors.user.domain.GitHubUserId;
import de.propra.game_of_advisors.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserQueryServiceTest {

    @Test
    @DisplayName("recognizes advisor")
    void test_01() {
        GitHubUserId userId = new GitHubUserId(123456L);
        User user = new User(userId);
        user.grantAdvisorRole();

        TestUserRepository repository = new TestUserRepository();
        repository.save(user);

        UserQueryService service = new UserQueryService(repository);

        assertThat(service.isAdvisor(userId)).isTrue();
    }

    @Test
    @DisplayName("unknown user is not advisor")
    void test_02() {
        UserQueryService service = new UserQueryService(new TestUserRepository());

        assertThat(service.isAdvisor(new GitHubUserId(999999L))
        ).isFalse();
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