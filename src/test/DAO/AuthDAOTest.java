package DAO;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthDAOTest {
    private AuthDAO authDAO;
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        userDAO = UserDAO.getInstance();
        authDAO = AuthDAO.getInstance();
        authDAO.clear();
        userDAO.clear();

        userDAO.insert(new User("john","asdfasdf--","ffsdf@hotmail.cl"));
        userDAO.insert(new User("alex","fsffsff335#","eerer@hotmail.cl"));
        userDAO.insert(new User("steve","ffeeffsd","steve@hotmail.cl"));
        userDAO.insert(new User("kate","fsd@#$@f","test2@hotmail.cl"));
        userDAO.insert(new User("connor","fsdf-sdfsd","test3@hotmail.cl"));


        authDAO.insert(new AuthToken("john", UUID.randomUUID().toString()));
        authDAO.insert(new AuthToken("alex", UUID.randomUUID().toString()));
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        authDAO.clear();
        userDAO.clear();
    }

    @Test
    void insert() throws DataAccessException {
        //valid
        String steveToken = UUID.randomUUID().toString();
        authDAO.insert(new AuthToken("steve", steveToken ));

        //adding a second element
        String kateToken =  UUID.randomUUID().toString();
        authDAO.insert(new AuthToken("kate", kateToken));

        AuthToken foundToken1 = authDAO.find("steve");
        AuthToken foundToken2 = authDAO.find("kate");

        assertEquals(steveToken, foundToken1.getToken());
        assertEquals(kateToken, foundToken2.getToken());

        assertEquals("steve", foundToken1.getUsername());
        assertEquals("kate", foundToken2.getUsername());

        //invalid -> try to insert a valid token for a user that does not exist
        assertThrows(DataAccessException.class, () -> {
            authDAO.insert(new AuthToken("invalidUser", "asdfasdfasdfsadf" ));
        });

        //invalid -> try to insert a valid token for a user that is already there
        assertThrows(DataAccessException.class, () -> {
            authDAO.insert(new AuthToken("steve", "asdfasdfasdfsadf" ));
        });
    }

    @Test
    void find() throws DataAccessException {
    //valid
        String steveToken = UUID.randomUUID().toString();
        authDAO.insert(new AuthToken("steve", steveToken ));

        AuthToken foundToken = authDAO.find("steve");

        assertEquals(steveToken, foundToken.getToken());

        assertEquals("steve", foundToken.getUsername());

    //invalid -> find with username that does not exist in the DB.
        assertThrows(DataAccessException.class, ()-> {
            authDAO.find("invalidUserName");
        });
    }

    @Test
    void findAll() throws DataAccessException {
    //valid
        authDAO.clear();

        String johnToken = UUID.randomUUID().toString();
        String alexToken = UUID.randomUUID().toString();

        authDAO.insert(new AuthToken("john", johnToken));
        authDAO.insert(new AuthToken("alex", alexToken));

        HashSet<AuthToken> actual = authDAO.findAll();

        HashSet<AuthToken> expected = new HashSet<>();
        expected.add(new AuthToken("john", johnToken));
        expected.add(new AuthToken("alex", alexToken));


        assertEquals(2, actual.size());
        assertEquals(expected, actual);

        authDAO.clear();
    //invalid
        assertThrows(DataAccessException.class, () -> {
            HashSet<AuthToken> error=authDAO.findAll();
        });
    }

    @Test
    void update() throws DataAccessException {
    //Valid
        authDAO.update("john", "newToken");
        AuthToken foundToken = authDAO.find("john");
        assertEquals("newToken", foundToken.getToken());
        assertEquals("john", foundToken.getUsername());

    //Invalid
        //non-emtpy db that does not have the username
        assertThrows(DataAccessException.class, () -> {
            authDAO.update("invalidUsername", "isNotGonnaWork");
        });

        //emtpy db
        authDAO.clear();
        assertThrows(DataAccessException.class, () -> {
            authDAO.update("Erick", "1238971945");
        });
    }

    @Test
    void remove() throws DataAccessException {
    //valid
        AuthToken foundBeforeRemove = authDAO.find("alex"); //should work because it is in DB.
        authDAO.remove("alex");
        assertThrows(DataAccessException.class, () -> {
            authDAO.find("alex"); //should throw because it is not in the DB anymore.
        });
        HashSet<AuthToken> authsAfterDelete = authDAO.findAll();
        assertFalse(authsAfterDelete.contains(foundBeforeRemove));

        //remove something that does not exist
        assertThrows(DataAccessException.class, () -> {
            authDAO.clear();
            authDAO.remove("alex");
        });
    }

    @Test
    void clear() throws DataAccessException {
        assertEquals(2, authDAO.findAll().size());
        authDAO.clear();
        assertThrows(DataAccessException.class, ()->{
            authDAO.findAll();
        });
    }

    @Test
    void findWithToken() throws DataAccessException {
        //valid
        String steveToken = UUID.randomUUID().toString();
        authDAO.insert(new AuthToken("steve", steveToken));

        AuthToken foundToken = authDAO.findWithToken(steveToken);

        assertEquals(steveToken, foundToken.getToken());

        assertEquals("steve", foundToken.getUsername());

        //invalid -> find with username that does not exist in the DB.
        assertThrows(DataAccessException.class, ()-> {
            authDAO.findWithToken("invalidUserName");
        });
    }
}