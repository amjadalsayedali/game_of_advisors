package de.propra.game_of_advisors.matching.application;


import java.util.Comparator;
import java.util.List;

public class MatchingService {

    public MatchingResult match(
            List<String> interests,
            List<String> passedCourses,
            List<TopicCandidate> topics,
            List<AdvisorCandidate> advisors
    ) {
        List<TopicMatch> topicMatches = topics.stream()
                .filter(topic -> passedCourses.containsAll(topic.requiredCourses())
                )
                .map(topic -> new TopicMatch(
                        topic.id(),
                        topic.title(),
                        calculateScore(interests, topic.fields())
                ))
                .filter(match -> match.score() > 0)
                .sorted(Comparator.comparingInt(TopicMatch::score).reversed())
                .toList();

        List<AdvisorMatch> advisorMatches = advisors.stream()
                .map(advisor -> new AdvisorMatch(
                        advisor.id(),
                        advisor.name(),
                        calculateScore(interests, advisor.fields())
                ))
                .filter(match -> match.score() > 0)
                .sorted(Comparator.comparingInt(AdvisorMatch::score).reversed())
                .toList();

        return new MatchingResult(
                topicMatches,
                advisorMatches
        );
    }

    private int calculateScore(
            List<String> interests,
            List<String> fields
    ) {
        return (int) fields.stream()
                .filter(interests::contains)
                .count();
    }
}
