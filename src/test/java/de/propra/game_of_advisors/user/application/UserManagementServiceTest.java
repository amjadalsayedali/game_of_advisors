package de.propra.game_of_advisors.user.application;

import de.propra.game_of_advisors.user.domain.GitHubUserId;
import de.propra.game_of_advisors.user.domain.User;
import de.propra.game_of_advisors.user.domain.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserManagementServiceTest {

    @Test
    @DisplayName("grants advisor role to existing user")
    void test_01() {
        GitHubUserId userId = new GitHubUserId(123456L);
        User user = new User(userId);

        TestUserRepository repository = new TestUserRepository();

        repository.save(user);

        UserManagementService service = new UserManagementService(repository);

        service.grantAdvisorRole(userId);

        User savedUser = repository
                .findById(userId)
                .orElseThrow();

        assertThat(savedUser.hasRole(UserRole.ADVISOR))
                .isTrue();
    }

    @Test
    @DisplayName("rejects unknown user")
    void test_02() {
        GitHubUserId userId = new GitHubUserId(999999L);

        UserManagementService service = new UserManagementService(new TestUserRepository());

        assertThatThrownBy(
                () -> service.grantAdvisorRole(userId)
        )
        .isInstanceOf(UserNotFoundException.class);
    }

    private static class TestUserRepository implements UserRepository {
        private final Map<GitHubUserId, User> users = new HashMap<>();

        @Override
        public Optional<User> findById(GitHubUserId githubUserId) {
            return Optional.ofNullable(users.get(githubUserId));
        }

        @Override
        public void save(User user) {
            users.put(
                    user.getGitHubUserId(),
                    user
            );
        }
    }
}