package de.propra.game_of_advisors.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("new user hs student role")
    void test_01() {
        User user = new User(
                new GitHubUserId(123456L)
        );

        assertThat(user.hasRole(UserRole.STUDENT))
                .isTrue();

        assertThat(user.hasRole(UserRole.ADVISOR))
                .isFalse();
    }

    @Test
    @DisplayName("grants advisor role")
    void test_02() {
        User user = new User(
                new GitHubUserId(123456L)
        );

        user.grantAdvisorRole();

        assertThat(user.hasRole(UserRole.ADVISOR))
                .isTrue();
    }

}