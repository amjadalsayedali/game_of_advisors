package de.propra.game_of_advisors.matching.application;

import java.util.List;

public record AdvisorCandidate(
        long id,
        String name,
        List<String> fields
) {
}
