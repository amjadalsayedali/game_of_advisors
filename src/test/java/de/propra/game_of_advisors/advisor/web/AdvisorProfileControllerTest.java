package de.propra.game_of_advisors.advisor.web;

import de.propra.game_of_advisors.SecurityConfig;
import de.propra.game_of_advisors.user.application.UserQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AdvisorProfileController.class)
@Import(SecurityConfig.class)
class AdvisorProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserQueryService userQueryService;

    @Test
    @WithMockUser(roles = "ADVISOR")
    @DisplayName("advisor can access own profile")
    void test_01() throws Exception {
        mockMvc.perform(get("/advisor/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("advisor-profile"));
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    @DisplayName("student can not access advisor profile")
    void test_02() throws Exception {
        mockMvc.perform(get("/advisor/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("admin without advisor role can not access advisor profile")
    void test_03() throws Exception {
        mockMvc.perform(get("/advisor/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("anonymus user cannot access advisor profile")
    void test_04() throws Exception {
        mockMvc.perform(get("/advisor/profile"))
                .andExpect(status().is3xxRedirection());
    }

}