/*
 * Board.cpp
 *
 */

#include "Board.h"

using namespace std;

enum Direction
{
	NORTH = 0,
	NORTH_EAST = 45,
	EAST = 90,
	SOUTH_EAST = 135,
	SOUTH = 180,
	SOUTH_WEST = 225,
	WEST = 270,
	NORTH_WEST = 315
};
Board::Board(const int &row_s, const int &column_s) :
		row_size_(row_s), column_size_(column_s), score_counter_(new Counter())
{
	initializeBoard();
	printBoard();
}

void Board::initializeBoard()
{
	int row_s = getRowSize();
	int column_s = getColumnSize();
	int empty_cells = 0;
	GamePiece** temp_matrix = new GamePiece*[row_s];
	for (int m = 0; m < row_s; m++)
	{
		temp_matrix[m] = new GamePiece[column_s];
		for (int n = 0; n < column_s; n++)
		{
			temp_matrix[m][n].setPieceLocationPoint(Point(m, n));
			empty_cells++;
		}
	}
	getScoreCounter()->setEmptyCells(empty_cells);
	setGameMatrix(temp_matrix);
	//initialize the first 4 game pieces.
	int x = row_s / 2;
	int y = column_s / 2;
	addGamePiece(x, y - 1, player_1);
	addGamePiece(x - 1, y, player_1);
	addGamePiece(x - 1, y - 1, player_2);
	addGamePiece(x, y, player_2);
}
void Board::printBoardLines()
{
	int column_s = getColumnSize();
	std::string s5 = "-----";
	std::string s3 = "---";
	cout << "-";
	// dynamicly change the number of separting lines based on number of columns.
	for (int i = 0; i < column_s; i++)
	{
		if (i % 2 == 0)
		{
			cout << s5;
		}
		else
		{
			cout << s3;
		}
	}
	if (column_s % 2 == 0)
	{
		cout << "-" << endl;
	}
	else
	{
		cout << endl;
	}
}
void Board::printBoard()
{
	GamePiece** matrix = getGameMatrix();
	int row_s = getRowSize();
	int column_s = getColumnSize();
	cout << "current board:" << endl << endl;
	//print the columns numbers
	for (int i = 0; i < column_s; i++)
	{
		cout << " | " << i;
	}
	cout << " |" << endl;
	//print each row of the matrix
	for (int m = 0; m < row_s; m++)
	{
		printBoardLines();
		cout << m << "|";
		for (int n = 0; n < column_s; n++)
		{
			char current_symbol = matrix[m][n].getGamePieceSymbol();
			cout << " " << current_symbol << " |";
		}
		cout << endl;
	}
	printBoardLines();
	cout << "Current game scores:   | Player X: "
			<< getScoreCounter()->getPlayer1Score() << " | Player O: "
			<< getScoreCounter()->getPlayer2Score() << " | empty cells: "
			<< getScoreCounter()->getEmptyCells() << " |" << endl << endl;
}

int Board::checkDuplicateStrikes(
		std::vector<std::vector<GamePiece> >& possible_moves,
		std::vector<GamePiece>& strike_vector)
{
	int strike_index = -1;
	std::vector<GamePiece> current_vector = possible_moves[0];
	std::size_t moves_size = possible_moves.size();
	std::size_t current_vector_size = current_vector.size();
	std::size_t strike_vector_size = strike_vector.size();
	Point current_strike_point =
			current_vector[current_vector_size - 1].getPieceLocationPoint();
	Point strike_vector_strike_point =
			strike_vector[strike_vector_size - 1].getPieceLocationPoint();
	for (std::size_t i = 0; i < moves_size; i++)
	{
		current_vector_size = possible_moves[i].size();
		current_strike_point =
				possible_moves[i][current_vector_size - 1].getPieceLocationPoint();
		if (current_strike_point == strike_vector_strike_point)
		{
			strike_index = i;
		}
	}
	return (strike_index);
}
void Board::printVector(std::vector<GamePiece> vector)
{
	std::size_t vector_size = vector.size();
	if (vector_size > 0)
	{
		cout << "************ Printing vector<gamePiece> ************" << endl;
		cout << "vector size is: " << vector.size() << endl;
		for (std::size_t i = 0; i < vector_size; i++)
		{
			GamePiece g = vector[i];
			cout << "i is: " << i << " point: "
					<< g.getPieceLocationPoint().pointToString() << endl;
		}
	}
	cout << "********************************************************" << endl;

}
bool Board::isBoardEdgePiece(const GamePiece &piece, const int &direction)
{
	int board_row_s = getRowSize();
	int board_column_s = getColumnSize();
	int piece_row = piece.getGamePieceRow();
	int piece_column = piece.getGamePieceColumn();
	bool val = false;
	switch (direction)
	{
		case NORTH:
			if (piece_row == 0)
			{
				val = true;
			}
			break;
		case NORTH_EAST:
			if ((piece_row == 0) && (piece_column == board_column_s - 1))
			{
				val = true;
			}
			break;
		case EAST:

			if (piece_column == board_column_s - 1)
			{
				val = true;
			}
			break;
		case SOUTH_EAST:
			if ((piece_row == board_row_s - 1)
					&& (piece_column == board_column_s - 1))
			{
				val = true;
			}
			break;
		case SOUTH:
			if (piece_row == board_row_s - 1)
			{
				val = true;
			}
			break;
		case SOUTH_WEST:
			if ((piece_row == board_row_s - 1) && (piece_column == 0))
			{
				val = true;
			}
			break;
		case WEST:
			if (piece_column == 0)
			{
				val = true;
			}
			break;
		case NORTH_WEST:
			if ((piece_row == 0) && (piece_column == 0))
			{
				val = true;
			}
			break;
		default:
			cout << "at direction switch default case" << endl;
			break;
	}
	return (val);
}

void Board::checkMovesInDirection(const int &row, const int &column,
		const int &direction, const int &opponent,
		std::vector<std::vector<GamePiece> >& possible_moves, GameRules *&rules)
{
	GamePiece** matrix = getGameMatrix();
	std::vector<GamePiece> strike_vector;
	int board_row_s = getRowSize();
	int board_column_s = getColumnSize();
	int i = 1;
	int end = 1;
	int m = row;
	int n = column;
	bool valid_move = false;
	bool opponent_encounterd = false;
	char player_symbol = matrix[m][n].getGamePieceSymbol();
	char current_piece;
	do
	{
		//add the first game piece at the strike vector (strike origin piece).
		if (i == 1)
		{
			strike_vector.push_back(matrix[m][n]);
		}
		//direction to check
		switch (direction)
		{
			case NORTH:
				m = row - i;
				n = column;
				break;
			case NORTH_EAST:
				m = row - i;
				n = column + i;
				break;
			case EAST:
				m = row;
				n = column + i;
				break;
			case SOUTH_EAST:
				m = row + i;
				n = column + i;
				break;
			case SOUTH:
				m = row + i;
				n = column;
				break;
			case SOUTH_WEST:
				m = row + i;
				n = column - i;
				break;
			case WEST:
				m = row;
				n = column - i;
				break;
			case NORTH_WEST:
				m = row - i;
				n = column - i;
				break;
			default:
				cout << "at direction switch default case" << endl;
				break;
		}
		//out of bounds.
		if ((n < 0) || (n >= board_column_s) || (m < 0) || (m >= board_row_s))
		{
			strike_vector.clear();
			break;
		}
		current_piece = matrix[m][n].getGamePieceSymbol();
		if (current_piece == opponent)
		{
			opponent_encounterd = true;
		}
		valid_move = rules->checkValidMove(current_piece, player_symbol,
				opponent_encounterd, isBoardEdgePiece(matrix[m][n], direction));
		if (!valid_move)
		{
			strike_vector.clear();
			break;
		}
		if (valid_move)
		{
			strike_vector.push_back(matrix[m][n]);
			//no valid strike vector for less then 3 game pieces.
			if ((current_piece == empty) && (strike_vector.size() > 2))
			{
				if (possible_moves.size() > 0)
				{
					int strike_index = checkDuplicateStrikes(possible_moves,
							strike_vector);
					//merge the vectors if duplicate found.
					if (strike_index > -1)
					{
						std::size_t strike_vector_size = strike_vector.size();
						for (std::size_t j = 0; j < strike_vector_size; j++)
						{
							possible_moves[strike_index].push_back(
									strike_vector[j]);
						}
					}
					else
					{
						possible_moves.push_back(strike_vector);
					}
				}
				else
				{
					//possible moves is empty, add the strike vector.
					possible_moves.push_back(strike_vector);
				}
				break;
			}
		}
		i++;
	} while (end == 1);
}

std::vector<std::vector<GamePiece> > Board::getPlayerPossibleMoves(
		Player * player, GameRules * &rules)
{
	GamePiece** matrix = getGameMatrix();
	int row_s = getRowSize();
	int column_s = getColumnSize();
	int opponent_symbol = player->getOpponentSymbol();
	int player_symbol = player->getPlayerSymbol();
	std::vector<std::vector<GamePiece> > possible_moves;
	//check the whole game board for player valid moves.
	for (int m = 0; m < row_s; m++)
	{
		for (int n = 0; n < column_s; n++)
		{
			int current_piece = matrix[m][n].getGamePieceSymbol();
			//player game piece found,checks all directions for valid moves.
			if (current_piece == player_symbol)
			{
				checkMovesInDirection(m, n, NORTH, opponent_symbol,
						possible_moves, rules);
				checkMovesInDirection(m, n, NORTH_EAST, opponent_symbol,
						possible_moves, rules);
				checkMovesInDirection(m, n, EAST, opponent_symbol,
						possible_moves, rules);
				checkMovesInDirection(m, n, SOUTH_EAST, opponent_symbol,
						possible_moves, rules);
				checkMovesInDirection(m, n, SOUTH, opponent_symbol,
						possible_moves, rules);
				checkMovesInDirection(m, n, SOUTH_WEST, opponent_symbol,
						possible_moves, rules);
				checkMovesInDirection(m, n, WEST, opponent_symbol,
						possible_moves, rules);
				checkMovesInDirection(m, n, NORTH_WEST, opponent_symbol,
						possible_moves, rules);
			}
		}
	}
	return (possible_moves);
}
void Board::flipAllPiecesInRange(
		std::vector<std::vector<GamePiece> >& possible_moves,
		const int &strike_index)
{
	std::vector<GamePiece> moves = possible_moves[strike_index];
	//the player symbol to change all other game pieces int the strike.
	int flipTo = moves[0].getGamePieceSymbol();
	for (std::size_t i = 0; i < moves.size(); i++)
	{
		addGamePiece(moves[i].getGamePieceRow(), moves[i].getGamePieceColumn(),
				flipTo);
	}
	printBoard();
}
int Board::getColumnSize() const
{
	return (column_size_);
}

void Board::setColumnSize(const int &columnSize)
{
	column_size_ = columnSize;
}

int Board::getRowSize() const
{
	return (row_size_);
}

void Board::setRowSize(const int &rowSize)
{
	row_size_ = rowSize;
}

GamePiece * *Board::getGameMatrix()
{
	return (game_matrix_);
}

void Board::setGameMatrix(GamePiece** gameMatrix)
{
	game_matrix_ = gameMatrix;
}
void Board::addGamePiece(const int &row, const int &column, const int &symbol)
{
	GamePiece** matrix = getGameMatrix();
	const int symbol_before = matrix[row][column].getGamePieceSymbol();
	matrix[row][column].setGamePieceSymbol(symbol);
	getScoreCounter()->addToPlayer(symbol_before, symbol);
}

Counter * &Board::getScoreCounter()
{
	return (score_counter_);
}

Board::~Board()
{
	GamePiece** matrix = getGameMatrix();
	int row_s = getRowSize();
	for (int i = row_s - 1; i >= 0; i--)
	{
		delete[] matrix[i];
	}
	delete[] matrix;
	delete score_counter_;
}

