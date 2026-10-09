package de.propra.game_of_advisors.advisor.domain;

import de.propra.game_of_advisors.user.domain.GitHubUserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AdvisorProfileTest {

    @Test
    @DisplayName("changes profile information")
    void test_01() {
        AdvisorProfile profile = new AdvisorProfile(
                new GitHubUserId(123456L),
                "Karla Turing",
                "Karla@example.org",
                Set.of(
                        new FieldOfStudy("Compilerbau")
                )
        );

        profile.changeName("Karla A. Turing");
        profile.changeContactInformation("karla.turing@example.org");
        profile.replaceFields(Set.of(
                new FieldOfStudy("Compilerbau"),
                new FieldOfStudy("Formale Methoden")
        ));

        assertThat(profile.name()).isEqualTo("Karla A. Turing");
        assertThat(profile.contactInfomration()).isEqualTo("karla.turing@example.org");
        assertThat(profile.fields())
                .containsExactlyInAnyOrder(
                        new FieldOfStudy("Compilerbau"),
                        new FieldOfStudy("Formale Methoden")
                );
    }

    @Test
    @DisplayName("rejects blank name")
    void test_02() {
        assertThatThrownBy(() ->
                new AdvisorProfile(
                        new GitHubUserId(123456L),
                        " ",
                        "karla@example.org",
                        Set.of()
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("rejects blank field of study")
    void test_03() {
        assertThatThrownBy(() ->
                new FieldOfStudy(" ")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}