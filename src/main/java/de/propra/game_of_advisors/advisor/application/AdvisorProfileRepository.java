package de.propra.game_of_advisors.advisor.application;

import de.propra.game_of_advisors.advisor.domain.AdvisorProfile;
import de.propra.game_of_advisors.user.domain.GitHubUserId;

import java.util.Optional;

public interface AdvisorProfileRepository {

    Optional<AdvisorProfile> findByOwnerId(GitHubUserId ownerId);
    void save(AdvisorProfile profile);
}
