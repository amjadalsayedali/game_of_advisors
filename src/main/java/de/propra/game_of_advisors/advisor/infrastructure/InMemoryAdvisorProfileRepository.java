package de.propra.game_of_advisors.advisor.infrastructure;

import de.propra.game_of_advisors.advisor.application.AdvisorProfileRepository;
import de.propra.game_of_advisors.advisor.domain.AdvisorProfile;
import de.propra.game_of_advisors.user.domain.GitHubUserId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class InMemoryAdvisorProfileRepository implements AdvisorProfileRepository {

    private final Map<GitHubUserId, AdvisorProfile> profiles = new HashMap<>();

    @Override
    public Optional<AdvisorProfile> findByOwnerId(GitHubUserId ownerId) {
        return Optional.ofNullable(profiles.get(ownerId));
    }

    @Override
    public void save(AdvisorProfile profile) {
        profiles.put(
                profile.owenerId(),
                profile
        );
    }
}
