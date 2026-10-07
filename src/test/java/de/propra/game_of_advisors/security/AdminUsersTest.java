package de.propra.game_of_advisors.security;

import de.propra.game_of_advisors.user.GitHubUserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AdminUsersTest {

    @Test
    @DisplayName("recognizes configured administrator")
    void test_01() {
        AdminProperties properties = new AdminProperties(List.of(123456L, 9876554L));

        AdminUsers adminUsers = new AdminUsers(properties);

        assertThat(
                adminUsers.isAdmin(new GitHubUserId(123456L))
        ).isTrue();
    }

    @Test
    @DisplayName("rejects unconfigured user as administrator")
    void test_02() {
        AdminProperties properties = new AdminProperties(List.of(123456L));

        AdminUsers adminUsers = new AdminUsers(properties);

        assertThat(
                adminUsers.isAdmin(new GitHubUserId(999999L))
        ).isFalse();
    }
}