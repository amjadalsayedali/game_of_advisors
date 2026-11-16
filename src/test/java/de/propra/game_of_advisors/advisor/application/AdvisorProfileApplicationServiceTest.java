package de.propra.game_of_advisors.advisor.application;

import de.propra.game_of_advisors.advisor.domain.AdvisorProfile;
import de.propra.game_of_advisors.advisor.domain.FieldOfStudy;
import de.propra.game_of_advisors.user.domain.GitHubUserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AdvisorProfileApplicationServiceTest {

    @Test
    @DisplayName("creates Profile when none exists")
    void test_01() {
        TestAdvisorProfileRepository repository = new TestAdvisorProfileRepository();
        AdvisorProfileApplicationService service = new AdvisorProfileApplicationService(repository);

        GitHubUserId ownerId = new GitHubUserId(123L);

        service.updateProfile(
                ownerId,
                new UpdateAdvisorProfileCommand(
                        "Karla Turing",
                        "karla@example.org",
                        Set.of("Compilerbau")
                )
        );

        AdvisorProfile stored = repository
                .findByOwnerId(ownerId).
                orElseThrow();

        assertThat(stored.name()).isEqualTo("Karla Turing");
        assertThat(stored.fields()).containsExactly(new FieldOfStudy("Compilerbau"));
    }

    @Test
    @DisplayName("updates existing profile")
    void test_02() {
        GitHubUserId ownerId = new GitHubUserId(123L);

        AdvisorProfile existing =
                new AdvisorProfile(
                        ownerId,
                        "Alter Name",
                        "alt@example.org",
                        Set.of()
                );

        TestAdvisorProfileRepository repository =
                new TestAdvisorProfileRepository();

        repository.save(existing);

        AdvisorProfileApplicationService service =
                new AdvisorProfileApplicationService(repository);

        service.updateProfile(
                ownerId,
                new UpdateAdvisorProfileCommand(
                        "Karla Turing",
                        "karla@example.org",
                        Set.of(
                                "Compilerbau",
                                "Formale Methoden"
                        )
                )
        );

        AdvisorProfile stored =
                repository.findByOwnerId(ownerId)
                        .orElseThrow();

        assertThat(stored.name())
                .isEqualTo("Karla Turing");

        assertThat(stored.fields())
                .containsExactlyInAnyOrder(
                        new FieldOfStudy("Compilerbau"),
                        new FieldOfStudy("Formale Methoden")
                );
    }

    private static class TestAdvisorProfileRepository implements AdvisorProfileRepository {
        private final Map<GitHubUserId, AdvisorProfile> profiles = new HashMap<>();

        @Override
        public Optional<AdvisorProfile> findByOwnerId(GitHubUserId ownerId) {
            return Optional.ofNullable(profiles.get(ownerId));
        }

        @Override
        public void save(AdvisorProfile profile) {
            profiles.put(
                    profile.owenerId(),
                    profile
            );
        }
    }
}