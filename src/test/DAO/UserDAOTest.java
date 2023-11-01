package DAO;

import dataAccess.DataAccessException;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.RegisterService;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private UserDAO userDAO;
    private User model;
    private User model2;


    @BeforeEach
    void setUp() {
        userDAO = UserDAO.getInstance();
        model = new User("john7", "johnPass", "john@gmail.com");
        model2 = new User("carla6", "carlaPass", "carla@gmail.com");
    }

    @AfterEach
    void cleanUp() throws DataAccessException {
        userDAO.clear();
    }

    @Test
    void insert() throws DataAccessException {
        //insert users
        userDAO.insert(new User("john","asdfasdf","ffsdf@hotmail.cl"));
        userDAO.insert(new User("alex","fsffs","eerer@hotmail.cl"));
        userDAO.insert(new User("steve","ffeef","steve@hotmail.cl"));
        userDAO.insert(new User("testy","fsdf","testy@hotmail.cl"));


//        assertTrue(UserDAO.getInstance().getUsersDB().contains(model));
//
//        userDAO.insert(model2);
//        assertTrue(UserDAO.getInstance().getUsersDB().contains(model));
//        assertTrue(UserDAO.getInstance().getUsersDB().contains(model2));
//
//        //insert user that is already there
//        assertThrows(DataAccessException.class, () -> {
//            userDAO.insert(model2);
//        });

    }

    @Test
    void find() throws DataAccessException {
//        userDAO.insert(new User("john","asdfasdf","ffsdf@hotmail.cl"));
//        userDAO.insert(new User("alex","fsffs","eerer@hotmail.cl"));
//        userDAO.insert(new User("steve","ffeef","steve@hotmail.cl"));
        userDAO.insert(new User("test","fsdf","testy@hotmail.cl"));

        User john = userDAO.find("test");


//        //user is in db
//        userDAO.insert(model);
//        User actual = userDAO.find(model.getUsername());
//        User expected = model;
//        assertEquals(expected, actual);
//
//        //user is not in db
//        assertThrows(DataAccessException.class, () -> {
//            userDAO.find(model2.getUsername());
//        });

    }

    @Test
    void findAll() throws DataAccessException {
        //valid
        userDAO.insert(model);
        userDAO.insert(model2);
        HashSet<User> actual = userDAO.findAll();
        assertTrue(actual.contains(model));
        assertTrue(actual.contains(model2));

        //invalid - set is empty
        userDAO.clear();
        assertThrows(DataAccessException.class, () -> {
            userDAO.findAll();
        });

    }

    @Test
    void update() throws DataAccessException {
        //valid
        userDAO.insert(model);
        userDAO.update(model.getUsername(), "newPassword", "updatedEmail");
        User update = new User(model.getUsername(), "newPassword", "updatedEmail");
        assertTrue(UserDAO.getInstance().getUsersDB().contains(update));
        assertFalse(UserDAO.getInstance().getUsersDB().contains(model));

        //invalid - trying to update something that is not there
        assertThrows(DataAccessException.class, () -> {
           userDAO.update(model2.getUsername(), "badPassoword", "badEmail");
        });
    }

    @Test
    void remove() throws DataAccessException {
        //valid
            userDAO.insert(model);
            userDAO.remove(model);
            assertTrue(UserDAO.getInstance().getUsersDB().isEmpty());

        //invalid - the user is not in the db
            assertThrows(DataAccessException.class, () -> {
               userDAO.remove(model2);
            });


    }

    @Test
    void clear() throws DataAccessException {
        userDAO.clear();


//        //valid
//        userDAO.insert(model);
//        userDAO.insert(model2);
//        userDAO.clear();
//        assertTrue(UserDAO.getInstance().getUsersDB().isEmpty());
//
//        //invalid - it was empty already
//        assertThrows(DataAccessException.class, () -> {
//            userDAO.clear();
//        });

    }

    @Test
    void findWithUsernameAndPassword() throws DataAccessException {
        //valid
            //model = new User("john7", "johnPass", "john@gmail.com");
        userDAO.insert(model);
        User actual = userDAO.findWithUsernameAndPassword(model.getUsername(), model.getPassword());
        assertSame(model, actual);

        //invalid
        String actualErrorMessage = "";
            //username does not match password
            try {
                User actual2 = userDAO.findWithUsernameAndPassword(model.getUsername(), "badPassword");
            } catch (DataAccessException e) {
                actualErrorMessage = e.getMessage();
            }
            assertSame("Error: unauthorized", actualErrorMessage);


            //username is not in the DB
            try {
                User actual3 = userDAO.findWithUsernameAndPassword("badUserName", model.getPassword());
            } catch (DataAccessException e) {
                actualErrorMessage = e.getMessage();
            }
            assertSame("Error: the username is not in the DB.", actualErrorMessage);

            //empty
            userDAO.clear();
            try {
                User actual4 = userDAO.findWithUsernameAndPassword("badUserName", model.getPassword());
            } catch (DataAccessException e) {
                actualErrorMessage = e.getMessage();
            }

        assertSame("Error: DB is empty.", actualErrorMessage);
    }

    @Test
    void testFindWithUsernameAndPassword_Valid() throws DataAccessException {
//        userDAO.insert(model);
//        userDAO.insert(model2);

        RegisterService registerService = new RegisterService();
        RegisterResponse registerResponse = registerService.register
                (new RegisterRequest("carla6", "carlaPass", "carla@gmail.com"));

        User foundUser = userDAO.findWithUsernameAndPassword("carla6", "carlaPass");
        assertNotNull(foundUser);

    }

    @Test
    void testFindWithUsernameAndPassword_Invalid() {

    }

}