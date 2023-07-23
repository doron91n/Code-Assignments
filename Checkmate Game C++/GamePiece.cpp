/*
 * GamePiece.cpp
 *
 */

#include "GamePiece.h"
using namespace std;

GamePiece::GamePiece() :
		piece_row_(-1), piece_column_(-1), piece_location_point_(Point(-1, -1)), piece_symbol_(
				empty)
{
}

GamePiece::GamePiece(const int &row, const int &column, const int &symbol) :
		piece_row_(row), piece_column_(column), piece_location_point_(
				Point(-1, -1)), piece_symbol_(symbol)
{

	setPieceLocationPoint(Point(row, column));
}

GamePiece::GamePiece(const Point &location_p, const int &symbol) :
		piece_row_(-1), piece_column_(-1), piece_location_point_(Point(-1, -1)), piece_symbol_(
				empty)
{
	GamePiece(location_p.getRow(), location_p.getColumn(), symbol);
}

int GamePiece::getGamePieceRow() const
{
	return (piece_row_);
}

void GamePiece::setGamePieceRow(const int &row)
{
	this->piece_row_ = row;
}

int GamePiece::getGamePieceColumn() const
{
	return (piece_column_);
}

void GamePiece::setGamePieceColumn(const int &column)
{
	this->piece_column_ = column;
}

Point GamePiece::getPieceLocationPoint() const
{
	return (piece_location_point_);
}

void GamePiece::setPieceLocationPoint(const Point &piece_location_point)
{
	this->piece_location_point_ = piece_location_point;
	setGamePieceRow(piece_location_point.getRow());
	setGamePieceColumn(piece_location_point.getColumn());
}

int GamePiece::getGamePieceSymbol() const
{
	return (piece_symbol_);
}

void GamePiece::setGamePieceSymbol(const int &symbol)
{
	this->piece_symbol_ = symbol;
}

const std::string GamePiece::gamePieceToString()
{
	std::string sp = getPieceLocationPoint().pointToString();
	std::string s = "game piece at point: " + sp + "  is : "
			+ (char) getGamePieceSymbol();
	return (s);
}

