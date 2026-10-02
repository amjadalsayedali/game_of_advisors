package de.propra.game_of_advisors.topic.web;

import de.propra.game_of_advisors.topic.application.TopicQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TopicController {
    private final TopicQueryService topicQueryService;

    public TopicController(TopicQueryService topicQueryService) {
        this.topicQueryService = topicQueryService;
    }

    @GetMapping("/topics")
    public String topics(Model model) {
        model.addAttribute(
                "topics",
                topicQueryService.findAll()
        );
        return "topics";
    }

}
