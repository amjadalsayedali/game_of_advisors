package de.propra.game_of_advisors.security;

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
    private final AdminUsers adminUsers;

    public GitHubAuthoritiesMapper(AdminUsers adminUsers) {
        this.adminUsers = adminUsers;
    }

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(
            Collection<? extends GrantedAuthority> authorities
    ) {
        Set<GrantedAuthority> mapperAuthorities = new HashSet<>(authorities);

        mapperAuthorities.add(STUDENT);

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
            mapperAuthorities.add(ADMIN);
        }

        return Set.copyOf(mapperAuthorities);
    }
}
