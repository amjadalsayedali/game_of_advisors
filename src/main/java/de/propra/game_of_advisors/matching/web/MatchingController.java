package de.propra.game_of_advisors.matching.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MatchingController {

    @GetMapping("/matching")
    public String matching() {
        return "matching";
    }
}
