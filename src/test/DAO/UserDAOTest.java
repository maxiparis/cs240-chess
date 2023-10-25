package DAO;

import dataAccess.DataAccessException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private UserDAO userDAO;
    private User model;
    private User model2;


    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        model = new User("john7", "johnPass", "john@gmail.com");
        model2 = new User("carla6", "carlaPass", "carla@gmail.com");
    }

    @Test
    void insert() throws DataAccessException {
        //insert one user
        userDAO.insert(model);
        assertTrue(UserDAO.getUsersDB().contains(model));

        userDAO.insert(model2);
        assertTrue(UserDAO.getUsersDB().contains(model));
        assertTrue(UserDAO.getUsersDB().contains(model2));

        //insert user that is already there
        assertThrows(DataAccessException.class, () -> {
            userDAO.insert(model2);
        });

    }

    @Test
    void find() throws DataAccessException {
        //user is in db
        userDAO.insert(model);
        User actual = userDAO.find(model);
        User expected = model;
        assertEquals(expected, actual);

        //user is not in db
        assertThrows(DataAccessException.class, () -> {
            userDAO.find(model2);
        });

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
        assertTrue(UserDAO.getUsersDB().contains(update));
        assertFalse(UserDAO.getUsersDB().contains(model));

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
            assertTrue(UserDAO.getUsersDB().isEmpty());

        //invalid - the user is not in the db
            assertThrows(DataAccessException.class, () -> {
               userDAO.remove(model2);
            });


    }

    @Test
    void clear() throws DataAccessException {
        //valid
        userDAO.insert(model);
        userDAO.insert(model2);
        userDAO.clear();
        assertTrue(UserDAO.getUsersDB().isEmpty());

        //invalid - it was empty already
        assertThrows(DataAccessException.class, () -> {
            userDAO.clear();
        });

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
}