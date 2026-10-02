package de.propra.game_of_advisors.advisor.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AdvisorQueryServiceTest {
    private final AdvisorQueryService service = new AdvisorQueryService();

    @Test
    @DisplayName("finds advisors by selected fields")
    void test_01() {
        List<AdvisorSummary> result = service.findByFields(
                List.of("Compilerbau", "Java")
        );
        assertThat(result)
                .extracting(AdvisorSummary::name)
                .containsExactlyInAnyOrder(
                        "Karla Turing",
                        "Ada Lovelace",
                        "Grace Hopper"
                );
    }

    @Test
    @DisplayName("returns empty list when no advisor matches sekected fields")
    void test_02() {
        List<AdvisorSummary> result = service.findByFields(
                List.of("Quantencomputing")
        );
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("returns all advisors when no field is selected")
    void test_03() {
        List<AdvisorSummary> result = service.findByFields(List.of());
        assertThat(result)
                .extracting(AdvisorSummary::name)
                .containsExactlyInAnyOrder(
                        "Karla Turing",
                        "Ada Lovelace",
                        "Grace Hopper"
                );
    }
}