/*
 * Player.h
 *
 *  Created on: Nov 13, 2017
 *      Author: Doron Norani
 *      ID:305419020
 */

#ifndef PLAYER_H_
#define PLAYER_H_
#include "GamePiece.h"
#include "Counter.h"
class Player
{
	private:
		/**
		 *Function Name: setPlayerSymbol
		 *Description: sets the player symbol to given symbol.
		 *Parameters: player_symbol- the symbol to set as player symbol.
		 *Return Value: void
		 */
		virtual void setPlayerSymbol(const int &player_symbol)=0;

		/**
		 *Function Name: setOpponentSymbol
		 *Description: sets the opponent symbol to given symbol.
		 *Parameters: opponent_symbol- the symbol to set as opponent symbol.
		 *Return Value: void
		 */
		virtual void setOpponentSymbol(const int &opponent_symbol)=0;

	public:

		/**
		 *Function Name: Player
		 *Description: construct player with given symbol.
		 *Parameters: ymbol- the symbol to set as player symbol.
		 *Return Value: void
		 */
		Player(const int &symbol)
		{
		}
		;

		/**
		 *Function Name: getPlayerSymbol
		 *Description: returns the player symbol .
		 *Parameters: void
		 *Return Value: player_symbol- the symbol representing the player.
		 */
		virtual const int getPlayerSymbol() const=0;

		/**
		 *Function Name: getOpponentSymbol
		 *Description: returns the opponent symbol .
		 *Parameters: void
		 *Return Value: opponent_symbol- the symbol representing the opponent.
		 */
		virtual const int getOpponentSymbol() const=0;

		/**
		 *Function Name: isPlayerTurn
		 *Description: returns true if its the player turn.
		 *Parameters: void.
		 *Return Value: returns true if its the player turn.
		 */
		virtual bool isPlayerTurn() const=0;

		/**
		 *Function Name: setPlayerTurn
		 *Description: sets the player turn to given value(true/false).
		 *Parameters: player_turn - the value(true/false) to be set.
		 *Return Value: void.
		 */
		virtual void setPlayerTurn(const bool &player_turn)=0;

		/**
		 *Function Name: ~player
		 *Description: destructor - deletes all created elements.
		 *Parameters: void.
		 *Return Value: void
		 */
		virtual ~Player()
		{
		}
		;
};

#endif /* PLAYER_H_ */
