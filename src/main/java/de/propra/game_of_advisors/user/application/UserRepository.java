package de.propra.game_of_advisors.user.application;

import de.propra.game_of_advisors.user.domain.GitHubUserId;
import de.propra.game_of_advisors.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(GitHubUserId gitHubUserId);

    void save(User user);
}
