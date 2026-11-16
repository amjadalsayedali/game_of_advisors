package de.propra.game_of_advisors.advisor.web;

import de.propra.game_of_advisors.advisor.application.AdvisorProfileApplicationService;
import de.propra.game_of_advisors.advisor.application.AdvisorProfileData;
import de.propra.game_of_advisors.advisor.application.UpdateAdvisorProfileCommand;
import de.propra.game_of_advisors.security.GitHubIdentity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
public class AdvisorProfileController {

    private final AdvisorProfileApplicationService profileService;

    public AdvisorProfileController(
            AdvisorProfileApplicationService profileService
    ) {
        this.profileService = profileService;
    }

    @GetMapping("/advisor/profile")
    public String editProfile(
            @AuthenticationPrincipal OAuth2User oauth2User,
            Model model
    ) {
        var ownerId = GitHubIdentity.from(oauth2User);

        AdvisorProfileData profile =
                profileService.findProfile(ownerId)
                        .orElse(new AdvisorProfileData(
                                "",
                                "",
                                Set.of()
                        ));

        model.addAttribute("profile", profile);

        return "advisor-profile";
    }

    @PostMapping("/advisor/profile")
    public String updateProfile(
            @AuthenticationPrincipal OAuth2User oauth2User,
            @RequestParam String name,
            @RequestParam String contactInformation,
            @RequestParam(
                    name = "field",
                    required = false
            ) List<String> fields,
            RedirectAttributes redirectAttributes
    ) {
        profileService.updateProfile(
                GitHubIdentity.from(oauth2User),
                new UpdateAdvisorProfileCommand(
                        name,
                        contactInformation,
                        Set.copyOf(
                                fields == null
                                        ? List.of()
                                        : fields
                        )
                )
        );

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Profil wurde gespeichert."
        );

        return "redirect:/advisor/profile";
    }
}