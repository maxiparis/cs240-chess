package chess;

public class GameChessPosition implements ChessPosition{
    private int rowNumber;
    private int columnNumber;

    public GameChessPosition(int rowNumber, int columnNumber) {
        this.rowNumber=rowNumber;
        this.columnNumber=columnNumber;
    }

    @Override
    public int getRow() {
        return rowNumber;
    }

    @Override
    public int getColumn() {
        return columnNumber;
    }
}
