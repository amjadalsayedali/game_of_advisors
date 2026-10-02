package de.propra.game_of_advisors.advisor.application;

import java.util.List;

public record TopicSummary(long id, String title, List<String> fields, List<String> requiredCourses) {
}
