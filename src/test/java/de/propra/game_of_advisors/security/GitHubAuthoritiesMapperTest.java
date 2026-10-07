package de.propra.game_of_advisors.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GitHubAuthoritiesMapperTest {

    @Test
    @DisplayName("assigns student role to authenticated GitHub user")
    void test_01() {
        AdminUsers adminUsers = new AdminUsers(
                new AdminProperties(List.of())
        );

        GitHubAuthoritiesMapper mapper = new GitHubAuthoritiesMapper(adminUsers);

        OAuth2UserAuthority githubUser = new OAuth2UserAuthority(
                        Map.of(
                                "id", 123456L,
                                "login", "student"
                        )
                );

                Collection<? extends GrantedAuthority> authorities = mapper.mapAuthorities(List.of(githubUser));

                assertThat(authorities)
                        .extracting(GrantedAuthority::getAuthority)
                        .contains("ROLE_STUDENT")
                        .doesNotContain("ROLE_ADMIN");
    }

    @Test
    @DisplayName("assigns admin role to configured GitHub user")
    void test_02() {
        AdminUsers adminUsers = new AdminUsers(
                new AdminProperties(
                        List.of(123456L)
                )
        );

        GitHubAuthoritiesMapper mapper = new GitHubAuthoritiesMapper(adminUsers);

        OAuth2UserAuthority githubUser = new OAuth2UserAuthority(
                Map.of(
                        "id", 123456L,
                        "login", "administrator"
                )
        );

        Collection<? extends GrantedAuthority> authorities = mapper.mapAuthorities(List.of(githubUser));

        assertThat(authorities)
        .extracting(GrantedAuthority::getAuthority)
                .contains(
                        "ROLE_STUDENT",
                        "ROLE_ADMIN");
    }

}