package de.propra.game_of_advisors.user.application;

import de.propra.game_of_advisors.user.domain.GitHubUserId;
import de.propra.game_of_advisors.user.domain.User;

public final class UserManagementService {

    private final UserRepository userRepository;

    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(GitHubUserId gitHubUserId) {
        if(userRepository.findById(gitHubUserId).isPresent()) {
            return;
        }

        User user = new User(gitHubUserId);
        userRepository.save(user);
    }

    public void grantAdvisorRole(GitHubUserId gitHubUserId) {
        User user = userRepository.findById(gitHubUserId)
                .orElseThrow(() -> new UserNotFoundException(gitHubUserId)
                );

        user.grantAdvisorRole();

        userRepository.save(user);
    }
}
