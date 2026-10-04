package de.propra.game_of_advisors.matching.application;

import java.util.List;

public record MatchingResult(
        List<TopicMatch> topics,
        List<AdvisorMatch> advisors
) {
}
