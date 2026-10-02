package de.propra.game_of_advisors.topic.application;

import java.util.List;

public record TopicDetails(
        long id,
        String title,
        String description,
        String advisorName,
        long advisorId,
        List<String> fields,
        List<String> requiredCourses
) {
}
