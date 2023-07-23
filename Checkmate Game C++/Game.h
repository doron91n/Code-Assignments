/*
 * Game.h - game interface.
 *
 */

#ifndef GAME_H_
#define GAME_H_
#include <iostream>
#include "Board.h"
class Game
{
	private:
		/**
		 * Function Name: getUserInput
		 * Description: asks for user input - player next move, 2 numbers.
		 * Parameters: void.
		 * Return Value:point representing player next move.
		 */
		virtual Point getUserInput()=0;

		/**
		 * Function Name: getValidMoveIndex
		 * Description: checks if the move the user entered is a valid player move.
		 * Parameters:player_moves-a vector containing all strike_vector, each
		 * strike_vector contains the game pieces to be fliped in the strike path.
		 * Return Value:move_index - the index to chosen move is player moves.
		 */
		virtual int getValidMoveIndex(
				std::vector<std::vector<GamePiece> >& player_moves)=0;

		/**
		 * Function Name: playOneTurn
		 * Description: plays one player turn in the game.
		 * Parameters:void.
		 * Return Value:void.
		 */
		virtual void playOneTurn()=0;

		/**
		 * Function Name: getPlayer1
		 * Description: returns player 1.
		 * Parameters:void.
		 * Return Value:player 1 - the first player.
		 */
		virtual Player*& getPlayer1()=0;

		/**
		 * Function Name: getPlayer2
		 * Description: returns player 2.
		 * Parameters:void.
		 * Return Value:player 2 - the second player.
		 */
		virtual Player*& getPlayer2()=0;

		/**
		 * Function Name: getGameBoard
		 * Description: returns the game board .
		 * Parameters:void.
		 * Return Value:game board - the board the game is player on.
		 */
		virtual Board*& getGameBoard()=0;

		/**
		 * Function Name: getColumnSize
		 * Description: returns the game board column size.
		 * Parameters:void.
		 * Return Value:column size - the game board column size.
		 */
		virtual const int getColumnSize() const=0;

		/**
		 * Function Name: getRowSize
		 * Description: returns the game board row size.
		 * Parameters:void.
		 * Return Value:row size - the game board row size.
		 */
		virtual const int getRowSize() const=0;

		/**
		 * Function Name: getGameRules
		 * Description: returns the game rules.
		 * Parameters:void.
		 * Return Value:game rules- the rules dictating which moves are valid
		 *  and the game over terms.
		 */
		virtual GameRules*& getGameRules()=0;

		/**
		 * Function Name: gameOver
		 * Description: invoked when the game over terms are met,prints winner.
		 * Parameters:void.
		 * Return Value:void.
		 */
		virtual void gameOver()=0;

	public:
		/**
		 * Function Name: Game
		 * Description: Game constructor.
		 * Parameters:row - the number of rows on the game board.
		 * Parameters:column - the number of columns on the game board.
		 * Return Value:void.
		 */
		Game(const int &row, const int &column)
		{
		}
		;

		/**
		 * Function Name: ~Game
		 * Description: Game destructor - deletes all created game elments.
		 * Parameters:void.
		 * Return Value:void.
		 */
		virtual ~Game()
		{
		}
		;
};

#endif /* GAME_H_ */
