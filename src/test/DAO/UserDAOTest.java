package DAO;

import dataAccess.DataAccessException;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private UserDAO userDAO;
    private User model;
    private User model2;


    @BeforeEach
    void setUp() throws DataAccessException {
        AuthDAO.getInstance().clear();
        userDAO = UserDAO.getInstance();
        model = new User("john7", "johnPass", "john@gmail.com");
        model2 = new User("carla6", "carlaPass", "carla@gmail.com");

        userDAO.insert(new User("john","asdfasdf--","ffsdf@hotmail.cl"));
        userDAO.insert(new User("alex","fsffsff335#","eerer@hotmail.cl"));
        userDAO.insert(new User("steve","ffeeffsd","steve@hotmail.cl"));
        userDAO.insert(new User("kate","fsd@#$@f","test2@hotmail.cl"));
        userDAO.insert(new User("connor","fsdf-sdfsd","test3@hotmail.cl"));
    }

    @AfterEach
    void cleanUp() throws DataAccessException {
        AuthDAO.getInstance().clear(); //cleaning authTokens to keep data
        userDAO.clear();
    }

    @Test
    void insert() throws DataAccessException {
        //insert
        userDAO.insert(new User("newUser","apassword","myemail@hotmail.cl"));
        User expected = new User("newUser","apassword","myemail@hotmail.cl");
        User actual = userDAO.find("newUser");
            //make sure it was added correctly
        assertEquals(expected, actual);

        //insert user that is already there
        assertThrows(DataAccessException.class, () -> {
            userDAO.insert(new User("newUser","apassword","myemail@hotmail.cl"));
        });
    }

    @Test
    void find() throws DataAccessException {
//        //user is in db
        User actual = userDAO.find("steve");
        User expected = new User("steve","ffeeffsd","steve@hotmail.cl");
        assertEquals(expected, actual);

        //Invalid  cases
        //user is not in db
        assertThrows(DataAccessException.class, () -> {
            userDAO.find("invalidUser");
        });

        //argument is null
        assertThrows(DataAccessException.class, () -> {
            userDAO.find(null);
        });


    }

    @Test
    void findAll() throws DataAccessException {
        //valid
        HashSet<User> actual = userDAO.findAll();
        HashSet<User> expected = new HashSet<>();

        expected.add(new User("john","asdfasdf--","ffsdf@hotmail.cl"));
        expected.add(new User("alex","fsffsff335#","eerer@hotmail.cl"));
        expected.add(new User("steve","ffeeffsd","steve@hotmail.cl"));
        expected.add(new User("kate","fsd@#$@f","test2@hotmail.cl"));
        expected.add(new User("connor","fsdf-sdfsd","test3@hotmail.cl"));


        assertEquals(expected, actual);

        //invalid - set is empty
        userDAO.clear();
        assertThrows(DataAccessException.class, () -> {
            userDAO.findAll();
        });

    }


    @Test
    void remove() throws DataAccessException {
        //valid
        userDAO.remove("john");

        assertThrows(DataAccessException.class, () -> {
            User actual = userDAO.find("john");
        });

        //invalid - the user is not in the db
        assertThrows(DataAccessException.class, () -> {
           userDAO.remove("somethingInvalid");
        });
    }

    @Test
    void clear() throws DataAccessException {
        assertEquals(5, userDAO.findAll().size()); //db is not empty before clear()
        userDAO.clear();

        assertThrows(DataAccessException.class, () -> {
            userDAO.findAll(); //findAll throws exception when the db is empty
        });
    }

    @Test
    void testFindWithUsernameAndPassword_Valid() throws DataAccessException {
        User actual = userDAO.findWithUsernameAndPassword("kate", "fsd@#$@f");
        User expected = new User("kate","fsd@#$@f","test2@hotmail.cl");
        assertEquals(expected, actual);


    }

    @Test
    void testFindWithUsernameAndPassword_Invalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            User actual = userDAO.findWithUsernameAndPassword("kate", "wrongpass");
        });

        assertThrows(DataAccessException.class, () -> {
            User actual = userDAO.findWithUsernameAndPassword("wrongUser", "fsd@#$@f");
        });
    }
}
