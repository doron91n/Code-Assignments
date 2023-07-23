
/**
 * GamePiece class - the game piece .
 *
 */
public class GamePiece {

    private int piece_row_ = -1;
    private int piece_column_ = -1;
    private int piece_symbol_ = GamePieceSymbol.EMPTY.getSymbolValue();
    private Point piece_location_point_ = new Point(-1, -1);

    /**
     * Function Name: GamePiece
     * Description: constructor.
     * 
     * @param void
     * @return: void
     */
    public GamePiece() {
    }

    /**
     * Function Name: GamePiece
     * Description: constructor.
     * 
     * @param row - the row the game piece is located at.
     * @param column - the column the game piece is located at.
     * @param symbol - the game piece symbol.
     * @return: void
     */
    public GamePiece(int row, int column, int symbol) {
        setPieceLocationPoint(new Point(row, column));
        setGamePieceSymbol(symbol);
    }

    /**
     * Function Name: GamePiece
     * Description: constructor.
     * 
     * @param location_p - the location point for the game piece (row,column).
     * @param symbol - the game piece symbol.
     * @return: void
     */
    public GamePiece(Point location_p, int symbol)

    {
        this(location_p.getRow(), location_p.getColumn(), symbol);
    }

    /**
     * Function Name: getGamePieceRow
     * Description: returns the Game Piece row.
     * 
     * @param void
     * @return: row - the row the game piece is located at.
     */
    public int getGamePieceRow() {
        return (this.piece_row_);
    }

    /**
     * Function Name: setGamePieceRow
     * Description: sets the Game Piece row to given row.
     * 
     * @param row - the row the game piece is located at.
     * @return: void
     */
    private void setGamePieceRow(int row) {
        this.piece_row_ = row;
    }

    /**
     * Function Name: getGamePieceColumn
     * Description: returns the Game Piece Column .
     * 
     * @param void
     * @return: column - the column the game piece is located at.
     */
    public int getGamePieceColumn() {
        return (this.piece_column_);
    }

    /**
     * Function Name: setGamePieceColumn
     * Description: sets the Game Piece Column to given coulmn.
     * 
     * @param column - the column the game piece is located at.
     * @return: void
     */
    private void setGamePieceColumn(int column) {
        this.piece_column_ = column;
    }

    /**
     * Function Name: getPieceLocationPoint
     * Description: return game piece location point.
     * 
     * @param void
     * @return: location_p - the location point for the game piece (row,column).
     */
    public Point getPieceLocationPoint() {
        return (this.piece_location_point_);
    }

    /**
     * Function Name: setPieceLocationPoint
     * Description: sets game piece location point to given point.
     * 
     * @param piece location_p - the location point for the game piece(row,column).
     * @return: void
     */
    public void setPieceLocationPoint(Point piece_location_point) {
        this.piece_location_point_ = piece_location_point;
        setGamePieceRow(piece_location_point.getRow());
        setGamePieceColumn(piece_location_point.getColumn());
    }

    /**
     * Function Name: getGamePieceSymbol
     * Description: returns the game piece symbol.
     * 
     * @param void
     * @return: symbol - the game piece symbol.
     */
    public int getGamePieceSymbol() {
        return (this.piece_symbol_);
    }

    /**
     * Function Name: setGamePieceSymbol
     * Description: sets the game piece symbol to given symbol.
     * 
     * @param symbol - the game piece symbol.
     * @return: void
     */
    public void setGamePieceSymbol(int symbol) {
        this.piece_symbol_ = symbol;
    }

    /**
     * Function Name: toString
     * Description: returns a string representing its symbol and location p.
     * 
     * @param void
     * @return: string representing game piece symbol and location p.
     */
    @Override
    public String toString() {
        String sp = getPieceLocationPoint().toString();
        String s = "game piece at point: " + sp + "  is : " + (char) getGamePieceSymbol();
        return (s);
    }

}
