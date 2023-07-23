/*
 * Counter.h
 *
 */

#ifndef COUNTER_H_
#define COUNTER_H_
#include <iostream>
#include "GamePieceSymbol.h"

class Counter
{
	private:
		int player_1_score_;
		int player_2_score_;
		int empty_cells_;
		/**
		 * Function Name: setPlayer1Score
		 * Description: sets player 1 score to given score.
		 * Parameters: player_1_score -the player 1 score.
		 * Return Value: void
		 */
		void setPlayer1Score(const int &player_1_score);

		/**
		 * Function Name: setPlayer2Score
		 * Description: sets player 2 score to given score.
		 * Parameters: player_2_score -the player 2 score.
		 * Return Value: void.
		 */
		void setPlayer2Score(const int &player_2_score);

	public:
		/**
		 * Function Name: Counter
		 * Description: counter constructor - keeps track of game scores.
		 * Parameters: void.
		 * Return Value: void.
		 */
		Counter();

		/**
		 * Function Name: addToPlayer
		 * Description: adds or remove from players scores based on given parameters.
		 * Parameters:symbol_before -the symbol being changed,which player loses score.
		 * Parameters:change_to_symbol-the symbol being changed to,
		 * which player gains score.
		 *
		 * Return Value: void
		 */
		void addToPlayer(const int &symbol_before, const int &change_to_symbol);

		/**
		 * Function Name: setEmptyCells
		 * Description: sets the number of empty cells in game board.
		 * Parameters: empty_cells - the number of empty cells in game board.
		 * Return Value: void.
		 */
		void setEmptyCells(const int &empty_cells);

		/**
		 * Function Name: getEmptyCells
		 * Description: returns the number of empty cells in game board.
		 * Parameters: void.
		 * Return Value:empty_cells - the number of empty cells in game board.
		 */
		int getEmptyCells() const;

		/**
		 * Function Name: getPlayer1Score
		 * Description: returns player 1 current score.
		 * Parameters: void.
		 * Return Value:player_1_score_ - player 1 current score.
		 */
		int getPlayer1Score() const;

		/**
		 * Function Name: getPlayer2Score
		 * Description: returns player 2 current score.
		 * Parameters: void.
		 * Return Value:player_2_score_ - player 2 current score.
		 */
		int getPlayer2Score() const;
};

#endif /* COUNTER_H_ */
