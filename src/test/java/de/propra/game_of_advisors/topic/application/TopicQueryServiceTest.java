package de.propra.game_of_advisors.topic.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TopicQueryServiceTest {
    private final TopicQueryService service = new TopicQueryService();

    @Test
    @DisplayName("find topics by selected fields")
    void test_01() {
        List<TopicSummary> result =
                service.findByFields(
                        List.of("Compilerbau", "Formale Methoden")
                );

        assertThat(result)
                .extracting(TopicSummary::title)
                .containsExactly(
                        "Entwicklung eines Parsergenerators in Rust",
                        "Verifikation verteilter Systeme"
                );
    }

    @Test
    @DisplayName("returns empty list when no topic matches selected field")
    void test_02() {
        List<TopicSummary> result = service.findByFields(List.of("Quantencomputing"));

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("return all topics when no field is selected")
    void test_03() {
        List<TopicSummary> result = service.findByFields(List.of());

        assertThat(result)
                .extracting(TopicSummary::title)
                .containsExactlyInAnyOrder(
                        "Entwicklung eines Parsergenerators in Rust",
                        "Verifikation verteilter Systeme"
                );
    }

}