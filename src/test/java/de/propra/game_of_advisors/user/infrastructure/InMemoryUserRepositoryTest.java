package de.propra.game_of_advisors.user.infrastructure;

import de.propra.game_of_advisors.user.domain.GitHubUserId;
import de.propra.game_of_advisors.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryUserRepositoryTest {

    private final InMemoryUserRepository repository = new InMemoryUserRepository();

    @Test
    @DisplayName("saves and finds user by gitub id")
    void test_01() {
        GitHubUserId userId = new GitHubUserId(123456L);

        User user = new User(userId);

        repository.save(user);

        assertThat(repository.findById(userId))
                .contains(user);
    }

    @Test
    @DisplayName("returns empty for unknown github id")
    void test_02() {
        assertThat(
                repository.findById(
                        new GitHubUserId(999999L)
                )
        )
        .isEmpty();
    }

}