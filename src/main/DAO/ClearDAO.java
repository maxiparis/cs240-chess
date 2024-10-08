package DAO;

import dataAccess.DataAccessException;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

public class ClearDAO {
    private Database database = Database.getInstance();
    public enum dataBaseType {
        USER,
        AUTHTOKEN,
        GAME
    }

    public void clear(dataBaseType dataBaseType) throws DataAccessException {
        String databaseName = null;
        if(dataBaseType.equals(ClearDAO.dataBaseType.USER)){
            databaseName = "user";
        } else if (dataBaseType.equals(ClearDAO.dataBaseType.AUTHTOKEN)) {
            databaseName = "authToken";
        } else if(dataBaseType.equals(ClearDAO.dataBaseType.GAME)){
            databaseName = "game";
        }

        String sql = "delete from " + databaseName;
        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            database.closeConnection(connection);

            throw new DataAccessException(e.getMessage());
        }
        database.closeConnection(connection);

    }
}
