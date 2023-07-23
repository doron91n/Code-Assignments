/*
 * GameRules.h
 *
 */

#ifndef GAMERULES_H_
#define GAMERULES_H_
#include <iostream>
#include "GamePiece.h"
#include "Counter.h"
class GameRules
{
	private:
		/**
		 *Function Name: setWinnerScore
		 *Description: sets the Game winner score to given score.
		 *Parameters: player_score - the player score to set as winner score.
		 *Return Value: void
		 */
		virtual void setWinnerScore(const int & player_score)=0;

		/**
		 *Function Name: setWinnerSymbol
		 *Description: sets the Game winner symbol to given symbol.
		 *Parameters: player_symbol - the player symbol to set as winner symbol.
		 *Return Value: void
		 */
		virtual void setWinnerSymbol(const int & player_symbol)=0;

	public:

		/**
		 *Function Name: GameRules
		 *Description: constructor.
		 *Parameters: void.
		 *Return Value: void
		 */
		GameRules()
		{
		}
		;

		/**
		 *Function Name: GameRules
		 *Description: constructor.
		 *Parameters: rule_description - a short description of the game rules.
		 *Return Value: void
		 */
		GameRules(std::string &rule_description)
		{
		}
		;

		/**
		 *Function Name: getRuleDescription
		 *Description:returns a short description of the game rules.
		 *Parameters:void.
		 *Return Value:rule_description - a short description of the game rules.
		 */
		virtual const std::string getRuleDescription()=0;

		/**
		 *Function Name: checkValidMove
		 *Description: checks each move if its allowed(valid).
		 *Parameters: current_piece - the symbol of the piece being checked.
		 *Parameters: player_symbol - the current player game piece symbol .
		 *Parameters: opponent_encounterd - true if encounterd opponent on the strike path.
		 *Parameters: is_Board_Edge_piece - true if the current piece is at the
		 * edge of the board.
		 *Return Value: true if the move on the current piece is possible(valid).
		 */
		virtual bool checkValidMove(const char & current_piece,
				const char &player_symbol, bool opponent_encounterd,
				const bool & is_Board_Edge_piece)=0;

		/**
		 *Function Name: isGameOver
		 *Description: checks the "game over" conditions.
		 *Parameters: score_counter - the game score counter, keeps current scores.
		 *Parameters: player1_turn - true if its player1 turn false otherwise.
		 *Parameters: player2_turn - true if its player2 turn false otherwise.
		 *Return Value: true if the game over conditions are met.
		 */
		virtual bool isGameOver(const Counter*& score_counter,
				const bool &player1_turn, const bool &player2_turn)=0;

		/**
		 *Function Name: getWinnerSymbol
		 *Description: returns the symbol of the player with the higest score.
		 *Parameters: void
		 *Return Value: winner symbol - the symbol of the current game winner.
		 */
		virtual const int getWinnerSymbol() const=0;

		/**
		 *Function Name: getWinnerScore
		 *Description: returns the score of the player with the higest score.
		 *Parameters: void
		 *Return Value: winner score - the score of the current game winner.
		 */
		virtual const int getWinnerScore() const=0;

		/**
		 *Function Name: ~GameRules
		 *Description: destructor - deletes all created elemnts.
		 *Parameters:void.
		 *Return Value: void
		 */
		virtual ~GameRules()
		{
		}
		;
};

#endif /* GAMERULES_H_ */
