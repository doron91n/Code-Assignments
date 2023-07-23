/*
 * BasicGameRules.cpp
 *
 */

#include "BasicGameRules.h"

using namespace std;

BasicGameRules::BasicGameRules() :
		winner_score_(-1), winner_symbol_(empty), rule_description_(
				"Basic game rules: "
						"1)player can only do moves if there is a empty cell at the end of his strike direction"
						"2)cant finish a player strike if it has the player pieces in it."
						"3)invalid move - if the piece is at the edge of board and isnt empty."
						"4)invalid move - if got to empty cell without encountering opponent.")
{
}
BasicGameRules::BasicGameRules(std::string &rule_description) :
		winner_score_(-1), winner_symbol_(empty), GameRules(rule_description)
{
}

const std::string BasicGameRules::getRuleDescription()
{
	return (rule_description_);
}

bool BasicGameRules::checkValidMove(const char & current_piece,
		const char &player_symbol, bool opponent_encounterd,
		const bool & is_Board_Edge_piece)
{
	bool val = true;
	//cant finish a player strike if it has the player pieces in it.
	if (current_piece == player_symbol)
	{
		val = false;
	}
	// invalid move - if the piece is at the edge of board and isnt empty.
	if (current_piece != empty && is_Board_Edge_piece)
	{
		val = false;
	}
	// got to empty cell without encountering opponent.
	if (!opponent_encounterd && (current_piece == empty))
	{
		val = false;
	}
	//valid move - encounterd opponent and foud empty cell.
	if (opponent_encounterd && (current_piece == empty))
	{
		val = true;
	}
	return (val);
}
void BasicGameRules::setWinnerSymbol(const int & player_symbol)
{
	this->winner_symbol_ = player_symbol;
}
const int BasicGameRules::getWinnerSymbol() const
{
	return (this->winner_symbol_);
}
void BasicGameRules::setWinnerScore(const int & player_score)
{
	this->winner_score_ = player_score;
}
const int BasicGameRules::getWinnerScore() const
{
	return (this->winner_score_);
}
bool BasicGameRules::isGameOver(const Counter*& score_counter,
		const bool &player1_turn, const bool &player2_turn)
{
	bool val = false;
	int empty_cell = score_counter->getEmptyCells();
	int score_1 = score_counter->getPlayer1Score();
	int score_2 = score_counter->getPlayer2Score();
//none of the players turn - no more moves in game.
	if (!player1_turn && !player2_turn)
	{
		val = true;
	}
//no more empty cells - board is full.
	if (empty_cell == 0)
	{
		val = true;
	}
	if (score_1 > score_2)
	{
		setWinnerSymbol(player_1);
		setWinnerScore(score_1);
	}
	if (score_2 > score_1)
	{
		setWinnerSymbol(player_2);
		setWinnerScore(score_2);
	}
	if (score_1 == score_2)
	{
		setWinnerSymbol(empty);
		setWinnerScore(-10);
	}
	return (val);
}
BasicGameRules::~BasicGameRules()
{
}
