/*
 * Point.cpp
 *
 *  Created on: Oct 31, 2017
 *      Author: Doron Norani
 *      ID:305419020
 */

#include "Point.h"
using namespace std;

Point::Point(const int &row, const int &column) :
		row_(-1), column_(-1)
{
	setRow(row);
	setColumn(column);
}

int Point::getColumn() const
{
	return (column_);
}

void Point::setColumn(const int &column)
{
	this->column_ = column;
}

int Point::getRow() const
{
	return (row_);
}

void Point::setRow(const int &row)
{
	this->row_ = row;
}

std::string Point::pointToString()
{
	std::stringstream string_stream;
	string_stream << "(" << getRow() << "," << getColumn() << ")";
	std::string s = string_stream.str();
	return (s);
}

bool Point::operator ==(const Point& other) const
{
	if ((other.getColumn() == this->getColumn())
			&& (other.getRow() == this->getRow()))
	{
		return (true);
	}
	return (false);
}

bool Point::operator !=(const Point& other) const
{
	if ((other.getColumn() != this->getColumn())
			|| (other.getRow() != this->getRow()))
	{
		return (true);
	}
	return (false);
}
