package de.propra.game_of_advisors.matching;

import de.propra.game_of_advisors.matching.application.MatchingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatchingConfiguration {

    @Bean
    MatchingService matchingService() {
        return new MatchingService();
    }
}
