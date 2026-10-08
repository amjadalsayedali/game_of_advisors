package de.propra.game_of_advisors;

import de.propra.game_of_advisors.security.AdminProperties;
import de.propra.game_of_advisors.security.AdminUsers;
import de.propra.game_of_advisors.security.GitHubAuthoritiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableConfigurationProperties(AdminProperties.class)
public class SecurityConfig {

    @Bean
    SecurityFilterChain SecurityFilterChain(
            HttpSecurity http,
            GitHubAuthoritiesMapper authoritiesMapper
    ) throws Exception {

        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/",
                                "/login/**",
                                "/oauth2/**")
                        .permitAll()

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(
                                        authoritiesMapper
                                )
                        )
                )
                .build();
    }

    @Bean
    AdminUsers adminUsers(AdminProperties properties) {
        return new AdminUsers(properties);
    }

    @Bean
    GitHubAuthoritiesMapper gitHubAuthoritiesMapper(AdminUsers adminUsers) {
        return new GitHubAuthoritiesMapper(adminUsers);
    }

    @Bean
    AuthenticationEventPublisher authenticationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher
    ) {
        return new DefaultAuthenticationEventPublisher(
                applicationEventPublisher
        );
    }
}
