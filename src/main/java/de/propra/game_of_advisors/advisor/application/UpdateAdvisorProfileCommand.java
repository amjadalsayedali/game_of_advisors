package de.propra.game_of_advisors.advisor.application;

import java.util.Set;

public record UpdateAdvisorProfileCommand(
        String name,
        String contactInformation,
        Set<String> fields
) {
}
