package de.propra.game_of_advisors.topic.web;

import de.propra.game_of_advisors.topic.application.TopicDetails;
import de.propra.game_of_advisors.topic.application.TopicQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class TopicController {
    private final TopicQueryService topicQueryService;

    public TopicController(TopicQueryService topicQueryService) {
        this.topicQueryService = topicQueryService;
    }

    @GetMapping("/topics")
    public String topics(
            @RequestParam(name = "field", required = false)
            List<String> fields,
            Model model
    ) {
        List<String> selectedFields =
                fields == null ? List.of() : fields;

        model.addAttribute(
                "topics",
                topicQueryService.findByFields(selectedFields)
        );

        model.addAttribute(
                "selectedFields",
                selectedFields
        );

        return "topics";
    }

    @GetMapping("topics/{id}")
    public String topicDetails(@PathVariable long id, Model model) {
        TopicDetails topic = topicQueryService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Thema nicht gefunden!"
                ));
        model.addAttribute("topic", topic);

        return "topic";
    }

}
