package chess;

import java.util.Objects;

public class ChessPositionImpl implements ChessPosition{
    private int rowNumber;
    private int columnNumber;

    public ChessPositionImpl(int rowNumber, int columnNumber) {
        this.rowNumber=rowNumber;
        this.columnNumber=columnNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPositionImpl that=(ChessPositionImpl) o;
        return rowNumber == that.rowNumber && columnNumber == that.columnNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNumber, columnNumber);
    }

    @Override
    public int getRow() {
        return rowNumber;
    }

    @Override
    public int getColumn() {
        return columnNumber;
    }

    @Override
    public String toString() {
        return "(" + rowNumber + ", " + columnNumber + ") ";
    }
}
