package de.propra.game_of_advisors.advisor.application;

import java.util.Set;

public record AdvisorProfileData(
        String name,
        String contactInformation,
        Set<String> fields
) {
}
