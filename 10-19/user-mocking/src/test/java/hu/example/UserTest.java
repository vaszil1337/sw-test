package hu.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    @DisplayName("Alapértelmezett User kezdeti állapota")
    void defaultUserHasExpectedInitialState() {
        User user = new User();

        assertNotNull(user);
        assertNull(user.getUserName());
        assertNull(user.getPassword());
        assertEquals(0, user.getId());
        assertEquals(0, user.getLoginCount());
        assertFalse(user.isLoggedIn());
    }

    @Test
    @DisplayName("Bejelentkezéskor beáll a loggedIn és nő a loginCount")
    void loginSetsStateAndIncrementsLoginCount() {
        User user = new User();

        user.login();

        assertTrue(user.isLoggedIn());
        assertEquals(1, user.getLoginCount());
    }

    @Test
    @DisplayName("Kijelentkezéskor törlődik a loggedIn állapot")
    void logoutClearsLoggedInState() {
        User user = new User();
        user.login();

        user.logout();

        assertFalse(user.isLoggedIn());
        assertEquals(1, user.getLoginCount());
    }
}
