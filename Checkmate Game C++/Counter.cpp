/*
 * Counter.cpp
 *
 */

#include "Counter.h"
using namespace std;

Counter::Counter() :
		player_1_score_(0), player_2_score_(0), empty_cells_(0)
{
}

void Counter::addToPlayer(const int &symbol_before, const int &change_to_symbol)
{
	//no need to switch if they are the same symbol already.
	if (symbol_before != change_to_symbol)
	{
		if (symbol_before == empty)
		{
			if (change_to_symbol == player_1)
			{
				setPlayer1Score(getPlayer1Score() + 1);
			}
			if (change_to_symbol == player_2)
			{
				setPlayer2Score(getPlayer2Score() + 1);
			}
			setEmptyCells(getEmptyCells() - 1);
		}
		if (symbol_before == player_1)
		{
			//fliping game piece from player 1 to player 2.
			if (change_to_symbol == player_2)
			{
				setPlayer2Score(getPlayer2Score() + 1);
			}
			//fliping game piece from player 1 to empty.
			if (change_to_symbol == empty)
			{
				setEmptyCells(getEmptyCells() + 1);
			}
			setPlayer1Score(getPlayer1Score() - 1);
		}
		if (symbol_before == player_2)
		{
			//fliping game piece from player 2 to player 1.
			if (change_to_symbol == player_1)
			{
				setPlayer1Score(getPlayer1Score() + 1);
			}
			//fliping game piece from player 2 to empty.
			if (change_to_symbol == empty)
			{
				setEmptyCells(getEmptyCells() + 1);
			}
			setPlayer2Score(getPlayer2Score() - 1);
		}
	}
}

int Counter::getEmptyCells() const
{
	return (empty_cells_);
}

void Counter::setEmptyCells(const int &empty_cells)
{
	empty_cells_ = empty_cells;
}

int Counter::getPlayer1Score() const
{
	return (player_1_score_);
}

void Counter::setPlayer1Score(const int &player_1_score)
{
	player_1_score_ = player_1_score;
}

int Counter::getPlayer2Score() const
{
	return (player_2_score_);
}

void Counter::setPlayer2Score(const int &player_2_score)
{
	player_2_score_ = player_2_score;
}

