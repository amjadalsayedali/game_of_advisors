package de.propra.game_of_advisors.matching.application;

import java.util.List;

public record TopicCandidate(
        long id,
        String title,
        List<String> fields,
        List<String> requiredCourses
) {
}
