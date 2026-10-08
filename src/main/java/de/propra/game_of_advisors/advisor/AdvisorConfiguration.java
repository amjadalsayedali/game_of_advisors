package de.propra.game_of_advisors.advisor;

import de.propra.game_of_advisors.advisor.application.AdvisorQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdvisorConfiguration {

    @Bean
    AdvisorQueryService advisorQueryService() {
        return new AdvisorQueryService();
    }
}
