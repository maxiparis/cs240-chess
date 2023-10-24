package DAO;

import dataAccess.DataAccessException;

import java.util.HashSet;

public class ClearDAO {

    public void clear(HashSet setToClear) throws DataAccessException {
        if (!setToClear.isEmpty()) {
            setToClear.clear();
        } else {
            throw new DataAccessException("The DB could not be cleared because it was empty.");
        }
    }
}
