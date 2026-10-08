package de.propra.game_of_advisors.admin.web;

import de.propra.game_of_advisors.user.application.UserManagementService;
import de.propra.game_of_advisors.user.domain.GitHubUserId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    private final UserManagementService userManagementService;

    public AdminController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @PostMapping("/admin/advisors")
    public String grantAdvisorRole(
            @RequestParam long githubUserId,
            RedirectAttributes redirectAttributes
    ) {
        userManagementService.grantAdvisorRole(
                new GitHubUserId(githubUserId)
        );

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Betreuendenrolle wurde vergeben."
        );

        return "redirect:/admin";
    }
}
