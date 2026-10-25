package hu.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMockTest {

    @Mock
    private UserDatabase userDatabase;

    @Mock
    private User mockUserField;

    @InjectMocks
    private User user;

    @Test
    @DisplayName("1. User mock objektum létrehozása és ellenőrzése a megadott adatokkal")
    void testMockUserReturnsExpectedData() {
        // Mock objektum létrehozása Mockito.mock metódussal
        User mockUser = mock(User.class);

        // Viselkedések mockolása when() és thenReturn() metódusokkal
        when(mockUser.getUserName()).thenReturn("TesztElek");
        when(mockUser.getPassword()).thenReturn("TOPSECRET");
        when(mockUser.isLoggedIn()).thenReturn(true);
        when(mockUser.getId()).thenReturn(123);

        // Ellenőrizzük a mockolt értékeket
        assertEquals("TesztElek", mockUser.getUserName());
        assertEquals("TOPSECRET", mockUser.getPassword());
        assertTrue(mockUser.isLoggedIn());
        assertEquals(123, mockUser.getId());

        // A verify() függvény használatával ellenőrizzük az egyes metódusok meghívását
        verify(mockUser, times(1)).getUserName();
        verify(mockUser, times(1)).getPassword();
        verify(mockUser, times(1)).isLoggedIn();
        verify(mockUser, times(1)).getId();
    }

    @Test
    @DisplayName("1b. User mock objektum ellenőrzése @Mock mezőannotációval")
    void testMockUserWithAnnotation() {
        // Viselkedések mockolása a @Mock mezőn
        when(mockUserField.getUserName()).thenReturn("TesztElek");
        when(mockUserField.getPassword()).thenReturn("TOPSECRET");
        when(mockUserField.isLoggedIn()).thenReturn(true);
        when(mockUserField.getId()).thenReturn(123);

        assertEquals("TesztElek", mockUserField.getUserName());
        assertEquals("TOPSECRET", mockUserField.getPassword());
        assertTrue(mockUserField.isLoggedIn());
        assertEquals(123, mockUserField.getId());

        verify(mockUserField).getUserName();
        verify(mockUserField).getPassword();
        verify(mockUserField).isLoggedIn();
        verify(mockUserField).getId();
    }

    @Test
    @DisplayName("2. Többszöri metódushívás ellenőrzése a verify() és times() használatával")
    void testMockUserMethodCallCounts() {
        User mockUser = mock(User.class);
        when(mockUser.getUserName()).thenReturn("TesztElek");

        // Kétszer hívjuk meg
        mockUser.getUserName();
        mockUser.getUserName();

        // Ellenőrizzük, hogy pontosan kétszer lett meghívva
        verify(mockUser, times(2)).getUserName();
    }

    @Test
    @DisplayName("3. Kivételdobás mockolása a doThrow() használatával")
    void testMockUserExceptionThrowing() {
        User mockUser = mock(User.class);
        doThrow(new RuntimeException("Nem elérhető funkció")).when(mockUser).logout();

        assertThrows(RuntimeException.class, mockUser::logout);
        verify(mockUser, times(1)).logout();
    }

    @Test
    @DisplayName("4. User updatePwd() függvény sikeres hívása bejelentkezve -> UserDatabase.setPassword() meghívása")
    void testUpdatePwdCallsUserDatabaseWhenLoggedIn() {
        user.setId(123);

        boolean result = user.updatePwd("NewSuperSecretPwd", true);

        assertTrue(result);
        assertEquals("NewSuperSecretPwd", user.getPassword());
        // Ellenőrizzük, hogy a UserDatabase setPassword metódusa pontosan egyszer lefutott a megfelelő paraméterekkel
        verify(userDatabase, times(1)).setPassword(123, "NewSuperSecretPwd");
    }

    @Test
    @DisplayName("5. User updatePwd() függvény hívása ha nincs bejelentkezve -> UserDatabase nem hívódik meg")
    void testUpdatePwdDoesNotCallUserDatabaseWhenNotLoggedIn() {
        user.setId(123);

        boolean result = user.updatePwd("NewSuperSecretPwd", false);

        assertFalse(result);
        // Ellenőrizzük, hogy az adatbázis hívás nem történt meg
        verify(userDatabase, never()).setPassword(anyInt(), anyString());
    }

    @Test
    @DisplayName("6. UserDatabase hiba szimulálása updatePwd() során (doThrow)")
    void testUpdatePwdThrowsExceptionWhenDatabaseFails() {
        user.setId(123);

        // Szimuláljuk, hogy a UserDatabase UnsupportedOperationException-t dob
        doThrow(new UnsupportedOperationException("not implemented"))
                .when(userDatabase).setPassword(123, "AnyPassword");

        assertThrows(UnsupportedOperationException.class, () -> user.updatePwd("AnyPassword", true));
        verify(userDatabase, times(1)).setPassword(123, "AnyPassword");
    }

    @Test
    @DisplayName("7. Valódi UserDatabase setPassword híváskor UnsupportedOperationException-t dob")
    void testRealUserDatabaseThrowsException() {
        UserDatabase realDb = new UserDatabase();

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> realDb.setPassword(123, "TOPSECRET")
        );
        assertEquals("not implemented", exception.getMessage());
    }
}
