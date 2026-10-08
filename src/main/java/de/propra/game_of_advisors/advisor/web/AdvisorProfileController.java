package de.propra.game_of_advisors.advisor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdvisorProfileController {

    @GetMapping("/advisor/profile")
    public String editProfile() {
        return "advisor-profile";
    }
}
