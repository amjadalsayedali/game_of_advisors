package de.propra.game_of_advisors.admin.web;

import de.propra.game_of_advisors.SecurityConfig;
import de.propra.game_of_advisors.user.application.UserManagementService;
import de.propra.game_of_advisors.user.domain.GitHubUserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AdminController.class)
@Import(SecurityConfig.class)
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserManagementService userManagementService;

    @Test
    @DisplayName("redirects anonymous user to login")
    void test_01() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    @DisplayName("denies access to student")
    void test_02() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("allows access to administrator")
    void test_03() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("administrator can grant advisor role")
    void test_04() throws Exception {
        mockMvc.perform(
                post("/admin/advisors")
                .param(
                        "githubUserId",
                        "123456"
                )
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

        verify(userManagementService)
                .grantAdvisorRole(
                        new GitHubUserId(123456L)
                );
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    @DisplayName("student can not grant advisor role")
    void test_05() throws Exception {
        mockMvc.perform(
                post("/admin/advisors")
                .param(
                        "githubUserId",
                        "123456"
                )
                        .with(csrf())
        )
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("anonymous user can not grant advisor role")
    void test_06() throws Exception {
        mockMvc.perform(
                post("/admin/advisors")
                .param(
                        "githubUserId",
                        "123456"
                )
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection());
    }
}