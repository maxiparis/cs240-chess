package DAO;

import chess.*;
import dataAccess.DataAccessException;
import model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GameDAOTest {
    private GameDAO gameDAO = GameDAO.getInstance();

    @BeforeEach
    void setUp() throws DataAccessException {
        gameDAO.clear();
        //    public Game(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGameImpl game)
        //insert1
        ChessGameImpl game = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        ChessBoardImpl board = new ChessBoardImpl();
        board.resetBoard();
        game.setBoard(board);
        Game gameToInsert = new Game(0, "sam", "tony",
                "gameName", game);
        gameDAO.insert(gameToInsert);


        //insert2
        ChessGameImpl game2 = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        ChessBoardImpl board2 = new ChessBoardImpl();
        board2.resetBoard();
        board2.addPiece(new ChessPositionImpl(4,5), new Rook(ChessGame.TeamColor.WHITE));
        board2.addPiece(new ChessPositionImpl(4,6), new Knight(ChessGame.TeamColor.BLACK));
        board2.addPiece(new ChessPositionImpl(3,7), new Bishop(ChessGame.TeamColor.WHITE));
        game2.setBoard(board2);
        Game gameToInsert2 = new Game(0, null, "alex",
                "ourGame", game2);
        gameDAO.insert(gameToInsert2);

        //insert3
        ChessGameImpl game3 = new ChessGameImpl(ChessGame.TeamColor.BLACK);
        ChessBoardImpl board3 = new ChessBoardImpl();
        game3.setBoard(board3);
        Game gameToInsert3 = new Game(0, "Tom", "theBest",
                "Masters", game3);
        gameDAO.insert(gameToInsert3);


    }

    @AfterEach
    void tearDown() throws DataAccessException {
        gameDAO.clear();
    }

    @Test
    void insert() throws DataAccessException {
        //insert one
        ChessGameImpl game = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        ChessBoardImpl board = new ChessBoardImpl();
        board.resetBoard();
        game.setBoard(board);
        Game testJSONSerialization = new Game(0, "alexa", "sanders",
                "gameName2", game);
        gameDAO.insert(testJSONSerialization);

        //insert one that is already there
        assertThrows(DataAccessException.class, () -> {
            gameDAO.insert(testJSONSerialization);
        });
    }

    @Test
    void find() throws DataAccessException {
        //not the best, needs to improve this one, there was a bug and it didn't detect it.
        //game is in db
        Game actual = gameDAO.find("ourGame");

        ChessGameImpl game2 = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        ChessBoardImpl board2 = new ChessBoardImpl();
        board2.resetBoard();
        board2.addPiece(new ChessPositionImpl(4,5), new Rook(ChessGame.TeamColor.WHITE));
        board2.addPiece(new ChessPositionImpl(4,6), new Knight(ChessGame.TeamColor.BLACK));
        board2.addPiece(new ChessPositionImpl(3,7), new Bishop(ChessGame.TeamColor.WHITE));
        game2.setBoard(board2);
        Game expected = new Game(0, null, "alex",
                "ourGame", game2);
        assertEquals(expected.getGame(), actual.getGame());
        assertEquals(expected.getWhiteUsername(), actual.getWhiteUsername());
        assertEquals(expected.getBlackUsername(), actual.getBlackUsername());
        assertEquals(expected.getGameName(), actual.getGameName());

//        //user is not in db
        assertThrows(DataAccessException.class, () -> {
            gameDAO.find("invalid");
        });
    }

    @Test
    void findAll() throws DataAccessException {
        gameDAO.clear();
        //insert1
        ChessGameImpl game = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        ChessBoardImpl board = new ChessBoardImpl();
        board.resetBoard();
        game.setBoard(board);
        Game gameToInsert = new Game(0, "sam", "tony",
                "gameName", game);
        gameDAO.insert(gameToInsert);


        //insert2
        ChessGameImpl game2 = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        ChessBoardImpl board2 = new ChessBoardImpl();
        board2.resetBoard();
        board2.addPiece(new ChessPositionImpl(4,5), new Rook(ChessGame.TeamColor.WHITE));
        board2.addPiece(new ChessPositionImpl(4,6), new Knight(ChessGame.TeamColor.BLACK));
        board2.addPiece(new ChessPositionImpl(3,7), new Bishop(ChessGame.TeamColor.WHITE));
        game2.setBoard(board2);
        Game gameToInsert2 = new Game(0, null, "alex",
                "ourGame", game2);
        gameDAO.insert(gameToInsert2);

        //insert3
        ChessGameImpl game3 = new ChessGameImpl(ChessGame.TeamColor.BLACK);
        ChessBoardImpl board3 = new ChessBoardImpl();
        game3.setBoard(board3);
        Game gameToInsert3 = new Game(0, "Tom", "theBest",
                "Masters", game3);
        gameDAO.insert(gameToInsert3);


        //valid
        HashSet<Game> actual = gameDAO.findAll();
        assertEquals(3, actual.size());

        for (Game gameInActual : actual) {
            boolean atLeastOneGameNameMatches = (gameInActual.getGameName().equals("Masters") ||
                    gameInActual.getGameName().equals("gameName") ||
                    gameInActual.getGameName().equals("ourGame")
            );
            assertTrue(atLeastOneGameNameMatches);

            if(gameInActual.getGameName().equals(gameToInsert.getGameName())){
                assertEquals(gameInActual.getWhiteUsername(), gameToInsert.getWhiteUsername());
                assertEquals(gameInActual.getBlackUsername(), gameToInsert.getBlackUsername());
                assertEquals(gameInActual.getGame(), gameToInsert.getGame());
            } else if(gameInActual.getGameName().equals(gameToInsert2.getGameName()) ){
                assertEquals(gameInActual.getWhiteUsername(), gameToInsert2.getWhiteUsername());
                assertEquals(gameInActual.getBlackUsername(), gameToInsert2.getBlackUsername());
                assertEquals(gameInActual.getGame(), gameToInsert2.getGame());
            } else if (gameInActual.getGameName().equals(gameToInsert3)) {
                assertEquals(gameInActual.getWhiteUsername(), gameToInsert3.getWhiteUsername());
                assertEquals(gameInActual.getBlackUsername(), gameToInsert3.getBlackUsername());
                assertEquals(gameInActual.getGame(), gameToInsert3.getGame());
            }
        }
    }

    @Test
    void updateGame() throws DataAccessException {
        //valid
        Game gameBeforeUpdate = gameDAO.find("ourGame");
        ChessGameImpl chessGameFoundAndUpdated = gameBeforeUpdate.getGame();
        ChessBoardImpl boardToUpdate = chessGameFoundAndUpdated.getBoard();
        boardToUpdate.addPiece(new ChessPositionImpl(4,1),
                new King(ChessGame.TeamColor.WHITE));

        chessGameFoundAndUpdated.setBoard(boardToUpdate);

        gameDAO.updateGame("ourGame", "newWhite", "newBlackie",
                chessGameFoundAndUpdated);

        Game gameAfterUpdate = gameDAO.find("ourGame");

        assertEquals("ourGame", gameAfterUpdate.getGameName());
        assertEquals("newWhite", gameAfterUpdate.getWhiteUsername());
        assertEquals("newBlackie", gameAfterUpdate.getBlackUsername());
        assertEquals(gameAfterUpdate.getGame(), chessGameFoundAndUpdated);

        //invalid - trying to update something that is not there
//        gameDAO.clear();


        assertThrows(DataAccessException.class, () -> {
            gameDAO.updateGame("wrongName", "newWhite",
                    "newBlackie", chessGameFoundAndUpdated);
        });
    }

    @Test
    void remove() throws DataAccessException {
        //valid
        //first, I can find it
        Game foundGameBeforeRemove = gameDAO.find("Masters");
        assertNotNull(foundGameBeforeRemove);

        gameDAO.remove("Masters");

        assertThrows(DataAccessException.class, () -> {
            Game actual = gameDAO.find("Masters");
        });

        //invalid - the user is not in the db
        assertThrows(DataAccessException.class, () -> {
            gameDAO.remove("somethingInvalid");
        });
    }

    @Test
    void clear() throws DataAccessException {
        assertEquals(3, gameDAO.findAll().size());
        gameDAO.clear();
        assertTrue(gameDAO.findAll().isEmpty());
    }
}