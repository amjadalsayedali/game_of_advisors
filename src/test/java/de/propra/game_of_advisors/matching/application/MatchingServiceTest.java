package de.propra.game_of_advisors.matching.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MatchingServiceTest {
    private final MatchingService service = new MatchingService();

    @Test
    @DisplayName("sorts topics by matching interests")
    void test_01() {
        TopicCandidate parserGenerator = new TopicCandidate(
                1L,
                "Parsergenerator",
                List.of("Compilerbau", "Rust"),
                List.of("Compilerbau")
        );

        TopicCandidate javaTopic = new TopicCandidate(
                2L,
                "Java Webanwednung",
                List.of("Java"),
                List.of()
        );

        MatchingResult result = service.match(
                List.of("Compilerbau", "Rust", "Java"),
                List.of("Compilerbau"),
                List.of(javaTopic, parserGenerator),
                List.of()
        );

        assertThat(result.topics())
                .extracting(TopicMatch::title)
                .containsExactly(
                        "Parsergenerator",
                        "Java Webanwednung"
                );
    }

    @Test
    @DisplayName("excludes topic when requied course was not passed")
    void test_02() {
        TopicCandidate topic = new TopicCandidate(
                1L,
                "Parsergenerator",
                List.of("Compilerbau", "Rust"),
                List.of("Compilerbau")
        );

        MatchingResult result = service.match(
                List.of("Compilerbau", "Rust"),
                List.of(),
                List.of(topic),
                List.of()
        );

        assertThat(result.topics()).isEmpty();
    }

    @Test
    @DisplayName("sorts advisors by matching interests")
    void test_03() {
        AdvisorCandidate karla = new AdvisorCandidate(
                1L,
                "Karla Turing",
                List.of("Compilerbau", "Formale Methoden")
        );

        AdvisorCandidate ada = new AdvisorCandidate(
                2L,
                "Ada Lovelace",
                List.of("Java")
        );

        MatchingResult result = service.match(
                List.of("Compilerbau", "Formale Methoden", "Java"),
                List.of(),
                List.of(),
                List.of(ada, karla)
        );

        assertThat(result.advisors())
                .extracting(AdvisorMatch::name)
                .containsExactly(
                        "Karla Turing",
                        "Ada Lovelace"
                );
    }

    @Test
    @DisplayName("excludes advisors without matching interests")
    void test_04() {
        AdvisorCandidate ada = new AdvisorCandidate(
                1L,
                "Ada Lovelace",
                List.of("Java")
        );

        MatchingResult result = service.match(
                List.of("Compilerbau"),
                List.of(),
                List.of(),
                List.of(ada)
        );

        assertThat(result.advisors()).isEmpty();
    }
}