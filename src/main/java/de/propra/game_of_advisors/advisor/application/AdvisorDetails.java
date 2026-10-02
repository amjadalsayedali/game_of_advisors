package de.propra.game_of_advisors.advisor.application;

import java.util.List;

public record AdvisorDetails(long l, String name, String email, List<String> fields, List<InformationFileView> files, List<TopicSummary> topics) {
}
