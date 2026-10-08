package de.propra.game_of_advisors.security;

import de.propra.game_of_advisors.user.application.UserManagementService;
import de.propra.game_of_advisors.user.domain.GitHubUserId;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class GitHubLoginListener {

    private final UserManagementService userManagementService;

    public GitHubLoginListener(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();

        if(!(principal instanceof OAuth2User oAuth2User)) {
            return;
        }

        GitHubUserId githubUserId = GitHubIdentity.from(oAuth2User);

        userManagementService.registerUser(githubUserId);
    }
}
