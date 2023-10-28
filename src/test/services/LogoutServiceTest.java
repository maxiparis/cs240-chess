package services;

import DAO.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import responses.LogoutResponse;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {
    private LogoutService service;
    private AuthDAO DB;
    private AuthToken token;

    @BeforeEach
    void setUp() {
        service = new LogoutService();
        DB = AuthDAO.getInstance();
        token = new AuthToken("John", "123456789");
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        try {
            DB.clear();
        } catch (DataAccessException e){  }
    }

    @Test
    void logoutValid() throws DataAccessException {
        //Valid - logging out a user correctly. The authToken is correctly removed from the DB.
        DB.insert(token);
        assertTrue(DB.getAuthTokensDB().size() == 1, "The DB did not add correctly the token.");
        LogoutResponse response = service.logout("123456789");
        assertTrue(DB.getAuthTokensDB().size() == 0, "The DB did not REMOVE correctly the token.");
        assertTrue(response.getMessage() == null, "The response message was not set to null.");


        DB.insert(new AuthToken("alex","53254325"));
        DB.insert(new AuthToken("eric","423423"));
        DB.insert(new AuthToken("stephen","222455"));
        DB.insert(new AuthToken("max","34234"));
        DB.insert(new AuthToken("thomas","1123123"));

        assertTrue(DB.getAuthTokensDB().size() == 5);

        LogoutResponse response2 = service.logout("53254325");

        assertTrue(DB.getAuthTokensDB().size() == 4);
        assertTrue(response2.getMessage() == null, "The response2 message was not set to null.");

    }

    @Test
    void logoutInvalidAuthTokenNotFound() throws DataAccessException {
        DB.insert(token);
        LogoutResponse response = service.logout("wrongAuthToken");
        assertEquals("Error: unauthorized", response.getMessage(),
                "The error messages are not the same.");

        DB.insert(new AuthToken("alex","53254325"));
        DB.insert(new AuthToken("eric","423423"));
        DB.insert(new AuthToken("stephen","222455"));
        DB.insert(new AuthToken("max","34234"));
        DB.insert(new AuthToken("thomas","1123123"));

        LogoutResponse response2 = service.logout("wrongAuthToken");
        assertEquals("Error: unauthorized", response2.getMessage(),
                "The error messages are not the same.");


    }

    @Test
    void logoutInvalidEmptyDB() throws DataAccessException {
        LogoutResponse response = service.logout("wrongAuthToken");
        assertEquals("Error: DB is empty", response.getMessage(),
                "The error messages are not the same.");

        DB.insert(new AuthToken("alex","53254325"));
        DB.insert(new AuthToken("eric","423423"));
        DB.insert(new AuthToken("stephen","222455"));
        DB.insert(new AuthToken("max","34234"));
        DB.insert(new AuthToken("thomas","1123123"));

        LogoutResponse response2 = service.logout("wrongAuthToken");
        assertNotEquals("Error: DB is empty", response2.getMessage(),
                "The error messages are the same.");

    }
}