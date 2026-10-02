package de.propra.game_of_advisors.advisor.web;

import de.propra.game_of_advisors.SecurityConfig;
import de.propra.game_of_advisors.advisor.application.AdvisorDetails;
import de.propra.game_of_advisors.advisor.application.AdvisorQueryService;
import de.propra.game_of_advisors.advisor.application.AdvisorSummary;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdvisorsContoller.class)
@Import(SecurityConfig.class)
class AdvisorsContollerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AdvisorQueryService advisorQueryService;

    @Test
    @DisplayName("show advisor Overview")
    void test_01() throws Exception {
        given(advisorQueryService.findByFields(List.of()))
                .willReturn(List.of(
                        new AdvisorSummary(
                                1L,
                                "Karla Turing",
                                List.of("Compilerbau", "Formale Methoden")
                        ),
                        new AdvisorSummary(
                                2L,
                                "Ada Lovelace",
                                List.of("Java", "Software Engineering")
                        )
                )
        );
        mockMvc.perform(get("/advisors"))
                .andExpect(status().isOk())
                .andExpect(view().name("advisors"))
                .andExpect(model().attribute("advisors", hasSize(2)))
                .andExpect(model().attribute("selectedFields", List.of()));
    }

    @Test
    @DisplayName("show advisor profile")
    void test_02() throws Exception {
        AdvisorDetails karla = new AdvisorDetails(
                1L,
                "Karla Turing",
                "karla.turing@hhu.de",
                List.of("Compilerbau", "Formale Methoden"),
                List.of(),
                List.of()
        );

        given(advisorQueryService.findById(1L))
                .willReturn(Optional.of(karla));

        mockMvc.perform(get("/advisors/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("advisor"))
                .andExpect(model().attribute("advisor", karla));
    }

    @Test
    @DisplayName("returns not found for unknown advisor")
    void test_03() throws Exception {
        given(advisorQueryService.findById(999L))
                .willReturn(Optional.empty());

        mockMvc.perform(get("/advisors/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("filters advisors by multiple fields")
    void test_04() throws Exception {
        given(advisorQueryService.findByFields(
                List.of("Compilerbau", "Java")
        )).willReturn(List.of(
                        new AdvisorSummary(
                                1L,
                                "Karla Turing",
                                List.of("Compilerbau", "Formale Methoden")
                        ),
                new AdvisorSummary(
                        2L,
                        "Ada Lovelace",
                        List.of("Java", "Software Engineering")
                )
                ));
        mockMvc.perform(get("/advisors")
                .param("field", "Compilerbau", "Java")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("advisors"))
                .andExpect(model().attribute("advisors", hasSize(2)))
                .andExpect(model().attribute("selectedFields", List.of("Compilerbau", "Java")
                ));
    }
}