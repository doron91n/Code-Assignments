/*
 * ConsolGame.cpp
 *
 */

#include "ConsolGame.h"
using namespace std;

ConsolGame::ConsolGame(const int &row, const int &column) :
		Game(row, column), row_size_(row), column_size_(column), game_board_(
				new Board(row, column)), player_1_(new LocalPlayer(player_1)), player_2_(
				new LocalPlayer(player_2)), rules_(new BasicGameRules())
{
	getPlayer1()->setPlayerTurn(true);
	bool game_over = false;
	const Counter * score_counter = getGameBoard()->getScoreCounter();
	//plays one turn untill game over conditions are met.
	do
	{
		playOneTurn();
		game_over = getGameRules()->isGameOver(score_counter,
				getPlayer1()->isPlayerTurn(), getPlayer2()->isPlayerTurn());
	} while (!game_over);
	gameOver();
}

void ConsolGame::gameOver()
{
	cout << endl << endl << "********   Game Over   ********" << endl << endl;
	char winner_symbol = getGameRules()->getWinnerSymbol();
	int winner_score = getGameRules()->getWinnerScore();
	if ((winner_symbol == empty) && (winner_score == -10))
	{
		cout << "        It`s a Tie!! there are no winners!!!" << endl << endl;
	}
	else
	{
		cout << "        The Winner is Player : " << winner_symbol
				<< "  with a score of: " << winner_score << endl << endl;
	}
}

Point ConsolGame::getUserInput()
{
	int row = -1;
	int column = -1;
	int i = 1;
	//gets the user input for next player move.
	do
	{
		cout << "choose a move (row,column) to play: [ input example: point "
				"(1,2) will be: 1 2 ]" << endl;
		cin >> row >> column;
		if (!cin.fail())
		{
			i = 0;
		}
		else
		{
			cout << "Error: input not in format - only 2 numbers are "
					"allowed seperated by space." << endl;
			row = column = -1;
		}
		cin.clear();
		cin.ignore(10000, '\n');
	} while (i == 1);
	return (Point(row, column));
}

int ConsolGame::getValidMoveIndex(
		std::vector<std::vector<GamePiece> >& player_moves)
{
	int end = 1;
	int move_index = -1;
	std::size_t player_moves_size = player_moves.size();
	do
	{
		//get the user input (next player move) and check if its a valid move.
		Point p = getUserInput();
		for (std::size_t i = 0; i < player_moves_size; i++)
		{
			Point p1 = player_moves[i].back().getPieceLocationPoint();
			if (p1 == p)
			{
				end = 0;
				move_index = i;
			}
		}
		if (end == 1)
		{
			cout << "Error: entered invalid move, try again." << endl;
		}
	} while (end == 1);
	return (move_index);
}

void ConsolGame::playOneTurn()
{
	GameRules *rules = getGameRules();
	Player* player;
	Player* opponent;
	Player* p1 = getPlayer1();
	Player* p2 = getPlayer2();
	if (p1->isPlayerTurn() && !p2->isPlayerTurn())
	{
		player = p1;
		opponent = p2;

	}
	if (!p1->isPlayerTurn() && p2->isPlayerTurn())
	{
		opponent = p1;
		player = p2;
	}
	char player_symbol = player->getPlayerSymbol();
	std::vector<std::vector<GamePiece> > player_moves =
			getGameBoard()->getPlayerPossibleMoves(player, rules);
	if (!player_moves.empty())
	{
		cout << "Player " << player_symbol << ": it's your turn." << endl;
		cout << "your possible moves are: ";
		std::size_t player_moves_size = player_moves.size();
		//print valid player moves
		for (std::size_t i = 0; i < player_moves_size; i++)
		{
			std::vector<GamePiece> v = player_moves[i];
			GamePiece g = v.back();
			cout << g.getPieceLocationPoint().pointToString();
			if (i < player_moves.size() - 1)
			{
				cout << ",";
			}
		}
		cout << endl;
		int move_index = getValidMoveIndex(player_moves);
		getGameBoard()->flipAllPiecesInRange(player_moves, move_index);
		opponent->setPlayerTurn(true);
	}
	else
	{
		cout << "no possible moves found for player "
				<< (char) player->getPlayerSymbol() << endl;
		//if the player has no more moves check if his opponent has moves.
		std::vector<std::vector<GamePiece> > opponent_moves =
				getGameBoard()->getPlayerPossibleMoves(opponent, rules);
		if (!opponent_moves.empty())
		{
			opponent->setPlayerTurn(true);
		}
		else
		{
			cout << "no possible moves found for player "
					<< (char) opponent->getPlayerSymbol() << endl;
		}
	}
	player->setPlayerTurn(false);
}

GameRules*& ConsolGame::getGameRules()
{
	return (this->rules_);
}

const int ConsolGame::getColumnSize() const
{
	return (this->column_size_);
}

Board*& ConsolGame::getGameBoard()
{
	return (this->game_board_);
}

const int ConsolGame::getRowSize() const
{
	return (this->row_size_);
}

Player*& ConsolGame::getPlayer1()
{
	return (this->player_1_);
}

Player*& ConsolGame::getPlayer2()
{
	return (this->player_2_);
}

ConsolGame::~ConsolGame()
{
	delete game_board_;
	delete player_1_;
	delete player_2_;
	delete rules_;
}
