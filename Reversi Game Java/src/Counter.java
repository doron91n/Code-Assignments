
/**
 * Counter class - keeps track of game scores..
 *
 */
public class Counter {
    private int player_1_score_ = 0; // Default value
    private int player_2_score_ = 0; // Default value
    private int empty_cells_ = 0; // Default value

    /**
     * Function Name: addToPlayer
     * Description: adds or remove from players scores based on given parameters.
     * 
     * @param symbol_before -the symbol being changed,which player loses score.
     * @param change_to_symbol-the symbol being changed to,
     *            which player gains score.
     * @return: void
     */
    public void addToPlayer(int symbol_before, int change_to_symbol) {
        // no need to switch if they are the same symbol already.
        if (symbol_before != change_to_symbol) {
            if (symbol_before == GamePieceSymbol.EMPTY.getSymbolValue()) {
                if (change_to_symbol == GamePieceSymbol.PLAYER_1.getSymbolValue()) {
                    setPlayer1Score(getPlayer1Score() + 1);
                }
                if (change_to_symbol == GamePieceSymbol.PLAYER_2.getSymbolValue()) {
                    setPlayer2Score(getPlayer2Score() + 1);
                }
                setEmptyCells(getEmptyCells() - 1);
            }
            if (symbol_before == GamePieceSymbol.PLAYER_1.getSymbolValue()) {
                // fliping game piece from player 1 to player 2.
                if (change_to_symbol == GamePieceSymbol.PLAYER_2.getSymbolValue()) {
                    setPlayer2Score(getPlayer2Score() + 1);
                }
                // fliping game piece from player 1 to empty.
                if (change_to_symbol == GamePieceSymbol.EMPTY.getSymbolValue()) {
                    setEmptyCells(getEmptyCells() + 1);
                }
                setPlayer1Score(getPlayer1Score() - 1);
            }
            if (symbol_before == GamePieceSymbol.PLAYER_2.getSymbolValue()) {
                // fliping game piece from player 2 to player 1.
                if (change_to_symbol == GamePieceSymbol.PLAYER_1.getSymbolValue()) {
                    setPlayer1Score(getPlayer1Score() + 1);
                }
                // fliping game piece from player 2 to empty.
                if (change_to_symbol == GamePieceSymbol.EMPTY.getSymbolValue()) {
                    setEmptyCells(getEmptyCells() + 1);
                }
                setPlayer2Score(getPlayer2Score() - 1);
            }
        }
    }

    /**
     * Function Name: getEmptyCells
     * Description: returns the number of empty cells in game board.
     * 
     * @param void.
     * @return: empty_cells - the number of empty cells in game board.
     */
    public int getEmptyCells() {
        return (this.empty_cells_);
    }

    /**
     * Function Name: setEmptyCells
     * Description: sets the number of empty cells in game board.
     * 
     * @param empty_cells - the number of empty cells in game board.
     * @return: void.
     */
    public void setEmptyCells(int empty_cells) {
        this.empty_cells_ = empty_cells;
    }

    /**
     * Function Name: getPlayer1Score
     * Description: returns player 1 current score.
     * 
     * @param void.
     * @return:player_1_score_ - player 1 current score.
     */
    public int getPlayer1Score() {
        return (this.player_1_score_);
    }

    /**
     * Function Name: setPlayer1Score
     * Description: sets player 1 score to given score.
     * 
     * @param player_1_score -the player 1 score.
     * @return: void
     */
    private void setPlayer1Score(int player_1_score) {
        this.player_1_score_ = player_1_score;
    }

    /**
     * Function Name: getPlayer2Score
     * Description: returns player 2 current score.
     * 
     * @param void.
     * @return:player_2_score_ - player 2 current score.
     */
    public int getPlayer2Score() {
        return (this.player_2_score_);
    }

    /**
     * Function Name: setPlayer2Score
     * Description: sets player 2 score to given score.
     * 
     * @param player_2_score -the player 2 score.
     * @return: void.
     */
    private void setPlayer2Score(int player_2_score) {
        this.player_2_score_ = player_2_score;
    }
}
