/*
 * LocalPlayer.cpp
 *
 */

#include "LocalPlayer.h"
using namespace std;

LocalPlayer::LocalPlayer(const int &symbol) :
		Player(symbol), player_symbol_(empty), opponent_symbol_(empty), player_turn_(
				false)
{
	setPlayerSymbol(symbol);
	if (symbol == player_1)
	{
		setOpponentSymbol(player_2);
	}
	else if (symbol == player_2)
	{
		setOpponentSymbol(player_1);
	}
}

const int LocalPlayer::getPlayerSymbol() const
{
	return (player_symbol_);
}

void LocalPlayer::setPlayerSymbol(const int &player_symbol)
{
	player_symbol_ = player_symbol;
}

bool LocalPlayer::isPlayerTurn() const
{
	return (player_turn_);
}

void LocalPlayer::setPlayerTurn(const bool &player_turn)
{
	player_turn_ = player_turn;
}

const int LocalPlayer::getOpponentSymbol() const
{
	return (opponent_symbol_);
}

void LocalPlayer::setOpponentSymbol(const int &opponent_symbol)
{
	opponent_symbol_ = opponent_symbol;
}

LocalPlayer::~LocalPlayer()
{
}
