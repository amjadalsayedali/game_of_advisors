package de.propra.game_of_advisors.matching.web;

import de.propra.game_of_advisors.SecurityConfig;
import de.propra.game_of_advisors.advisor.application.AdvisorQueryService;
import de.propra.game_of_advisors.advisor.application.AdvisorSummary;
import de.propra.game_of_advisors.matching.application.AdvisorMatch;
import de.propra.game_of_advisors.matching.application.MatchingResult;
import de.propra.game_of_advisors.matching.application.MatchingService;
import de.propra.game_of_advisors.matching.application.TopicMatch;
import de.propra.game_of_advisors.topic.application.TopicQueryService;
import de.propra.game_of_advisors.topic.application.TopicSummary;
import de.propra.game_of_advisors.user.application.UserQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchingController.class)
@Import(SecurityConfig.class)
@WithMockUser
class MatchingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MatchingService matchingService;

    @MockitoBean
    TopicQueryService topicQueryService;

    @MockitoBean
    AdvisorQueryService advisorQueryService;

    @MockitoBean
    UserQueryService userQueryService;

    @Test
    @DisplayName("shows mathingform")
    void test_01() throws Exception {
        mockMvc.perform(get("/matching"))
                .andExpect(status().isOk())
                .andExpect(view().name("matching"));
    }

    @Test
    @DisplayName("shows matching results")
    void test_02() throws Exception {
        given(topicQueryService.findAll())
                .willReturn(List.of(
                        new TopicSummary(
                                1L,
                                "Parsergenerator",
                                "Karla Turing",
                                List.of("Compilerbau", "Rust"),
                                List.of("Compilerbau")
                        )
                ));

        given(advisorQueryService.findAll())
        .willReturn(List.of(
                new AdvisorSummary(
                        1L,
                        "Karla Turing",
                        List.of("Compilerbau", "Formale Methoden")
                )
        ));

        MatchingResult matchingResult = new MatchingResult(
                List.of(
                        new TopicMatch(
                                1L,
                                "Parsergenerator",
                                2
                        )
                ),
                List.of(
                        new AdvisorMatch(
                                1L,
                                "Karla Turing",
                                1
                        )
                )
        );

        given(matchingService.match(
                anyList(),
                anyList(),
                anyList(),
                anyList()
        )).willReturn(matchingResult);

        mockMvc.perform(get("/matching/results")
                .param(
                        "interest",
                        "Compilerbau",
                        "Rust"
                )
                .param(
                        "course",
                        "Compilerbau"
                )
        )
                .andExpect(status().isOk())
                .andExpect(view().name("matching-results"))
                .andExpect(model().attribute(
                        "result",
                        matchingResult
                ))
        .andExpect(model().attribute(
                "interests",
                List.of("Compilerbau", "Rust")
        ))
                .andExpect(model().attribute(
                        "passedCourses",
                        List.of("Compilerbau")
                ));
    }
}