package de.propra.game_of_advisors.advisor.application;

import de.propra.game_of_advisors.advisor.domain.AdvisorProfile;
import de.propra.game_of_advisors.advisor.domain.FieldOfStudy;
import de.propra.game_of_advisors.user.domain.GitHubUserId;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class AdvisorProfileApplicationService {
    private final AdvisorProfileRepository repository;

    public AdvisorProfileApplicationService(AdvisorProfileRepository repository) {
        this.repository = repository;
    }

    public void updateProfile(
            GitHubUserId ownerId,
            UpdateAdvisorProfileCommand command
    ) {
        Set<FieldOfStudy> fields = command.fields().stream()
                .map(FieldOfStudy::new)
                .collect(Collectors.toSet());

        var existingProfile =
                repository.findByOwnerId(ownerId);

        AdvisorProfile profile;

        if (existingProfile.isPresent()) {
            profile = existingProfile.get();

            profile.changeName(command.name());
            profile.changeContactInformation(
                    command.contactInformation()
            );
            profile.replaceFields(fields);
        } else {
            profile = new AdvisorProfile(
                    ownerId,
                    command.name(),
                    command.contactInformation(),
                    fields
            );
        }

        repository.save(profile);
    }

    public Optional<AdvisorProfileData> findProfile(GitHubUserId ownerId) {
        return repository.findByOwnerId(ownerId)
                .map(profile -> new AdvisorProfileData(
                        profile.name(),
                        profile.contactInfomration(),
                        profile.fields().stream()
                                .map(FieldOfStudy::name)
                                .collect(Collectors.toSet())
                ));
    }
}
