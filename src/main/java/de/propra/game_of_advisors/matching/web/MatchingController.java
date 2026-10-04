package de.propra.game_of_advisors.matching.web;

import de.propra.game_of_advisors.advisor.application.AdvisorQueryService;
import de.propra.game_of_advisors.matching.application.AdvisorCandidate;
import de.propra.game_of_advisors.matching.application.MatchingResult;
import de.propra.game_of_advisors.matching.application.MatchingService;
import de.propra.game_of_advisors.matching.application.TopicCandidate;
import de.propra.game_of_advisors.topic.application.TopicQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MatchingController {

    private final MatchingService matchingService;
    private final TopicQueryService topicQueryService;
    private final AdvisorQueryService advisorQueryService;

    public MatchingController(
            MatchingService matchingService,
            TopicQueryService topicQueryService,
            AdvisorQueryService advisorQueryService
    ) {
        this.matchingService = matchingService;
        this.topicQueryService = topicQueryService;
        this.advisorQueryService = advisorQueryService;
    }

    @GetMapping("/matching")
    public String matching() {
        return "matching";
    }

    @GetMapping("/matching/results")
    public String matchingResults(
            @RequestParam(name = "interest", required = false)
            List<String> interests,

            @RequestParam(name = "course", required = false)
            List<String> courses,

            Model model
    ) {
        List<String> selectedInterests =
                interests == null ? List.of() : interests;

        List<String> passedCourses =
                courses == null ? List.of() : courses;

        List<TopicCandidate> topics = topicQueryService
                .findAll()
                .stream()
                .map(topic -> new TopicCandidate(
                        topic.id(),
                        topic.title(),
                        topic.fields(),
                        topic.requiredCourses()
                ))
                .toList();

        List<AdvisorCandidate> advisors = advisorQueryService
                .findAll()
                .stream()
                .map(advisor -> new AdvisorCandidate(
                        advisor.id(),
                        advisor.name(),
                        advisor.fields()
                ))
                .toList();

        MatchingResult result = matchingService.match(
                selectedInterests,
                passedCourses,
                topics,
                advisors
        );

        model.addAttribute("result", result);
        model.addAttribute("interests", selectedInterests);
        model.addAttribute("passedCourses", passedCourses);

        return "matching-results";
    }
}
