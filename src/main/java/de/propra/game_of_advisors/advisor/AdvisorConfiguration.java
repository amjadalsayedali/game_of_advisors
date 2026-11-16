package de.propra.game_of_advisors.advisor;

import de.propra.game_of_advisors.advisor.application.AdvisorProfileApplicationService;
import de.propra.game_of_advisors.advisor.application.AdvisorProfileRepository;
import de.propra.game_of_advisors.advisor.application.AdvisorQueryService;
import de.propra.game_of_advisors.advisor.infrastructure.InMemoryAdvisorProfileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdvisorConfiguration {

    @Bean
    AdvisorQueryService advisorQueryService() {
        return new AdvisorQueryService();
    }

    @Bean
    AdvisorProfileRepository advisorProfileRepository() {
        return new InMemoryAdvisorProfileRepository();
    }

    @Bean
    AdvisorProfileApplicationService advisorProfileApplicationService(AdvisorProfileRepository repository) {
        return new AdvisorProfileApplicationService(repository);
    }
}
