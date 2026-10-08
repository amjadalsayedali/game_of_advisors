package de.propra.game_of_advisors.security;

import de.propra.game_of_advisors.user.application.UserQueryService;
import de.propra.game_of_advisors.user.domain.GitHubUserId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class GitHubAuthoritiesMapper implements GrantedAuthoritiesMapper {
    private static final GrantedAuthority STUDENT = new SimpleGrantedAuthority("ROLE_STUDENT");
    private static final GrantedAuthority ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
    private static final GrantedAuthority ADVISOR = new SimpleGrantedAuthority("ROLE_ADVISOR");
    private final AdminUsers adminUsers;
    private final UserQueryService userQueryService;

    public GitHubAuthoritiesMapper(AdminUsers adminUsers, UserQueryService userQueryService) {
        this.adminUsers = adminUsers;
        this.userQueryService = userQueryService;
    }

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(
            Collection<? extends GrantedAuthority> authorities
    ) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(authorities);

        mappedAuthorities.add(STUDENT);

        GitHubUserId gitHubUserId = authorities.stream()
                .filter(OAuth2UserAuthority.class::isInstance)
                .map(OAuth2UserAuthority.class::cast)
                .findFirst()
                .map(OAuth2UserAuthority::getAttributes)
                .map(GitHubIdentity::fromAttributes)
                .orElseThrow(() -> new IllegalStateException(
                        "Authentication contains no GitHub user"
                ));
        if(adminUsers.isAdmin(gitHubUserId)) {
            mappedAuthorities.add(ADMIN);
        }

        if (userQueryService.isAdvisor(gitHubUserId)) {
            mappedAuthorities.add(ADVISOR);
        }

        if (adminUsers.isAdmin(gitHubUserId)) {
            mappedAuthorities.add(ADMIN);
        }

        return Set.copyOf(mappedAuthorities);
    }
}
