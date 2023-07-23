/*
 * BasicGameRules.h
 *
 */

#ifndef BASICGAMERULES_H_
#define BASICGAMERULES_H_

#include "GameRules.h"
class BasicGameRules: public GameRules
{
	private:
		int winner_score_;
		int winner_symbol_;
		std::string rule_description_;
		void setWinnerScore(const int & player_score);
		void setWinnerSymbol(const int & player_symbol);

	public:
		BasicGameRules();
		BasicGameRules(std::string &rule_description);
		const std::string getRuleDescription();
		bool checkValidMove(const char & current_piece,
				const char &player_symbol, bool opponent_encounterd,
				const bool & is_Board_Edge_piece);
		bool isGameOver(const Counter*& score_counter, const bool &player1_turn,
				const bool &player2_turn);
		const int getWinnerSymbol() const;
		const int getWinnerScore() const;
		~ BasicGameRules();
};

#endif /* BASICGAMERULES_H_ */
