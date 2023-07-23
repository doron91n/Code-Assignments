/**
 * BasicGameRules class - a set of game rules for the game.
 *
 */

public class BasicGameRules implements GameRules {

	private int winner_score_ = -1;
	private char winner_symbol_ = GamePieceSymbol.EMPTY.getSymbolChar();
	private String rule_description_ = "Basic game rules:\n"
			+ "1) player can only do moves if there is a empty cell at the end of his strike direction.\n"
			+ "2) cant finish a player strike if it has the player pieces in it.\n"
			+ "3) invalid move - if the piece is at the edge of board and isnt empty.\n"
			+ "4) invalid move - if got to empty cell without encountering opponent.\n";

	/**
	 * Function Name: BasicGameRules
	 * Description: constructor.
	 * 
	 * @param rule_description - a short description of the game rules.
	 * @return: void
	 */
	public BasicGameRules(String rule_description) {
		this.rule_description_ = rule_description;
	}

	/**
	 * Function Name: BasicGameRules
	 * Description: constructor.
	 * 
	 * @param void.
	 * @return: void
	 */
	public BasicGameRules() {
	}

	public String getRuleDescription() {
		return (this.rule_description_);
	}

	public boolean checkValidMove(char current_piece, char player_symbol, boolean opponent_encounterd,
			boolean is_Board_Edge_piece) {
		boolean val = true;
		// Can't finish a player strike if it has the player pieces in it.
		if (current_piece == player_symbol) {
			val = false;
		}
		// invalid move - if the piece is at the edge of board and isn't empty.
		if (current_piece != GamePieceSymbol.EMPTY.getSymbolChar() && is_Board_Edge_piece) {
			val = false;
		}
		// got to empty cell without encountering opponent.
		if (!opponent_encounterd && (current_piece == GamePieceSymbol.EMPTY.getSymbolChar())) {
			val = false;
		}
		// valid move - encountered opponent and found empty cell.
		if (opponent_encounterd && (current_piece == GamePieceSymbol.EMPTY.getSymbolChar())) {
			val = true;
		}
		return (val);
	}

	public void setWinnerSymbol(char player_symbol) {
		this.winner_symbol_ = player_symbol;
	}

	public int getWinnerSymbol() {
		return (this.winner_symbol_);
	}

	public void setWinnerScore(int player_score) {
		this.winner_score_ = player_score;
	}

	public int getWinnerScore() {
		return (this.winner_score_);
	}

	public boolean isGameOver(Counter score_counter, boolean player1_turn, boolean player2_turn) {
		boolean val = false;
		int empty_cell = score_counter.getEmptyCells();
		int score_1 = score_counter.getPlayer1Score();
		int score_2 = score_counter.getPlayer2Score();
		// none of the players turn - no more moves in game.
		if (!player1_turn && !player2_turn) {
			val = true;
		}
		// no more empty cells - board is full.
		if (empty_cell == 0) {
			val = true;
		}
		if (score_1 > score_2) {
			setWinnerSymbol(GamePieceSymbol.PLAYER_1.getSymbolChar());
			setWinnerScore(score_1);
		}
		if (score_2 > score_1) {
			setWinnerSymbol(GamePieceSymbol.PLAYER_2.getSymbolChar());
			setWinnerScore(score_2);
		}
		if (score_1 == score_2) {
			setWinnerSymbol(GamePieceSymbol.EMPTY.getSymbolChar());
			setWinnerScore(-10);
		}
		return (val);
	}
}
