/*
 * GamePiece.h
 *
 */

#ifndef GAMEPIECE_H_
#define GAMEPIECE_H_
#include "Point.h"
#include "GamePieceSymbol.h"
#include <iostream>
#include<string>
/**
 * enum GamePieceSymbol
 {
 player_1 = 'X', player_2 = 'O', empty = ' '
 };
 */
class GamePiece
{
	private:
		int piece_row_;
		int piece_column_;
		Point piece_location_point_;
		int piece_symbol_;

		/**
		 *Function Name: setGamePieceColumn
		 *Description: sets the Game Piece Column to given coulmn.
		 *Parameters: column - the column the game piece is located at.
		 *Return Value: void
		 */
		void setGamePieceColumn(const int &column);

		/**
		 *Function Name: setGamePieceRow
		 *Description: sets the Game Piece row to given row.
		 *Parameters: row - the row the game piece is located at.
		 *Return Value: void
		 */
		void setGamePieceRow(const int &row);

	public:
		/**
		 *Function Name: GamePiece
		 *Description: constructor.
		 *Parameters: void
		 *Return Value: void
		 */
		GamePiece();

		/**
		 *Function Name: GamePiece
		 *Description: constructor.
		 *Parameters: row - the row the game piece is located at.
		 *Parameters: column - the column the game piece is located at.
		 *Parameters: symbol - the game piece symbol.
		 *Return Value: void
		 */
		GamePiece(const int &row, const int &column, const int &symbol);

		/**
		 *Function Name: GamePiece
		 *Description: constructor.
		 *Parameters: location_p - the location point for the game piece (row,column).
		 *Parameters: symbol - the game piece symbol.
		 *Return Value: void
		 */
		GamePiece(const Point &location_p, const int &symbol);

		/**
		 *Function Name: getGamePieceRow
		 *Description: returns the Game Piece row.
		 *Parameters: void
		 *Return Value: row - the row the game piece is located at.
		 */
		int getGamePieceRow() const;

		/**
		 *Function Name: getGamePieceColumn
		 *Description: returns the Game Piece Column .
		 *Parameters:void
		 *Return Value:  column - the column the game piece is located at.
		 */
		int getGamePieceColumn() const;

		/**
		 *Function Name: getPieceLocationPoint
		 *Description: return game piece location point.
		 *Parameters: void
		 *Return Value: location_p - the location point for the game piece (row,column).
		 */
		Point getPieceLocationPoint() const;

		/**
		 *Function Name: setPieceLocationPoint
		 *Description: sets game piece location point to given point.
		 *Parameters:piece location_p - the location point for the game piece(row,column).
		 *Return Value: void
		 */
		void setPieceLocationPoint(const Point &piece_location_point);

		/**
		 *Function Name: getGamePieceSymbol
		 *Description: returns the game piece symbol.
		 *Parameters: void
		 *Return Value: symbol - the game piece symbol.
		 */
		int getGamePieceSymbol() const;

		/**
		 *Function Name: setGamePieceSymbol
		 *Description: sets the game piece symbol to given symbol.
		 *Parameters:symbol - the game piece symbol.
		 *Return Value: void
		 */
		void setGamePieceSymbol(const int &symbol);

		/**
		 *Function Name: gamePieceToString
		 *Description: returns a string representing its symbol and location p.
		 *Parameters: void
		 *Return Value: string representing game piece symbol and location p.
		 */
		const std::string gamePieceToString();

};

#endif /* GAMEPIECE_H_ */
