package de.propra.game_of_advisors.topic;

import de.propra.game_of_advisors.topic.application.TopicQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {

    @Bean
    TopicQueryService topicQueryService() {
        return new TopicQueryService();
    }
}
