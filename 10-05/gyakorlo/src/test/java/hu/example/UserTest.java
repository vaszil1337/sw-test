package hu.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    @Test
    void defaultUserHasExpectedInitialState() {
        User user = new User();

        assertTrue(user != null);
        assertNull(user.getUserName());
        assertNull(user.getPassword());
        assertEquals(0, user.getId());
        assertEquals(0, user.getLoginCount());
        assertFalse(user.isLoggedIn());
    }

    @Test
    void loginSetsStateAndIncrementsLoginCount() {
        User user = new User();

        user.login();

        assertTrue(user.isLoggedIn());
        assertEquals(1, user.getLoginCount());
    }

    @Test
    void logoutClearsLoggedInState() {
        User user = new User();
        user.login();

        user.logout();

        assertFalse(user.isLoggedIn());
        assertEquals(1, user.getLoginCount());
    }

    @Test
    void passwordUpdateIsNotImplementedYet() {
        User user = new User();

        assertThrows(UnsupportedOperationException.class,
                () -> user.updatePwd("new-password", true));
    }
}
