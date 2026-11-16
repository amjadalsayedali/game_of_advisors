package de.propra.game_of_advisors.advisor.web;

import de.propra.game_of_advisors.SecurityConfig;
import de.propra.game_of_advisors.advisor.application.AdvisorProfileApplicationService;
import de.propra.game_of_advisors.advisor.application.UpdateAdvisorProfileCommand;
import de.propra.game_of_advisors.user.application.UserQueryService;
import de.propra.game_of_advisors.user.domain.GitHubUserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdvisorProfileController.class)
@Import(SecurityConfig.class)
class AdvisorProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserQueryService userQueryService;

    @MockitoBean
    AdvisorProfileApplicationService profileService;

    @Test
    //@WithMockUser(roles = "ADVISOR")
    @DisplayName("advisor can access own profile")
    void test_01() throws Exception {
        when(profileService.findProfile(
                new GitHubUserId(123456L)
        )).thenReturn(Optional.empty());

        mockMvc.perform(get("/advisor/profile")
                        .with(oauth2Login()
                                .attributes(attributes -> attributes.put("id", 123456L))
                                .authorities(new SimpleGrantedAuthority("ROLE_ADVISOR"))
                        )
                )
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

    @Test
    @DisplayName("advisor can update own profile")
    void test_05() throws Exception {
        mockMvc.perform(
                post("/advisor/profile")
                        .with(csrf())
                        .with(oauth2Login()
                                .attributes(attributes -> attributes.put("id", 123456L))
                                .authorities(new SimpleGrantedAuthority("ROLE_ADVISOR"))
        )
                        .param("name", "Karla Turing")
                        .param("contactInformation", "karla@example.org")
                        .param("field", "Compilerbau", "Rust")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/advisor/profile")
                );

        verify(profileService).updateProfile(
                new GitHubUserId(123456L),
                new UpdateAdvisorProfileCommand(
                        "Karla Turing",
                        "karla@example.org",
                        Set.of(
                                "Compilerbau",
                                "Rust"
                        )
                )
        );
    }

}