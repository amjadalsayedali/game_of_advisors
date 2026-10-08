package de.propra.game_of_advisors.user.infrastructure;

import de.propra.game_of_advisors.user.application.UserRepository;
import de.propra.game_of_advisors.user.domain.GitHubUserId;
import de.propra.game_of_advisors.user.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class InMemoryUserRepository implements UserRepository {

    private final Map<GitHubUserId, User> users = new HashMap<>();

    @Override
    public Optional<User> findById(GitHubUserId githubUserId) {
        return Optional.ofNullable(users.get(githubUserId));
    }

    @Override
    public void save(User user) {
        users.put(user.getGitHubUserId(), user);
    }
}
