package chess;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPositionImplTest {
    private static ChessPositionImpl test;


    @BeforeAll
    static void setTest(){
        test = new ChessPositionImpl(3,4);
    }

    @Test
    void testToString() {
        String expected = "(3, 4) ";
        assertEquals(expected, test.toString());
    }
}