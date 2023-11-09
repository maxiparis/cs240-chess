package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardImplTest {
    public static ChessBoardImpl testBoard;

    @BeforeAll
    public static void setUpBoard(){
        testBoard = new ChessBoardImpl();
    }


    @Test
    @DisplayName("Testing the ToString()")
    void testToString() {
        testBoard.resetBoard();
        String expected =
                "8 |r|n|b|q|k|b|n|r|\n" +
                "7 |p|p|p|p|p|p|p|p|\n" +
                "6 | | | | | | | | |\n" +
                "5 | | | | | | | | |\n" +
                "4 | | | | | | | | |\n" +
                "3 | | | | | | | | |\n" +
                "2 |P|P|P|P|P|P|P|P|\n" +
                "1 |R|N|B|Q|K|B|N|R|\n" +
                "   1 2 3 4 5 6 7 8 ";
        String actual = testBoard.toString();
        Assertions.assertEquals(expected, actual);

    }
}