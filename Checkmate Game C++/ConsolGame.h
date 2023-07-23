/*
 * ConsolGame.h
 *
 */

#ifndef CONSOLGAME_H_
#define CONSOLGAME_H_

#include "Game.h"

class ConsolGame: public Game
{
	private:
		const int row_size_;
		const int column_size_;
		Board * game_board_;
		Player *player_1_;
		Player *player_2_;
		GameRules *rules_;
		Point getUserInput();
		int getValidMoveIndex(
				std::vector<std::vector<GamePiece> >& player_moves);
		void playOneTurn();
		Player*& getPlayer1();
		Player*& getPlayer2();
		Board*& getGameBoard();
		const int getColumnSize() const;
		const int getRowSize() const;
		GameRules*& getGameRules();
		void gameOver();

	public:
		ConsolGame(const int &row, const int &column);
		~ConsolGame();
};

#endif /* CONSOLGAME_H_ */
