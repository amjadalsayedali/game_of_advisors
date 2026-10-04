package de.propra.game_of_advisors.matching.web;

import de.propra.game_of_advisors.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MatchingController.class)
@Import(SecurityConfig.class)
class MatchingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("shows mathingform")
    void test_01() throws Exception {
        mockMvc.perform(get("/matching"))
                .andExpect(status().isOk())
                .andExpect(view().name("matching"));
    }
}