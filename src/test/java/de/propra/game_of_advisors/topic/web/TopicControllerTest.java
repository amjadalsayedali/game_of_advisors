package de.propra.game_of_advisors.topic.web;

import de.propra.game_of_advisors.SecurityConfig;
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
        given(topicQueryService.findAll())
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
                .andExpect(model().attribute("topics", hasSize(1)));
    }

}