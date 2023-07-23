
/**
 * Game interface - responsible for the game flow .
 *
 */
import java.util.Vector;

public interface Game {

	/**
	 * Function Name: getUserInput
	 * Description: asks for user input - player next move, 2 numbers.
	 * 
	 * @param void.
	 * @return: point representing player next move.
	 */
	Point getUserInput();

	/**
	 * Function Name: getValidMoveIndex
	 * Description: checks if the move the user entered is a valid player move.
	 * 
	 * @param player_moves-a vector containing all strike_vector, each
	 * strike_vector contains the game pieces to be fliped in the strike path.
	 * @return: move_index - the index to chosen move is player moves.
	 */
	int getValidMoveIndex(Vector<Vector<GamePiece>> player_moves);

	/**
	 * Function Name: playOneTurn
	 * Description: plays one player turn in the game.
	 * 
	 * @param void.
	 * @return: void.
	 */
	void playOneTurn();

	/**
	 * Function Name: getPlayer1
	 * Description: returns player 1.
	 * 
	 * @param void.
	 * @return: player 1 - the first player.
	 */
	Player getPlayer1();

	/**
	 * Function Name: getPlayer2
	 * Description: returns player 2.
	 * 
	 * @param void.
	 * @return: player 2 - the second player.
	 */
	Player getPlayer2();

	/**
	 * Function Name: getGameBoard
	 * Description: returns the game board .
	 * 
	 * @param void.
	 * @return: game board - the board the game is player on.
	 */
	Board getGameBoard();

	/**
	 * Function Name: getColumnSize
	 * Description: returns the game board column size.
	 * 
	 * @param void.
	 * @return: column size - the game board column size.
	 */
	int getColumnSize();

	/**
	 * Function Name: getRowSize
	 * Description: returns the game board row size.
	 * 
	 * @param void.
	 * @return: row size - the game board row size.
	 */
	int getRowSize();

	/**
	 * Function Name: getGameRules
	 * Description: returns the game rules.
	 * 
	 * @param void.
	 * @return: game rules- the rules dictating which moves are
	 * valid and the game over terms.
	 */
	GameRules getGameRules();

	/**
	 * Function Name: gameOver
	 * Description: invoked when the game over terms are met,prints winner.
	 * 
	 * @param void.
	 * @return: void.
	 */
	void gameOver();
}
