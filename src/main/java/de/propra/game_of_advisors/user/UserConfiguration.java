package de.propra.game_of_advisors.user;

import de.propra.game_of_advisors.user.application.UserManagementService;
import de.propra.game_of_advisors.user.application.UserQueryService;
import de.propra.game_of_advisors.user.application.UserRepository;
import de.propra.game_of_advisors.user.infrastructure.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    UserRepository userRepository() {
        return new InMemoryUserRepository();
    }
    
    @Bean
    UserManagementService userManagementService(UserRepository userRepository) {
        return new UserManagementService(userRepository);
    }

    @Bean
    UserQueryService userQueryService(UserRepository userRepository) {
        return new UserQueryService(userRepository);
    }
}
