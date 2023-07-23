/**
 * Player interface - the game player.
 *
 */
public interface Player {
	/**
	 * Function Name: setPlayerSymbol
	 * Description: sets the player symbol to given symbol.
	 * 
	 * @param player_symbol- the symbol to set as player symbol.
	 * @return: void
	 */
	void setPlayerSymbol(int player_symbol);

	/**
	 * Function Name: setOpponentSymbol
	 * Description: sets the opponent symbol to given symbol.
	 * 
	 * @param opponent_symbol- the symbol to set as opponent symbol.
	 * @return: void
	 */
	void setOpponentSymbol(int opponent_symbol);

	/**
	 * Function Name: getPlayerSymbol
	 * Description: returns the player symbol .
	 * 
	 * @param void
	 * @return: player_symbol- the symbol representing the player.
	 */
	int getPlayerSymbol();

	/**
	 * Function Name: getOpponentSymbol
	 * Description: returns the opponent symbol .
	 * 
	 * @param void
	 * @return: opponent_symbol- the symbol representing the opponent.
	 */
	int getOpponentSymbol();

	/**
	 * Function Name: isPlayerTurn
	 * Description: returns true if its the player turn.
	 * 
	 * @param void. @return:
	 * returns true if its the player turn.
	 */
	boolean isPlayerTurn();

	/**
	 * Function Name: setPlayerTurn
	 * Description: sets the player turn to given value(true/false).
	 * 
	 * @param player_turn - the value(true/false) to be set.
	 * @return: void.
	 */
	void setPlayerTurn(boolean player_turn);

}
