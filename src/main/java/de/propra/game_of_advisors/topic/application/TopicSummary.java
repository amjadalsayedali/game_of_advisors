package de.propra.game_of_advisors.topic.application;

import java.util.List;

public record TopicSummary(long id, String title, String advisorName, List<String> fields, List<String> requiredCourses) {
}
