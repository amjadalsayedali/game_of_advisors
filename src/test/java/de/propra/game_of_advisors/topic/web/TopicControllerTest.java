package de.propra.game_of_advisors.topic.web;

import de.propra.game_of_advisors.SecurityConfig;
import de.propra.game_of_advisors.topic.application.TopicDetails;
import de.propra.game_of_advisors.topic.application.TopicQueryService;
import de.propra.game_of_advisors.topic.application.TopicSummary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TopicController.class)
@Import(SecurityConfig.class)
class TopicControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    TopicQueryService topicQueryService;

    @Test
    @DisplayName("shows Topics view")
    void test_01() throws Exception {
        given(topicQueryService.findByFields(List.of()))
                .willReturn(List.of(
                        new TopicSummary(
                                1L,
                                "Entwicklung eines Parsergenerators in Rust",
                                "Karla Turing",
                                List.of("Compilerbau", "Rust"),
                                List.of("Compilerbau")
                        )
                ));
        mockMvc.perform(get("/topics"))
                .andExpect(status().isOk())
                .andExpect(view().name("topics"))
                .andExpect(model().attribute("topics", hasSize(1)))
                .andExpect(model().attribute("selectedFields", List.of())
                );
    }

    @Test
    @DisplayName("shows Topic details")
    void test_02() throws Exception {
        TopicDetails topic = new TopicDetails(
                1L,
                "Entwicklung eines Parsergenerators in Rust",
                "Beschreibung",
                "Karla Turing",
                1L,
                List.of("Compilerbau", "Rust"),
                List.of("Compilerbau")
        );
        given(topicQueryService.findById(1L))
        .willReturn(Optional.of(topic));

        mockMvc.perform(get("/topics/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("topic"))
                .andExpect(model().attribute("topic", topic));
    }

    @Test
    @DisplayName("returns not found for unknown topics")
    void test_03() throws Exception {
        given(topicQueryService.findById(999L))
                .willReturn(Optional.empty());

        mockMvc.perform(get("/topics/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("filters topics by multiple fields")
    void test_04() throws Exception {
        given(topicQueryService.findByFields(List.of("Compilerbau", "Formale Methoden")))
                .willReturn(List.of(
                        new TopicSummary(
                                1L,
                                "Entwicklung eines Parsergenerators in Rust",
                                "Karla Turing",
                                List.of("Compilerbau", "Rust"),
                                List.of("Compilerbau")
                        ),
                        new TopicSummary(
                                2L,
                                "Verifikation verteilter Systeme",
                                "Ada Lovelace",
                                List.of("Formale Methoden"),
                                List.of()
                        )
                ));

        mockMvc.perform(
                get("/topics")
                    .param(
                "field",
                "Compilerbau",
                "Formale Methoden"
                    )
                )
                .andExpect(status().isOk())
                .andExpect(view().name("topics"))
                .andExpect(model().attribute(
                        "topics",
                        hasSize(2)
                ))
                .andExpect(model().attribute(
                        "selectedFields",
                        List.of(
                                "Compilerbau",
                                "Formale Methoden"
                )
                ));
    }


}