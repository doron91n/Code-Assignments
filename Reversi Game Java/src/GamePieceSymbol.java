
/**
 * GamePieceSymbol enum - represent the pieces symbols.
 *
 */
public enum GamePieceSymbol {
	PLAYER_1('X'), PLAYER_2('O'), EMPTY(' ');
	private char symbol_as_char;
	private int symbol_as_int;

	/**
	 * Function Name: GamePieceSymbol
	 * Description: constructor.
	 * 
	 * @param name - the char symbol.
	 * @return: void
	 */
	private GamePieceSymbol(char name) {
		this.symbol_as_char = name;
		this.symbol_as_int = Character.valueOf(name);
	}

	/**
	 * Function Name: getSymbolChar
	 * Description: returns the symbol as char.
	 * 
	 * @param void.
	 * @return: symbol_as_char - the symbol as char.
	 */
	public char getSymbolChar() {
		return this.symbol_as_char;
	}

	/**
	 * Function Name: getSymbolValue
	 * Description: returns the symbol as int.
	 * 
	 * @param void.
	 * @return: symbol_as_int - the symbol as int.
	 */
	public int getSymbolValue() {
		return this.symbol_as_int;
	}
}
