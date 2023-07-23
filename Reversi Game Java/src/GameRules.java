/**
 * GameRules interface - the game rules .
 *
 */
public interface GameRules {

	/**
	 * Function Name: setWinnerScore
	 * Description: sets the Game winner score to given score.
	 * 
	 * @param player_score - the player score to set as winner score.
	 * @return: void
	 */
	void setWinnerScore(int player_score);

	/**
	 * Function Name: setWinnerSymbol
	 * Description: sets the Game winner symbol to given symbol.
	 * 
	 * @param player_symbol- the player symbol to set as winner symbol.
	 * @return: void
	 */

	void setWinnerSymbol(char player_symbol);

	/**
	 * Function Name: getRuleDescription
	 * Description:returns a short description of the game rules.
	 * 
	 * @param void.
	 * @return: rule_description - a short description of the game rules.
	 */
	String getRuleDescription();

	/**
	 * Function Name: checkValidMove
	 * Description: checks each move if its allowed(valid).
	 * 
	 * @param current_piece - the symbol of the piece being checked.
	 * @param player_symbol - the current player game piece symbol .
	 * @param opponent_encounterd - true if encountered opponent on the strike path.
	 * @param is_Board_Edge_piece - true if the current piece is at the edge of the
	 * board.
	 * @return: true if the move on the current piece is possible(valid).
	 */
	boolean checkValidMove(char current_piece, char player_symbol, boolean opponent_encounterd,
			boolean is_Board_Edge_piece);

	/**
	 * Function Name: isGameOver
	 * Description: checks the "game over" conditions.
	 * 
	 * @param score_counter- the game score counter, keeps current scores.
	 * @param player1_turn - true if its player1 turn false otherwise.
	 * @param player2_turn - true if its player2 turn false otherwise.
	 * @return: true if the game over conditions are met.
	 */
	boolean isGameOver(Counter score_counter, boolean player1_turn, boolean player2_turn);

	/**
	 * Function Name: getWinnerSymbol
	 * Description: returns the symbol of the player with the highest score.
	 * 
	 * @param void
	 * @return: winner symbol - the symbol of the current game winner.
	 */
	int getWinnerSymbol();

	/**
	 * Function Name: getWinnerScore
	 * Description: returns the score of the player with the highest score.
	 * 
	 * @param void
	 * @return: winner score - the score of the current game winner.
	 */
	int getWinnerScore();

}
