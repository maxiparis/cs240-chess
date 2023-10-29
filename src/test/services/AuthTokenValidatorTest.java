package services;

import DAO.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenValidatorTest {
    private AuthTokenValidator validator;
    private AuthDAO DB;

    @BeforeEach
    void setUp() {
        validator = new AuthTokenValidator();
        DB = AuthDAO.getInstance();
    }

    @AfterEach
    void tearDown() {
        DB.clear();
    }

    @Test
    void validate_Valid() throws DataAccessException {
        DB.insert(new AuthToken("test1", "12"));
        DB.insert(new AuthToken("test2", "23"));
        DB.insert(new AuthToken("test3", "34"));
        DB.insert(new AuthToken("test4", "45"));
        DB.insert(new AuthToken("test5", "56"));

        assertEquals(5, DB.getAuthTokensDB().size());

        boolean validToken = validator.validateAuthToken("23"); //valid
        assertTrue(validToken, "validToken was not validated correctly.");

        boolean validToken2 = validator.validateAuthToken("56"); //valid
        assertTrue(validToken2, "validToken2 was not validated correctly.");

        assertEquals(5, DB.getAuthTokensDB().size());

    }

    @Test
    void validate_Invalid() throws DataAccessException {
        DB.insert(new AuthToken("test1", "12"));
        DB.insert(new AuthToken("test2", "23"));
        DB.insert(new AuthToken("test3", "34"));
        DB.insert(new AuthToken("test4", "45"));
        DB.insert(new AuthToken("test5", "56"));

        assertThrows(DataAccessException.class, () -> {
            boolean invalidToken = validator.validateAuthToken("235234");
        });

        assertThrows(DataAccessException.class, () -> {
            boolean invalidToken = validator.validateAuthToken("invalidToken!");
        });
    }
}