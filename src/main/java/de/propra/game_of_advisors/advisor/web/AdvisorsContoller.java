package de.propra.game_of_advisors.advisor.web;

import de.propra.game_of_advisors.advisor.application.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class AdvisorsContoller {

    private final AdvisorQueryService advisorQueryService;

    public AdvisorsContoller(AdvisorQueryService advisorQueryService) {
        this.advisorQueryService = advisorQueryService;
    }

    @GetMapping("/advisors")
    public String advisors(
            @RequestParam(name = "field", required = false) List<String> fields,
            Model model) {
        List<String> selectedFields =
                fields == null ? List.of() : fields;

        model.addAttribute(
                "advisors",
                advisorQueryService.findByFields(selectedFields)
        );

        model.addAttribute(
                "selectedFields",
                selectedFields
        );

        return "advisors";
    }

    @GetMapping("/advisors/{id}")
    public String advisorProfile(@PathVariable long id, Model model) {
        AdvisorDetails advisor = advisorQueryService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bertreuungsperson nicht gefunden"
                ));
        model.addAttribute("advisor", advisor);
        return "advisor";
    }
}
