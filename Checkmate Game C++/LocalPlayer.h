/*
 * LocalPlayer.h
 *
 */

#ifndef LOCALPLAYER_H_
#define LOCALPLAYER_H_
#include "Player.h"
#include <iostream>
class LocalPlayer: public Player
{
	private:
		int player_symbol_;
		int opponent_symbol_;
		bool player_turn_;
		void setPlayerSymbol(const int &player_symbol);
		void setOpponentSymbol(const int &opponent_symbol);

	public:
		LocalPlayer(const int &symbol);
		const int getPlayerSymbol() const;
		const int getOpponentSymbol() const;
		bool isPlayerTurn() const;
		void setPlayerTurn(const bool &player_turn);
		~LocalPlayer();
};

#endif /* LOCALPLAYER_H_ */
