/**
 * LocalPlayer class - the game local player.
 *
 */
public class LocalPlayer implements Player {

	private int player_symbol_ = GamePieceSymbol.EMPTY.getSymbolValue(); // Default value
	private int opponent_symbol_ = GamePieceSymbol.EMPTY.getSymbolValue(); // Default value
	private Boolean player_turn_ = false; // Default value

	/**
	 * Function Name: LocalPlayer
	 * Description: construct player with given symbol.
	 * 
	 * @param symbol- the symbol to set as player symbol.
	 * @return: void
	 */
	public LocalPlayer(int symbol)

	{
		setPlayerSymbol(symbol);
		if (symbol == GamePieceSymbol.PLAYER_1.getSymbolValue()) {
			setOpponentSymbol(GamePieceSymbol.PLAYER_2.getSymbolValue());
		} else if (symbol == GamePieceSymbol.PLAYER_2.getSymbolValue()) {
			setOpponentSymbol(GamePieceSymbol.PLAYER_1.getSymbolValue());
		}
	}

	public int getPlayerSymbol() {
		return (this.player_symbol_);
	}

	public void setPlayerSymbol(int player_symbol) {
		this.player_symbol_ = player_symbol;
	}

	public boolean isPlayerTurn() {
		return (this.player_turn_);
	}

	public void setPlayerTurn(boolean player_turn) {
		this.player_turn_ = player_turn;
	}

	public int getOpponentSymbol() {
		return (this.opponent_symbol_);
	}

	public void setOpponentSymbol(int opponent_symbol) {
		this.opponent_symbol_ = opponent_symbol;
	}
}
