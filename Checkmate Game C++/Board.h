/*
 * Board.h
 *
 */

#ifndef BOARD_H_
#define BOARD_H_
#include "GamePiece.h"
#include "Counter.h"
#include <string>
#include <vector>
#include <iostream>
#include "BasicGameRules.h"
#include "LocalPlayer.h"
/**
 *class Name: Board.
 *Description: The game board class.
 */
class Board
{
	private:
		int row_size_;
		int column_size_;
		GamePiece** game_matrix_;
		Counter * score_counter_;

		/**
		 * Function Name: initializeBoard
		 * Description: creates a 2 dimnsional matrix with the Board given size
		 *  (rows*columns),initialize the board matrix with 4 starting game pieces.
		 * Parameters: void
		 * Return Value: void
		 */
		void initializeBoard();

		/**
		 *Function Name: printBoard
		 *Description: prints the game board in a certain format.
		 *Parameters: void
		 *Return Value: void
		 */
		void printBoard();

		/**
		 *Function Name: printBoardLines
		 *Description: used by printBoard ,prints the separating lines between
		 *             game board rows .
		 *Parameters: void
		 *Return Value: void
		 */
		void printBoardLines();

		/**
		 *Function Name: printVector
		 *Description: prints the given <GamePiece> vector in a certain format.
		 *Parameters: std::vector<GamePiece> v - the vector to print.
		 *Return Value: void
		 */
		void printVector(std::vector<GamePiece> v);

		/**
		 *Function Name: isBoardEdgePiece
		 *
		 *Description: checks if the given game piece is at the edge of the
		 *board at the given direction.
		 *
		 *Parameters:piece - the game piece to check.
		 *
		 *Parameters:direction - the direction to check.
		 *
		 *Return Value: true - if the game piece at the given direction is at
		 *              the edge of the board false otherwise.
		 */
		bool isBoardEdgePiece(const GamePiece &piece, const int &direction);

		/**
		 *Function Name: checkMovesInDirection
		 *
		 *Description: checks for a player game piece at(row,coulmn) if it has
		 *possible moves in the given direction(checking the game rules),
		 *if such moves are found (valid ones) creates a strike_vector
		 *containing all the pieces to be filped in that strike,
		 *this strike_vector is compared with other strike vectors in
		 *the possible_moves vector (contains other strike vectors) if a strike
		 *vector with the same end point is found merge them else add it to
		 *the possible moves vector.
		 *
		 *Parameters:row - the player game piece being checked location row.
		 *Parameters:column -the player game piece being checked location column.
		 *Parameters:direction - the direction to check moves for.
		 *Parameters:possible_moves-a vector containing all strike_vector, each
		 * strike_vector contains the game pieces to be fliped in the strike path.
		 *Parameters:rules -the game rules set for the game - for move/strike validation.
		 *Return Value: void
		 */
		void checkMovesInDirection(const int &row, const int &column,
				const int &direction, const int &opponent,
				std::vector<std::vector<GamePiece> >& possible_moves,
				GameRules *&rules);

		/**
		 *Function Name: checkDuplicateStrikes
		 *
		 *Description: checks the given strike_vector if its last move
		 *(the player chosen move) already exist in the player possible moves
		 *vector,if so returns the index of that strike_vector in possible
		 *moves so they will be merged into one bigger vector
		 *(doing so enables one move to change more then one line) .
		 *
		 *Parameters:possible_moves-a vector containing all strike_vector, each
		 *strike_vector contains the game pieces to be fliped in the strike path.
		 *Parameters:strike_vector - the vector to be checked.
		 *Return Value: strike_index - the index of the strike_vector to be
		 * merged(from possible_moves) with the given strike_vector.
		 *(if -1 is returned - not merging and the strike_vector is added normaly).
		 */
		int checkDuplicateStrikes(
				std::vector<std::vector<GamePiece> >& possible_moves,
				std::vector<GamePiece>& strike_vector);

		/**
		 *Function Name: addGamePiece.
		 *Description: changes the symbol for the game piece at the given
		 *location and updates the game score counter of the change.
		 *
		 *Parameters: int row - the row on the game board to place the game piece.
		 *Parameters: int column - the column on the game board to place the game piece.
		 *Parameters: int symbol - the int representing the game piece symbol.
		 *Return Value: void.
		 */
		void addGamePiece(const int &row, const int &column, const int &symbol);

		/**
		 *Function Name: getColumnSize.
		 *Description: returns the game board matrix column size.
		 *Parameters: void.
		 *Return Value: int column_size - the game board matrix column size.
		 */
		int getColumnSize() const;

		/**
		 *Function Name:getRowSize.
		 *Description: returns the game board matrix row size.
		 *Parameters: void.
		 *Return Value: int row_size - the game board matrix row size.
		 */
		int getRowSize() const;

		/**
		 *Function Name: setColumnSize.
		 *Description: sets the game board matrix column size to given parameter.
		 *Parameters: int columnSize - the game board matrix column size.
		 *Return Value: void.
		 */
		void setColumnSize(const int &columnSize);

		/**
		 *Function Name: setRowSize.
		 *Description: sets the game board matrix row size to given parameter.
		 *Parameters: int rowSize - the game board matrix row size.
		 *Return Value: void.
		 */
		void setRowSize(const int &rowSize);

		/**
		 *Function Name: setGameMatrix.
		 *Description: sets the game board matrix to given matrix.
		 *Parameters: GamePiece** gameMatrix - the game board matrix to set.
		 *Return Value: void.
		 */
		void setGameMatrix(GamePiece** gameMatrix);

	public:
		/**
		 *Function Name: Board.
		 *Description: The game board constructor, creates the game board with the
		 *             given parameters.
		 *Parameters: int row_s - the number of rows in the game board.
		 *Parameters: int column_s - the number of columns in the game board.
		 *Return Value: void.
		 */
		Board(const int &row_s, const int &column_s);

		/**
		 *Function Name: flipAllPiecesInRange.
		 *Description:filpes all the game pieces to the right player symbol at
		 *the strike vector located at move index inside the possible moves vector.
		 *
		 *Parameters:possible_moves-a vector containing all strike_vectors, each
		 *strike_vector contains the game pieces to be fliped in the strike path.
		 *Parameters: strike_index - the index of the strike_vector to flip.
		 *Return Value: void.
		 */
		void flipAllPiecesInRange(
				std::vector<std::vector<GamePiece> >& possible_moves,
				const int &strike_index);

		/**
		 *Function Name: getPlayerPossibleMoves.
		 *Description: checks all the player possible moves for each game piece
		 * he owns , and returns a possible moves vector.
		 *Parameters: player - the player to check moves for.
		 *Parameters: rules - the game rules to check valid moves by.
		 *Return Value: possible_moves - a vector containing all the player
		 * possible moves(that will result at a strike).
		 */
		std::vector<std::vector<GamePiece> > getPlayerPossibleMoves(
				Player* player, GameRules *&rules);

		/**
		 *Function Name: getGameMatrix.
		 *Description: returns the game board matrix.
		 *Parameters: void.
		 *Return Value: GamePiece** matrix - the game board matrix.
		 */
		GamePiece** getGameMatrix();

		/**
		 *Function Name: getScoreCounter.
		 *Description: returns the game score counter.
		 *Parameters: void.
		 *Return Value: score counter - keeps the game board scores.
		 */
		Counter*& getScoreCounter();

		/**
		 * Function Name: ~Board
		 * Description: destructor, deletes and free the game board matrix,
		 *  score counter.
		 * Parameters: void
		 * Return Value: void
		 */
		~Board();
};

#endif /* BOARD_H_ */
