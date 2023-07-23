/*
 * Point.h
 *
 *  Created on: Oct 31, 2017
 *      Author: Doron Norani
 *      ID:305419020
 */

#ifndef POINT_H_
#define POINT_H_
#include <iostream>
#include <sstream>
class Point
{
	private:
		int row_;
		int column_;
		/**
		 *Function Name: setColumn.
		 *Description: sets the point column (x) to given parameter.
		 *Parameters: int column - the point column (x) parameter.
		 *Return Value: void.
		 */
		void setColumn(const int &column);

		/**
		 *Function Name: setRow.
		 *Description: sets the point row (y) to given parameter.
		 *Parameters: int row - the point row (y) parameter.
		 *Return Value: void.
		 */
		void setRow(const int &row);

	public:
		/**
		 *Function Name: Point.
		 *Description: construct a point at (row,column).
		 *Parameters: int row - the point row (y) parameter.
		 *Parameters: int column - the point column (x) parameter.
		 *Return Value: void.
		 */
		Point(const int &row, const int &column);

		/**
		 *Function getColumn.
		 *Description: returns the point column parameter (x).
		 *Parameters: void.
		 *Return Value: int column - the point column parameter (x).
		 */
		int getColumn() const;

		/**
		 *Function getRow.
		 *Description: returns the point row parameter (y).
		 *Parameters: void.
		 *Return Value: int row - the point row parameter (y).
		 */
		int getRow() const;

		/**
		 *Function Name: pointToString
		 *Description: returns a string representing the point.
		 *Parameters: void
		 *Return Value: string representing the point.
		 */
		std::string pointToString();

		/**
		 *Function Name: operator ==
		 *Description: operator == to compare between 2 points.
		 *Parameters: other - the point being compared to this point.
		 *Return Value: true if both points are equal.
		 */
		bool operator ==(const Point &other) const;

		/**
		 *Function Name: operator !=
		 *Description: operator != to compare between 2 points.
		 *Parameters: other - the point being compared to this point.
		 *Return Value: true if both points are not equal.
		 */
		bool operator !=(const Point &other) const;
};

#endif /* POINT_H_ */
