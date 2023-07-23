/**
 * Point class - a point .
 *
 */

public class Point {
	private int column_ = 0;
	private int row_ = 0;

	/**
	 * Function Name: Point.
	 * Description: construct a point at (row,column).
	 * 
	 * @param int row - the point row (y) parameter.
	 * @param int column - the point column (x) parameter.
	 * @return: void.
	 */
	public Point(int row, int column) {
		setRow(row);
		setColumn(column);
	}

	/**
	 * Function getColumn.
	 * Description: returns the point column parameter (x).
	 * 
	 * @param void.
	 * @return: int column - the point column parameter (x).
	 */
	public int getColumn() {
		return (this.column_);
	}

	/**
	 * Function Name: setColumn.
	 * Description: sets the point column (x) to given parameter.
	 * 
	 * @param int column - the point column (x) parameter.
	 * @return: void.
	 */
	public void setColumn(int column) {
		this.column_ = column;
	}

	/**
	 * Function getRow.
	 * Description: returns the point row parameter (y).
	 * 
	 * @param void.
	 * @return: int row - the point row parameter (y).
	 */
	public int getRow() {
		return (this.row_);
	}

	/**
	 * Function Name: setRow.
	 * Description: sets the point row (y) to given parameter.
	 * 
	 * @param int row - the point row (y) parameter.
	 * @return: void.
	 */
	public void setRow(int row) {
		this.row_ = row;
	}

	/**
	 * Function Name: toString
	 * Description: returns a string representing the point.
	 * 
	 * @param void
	 * @return: string representing the point.
	 */
	public String toString() {
		String s = "(" + getRow() + "," + getColumn() + ")";
		return (s);
	}

	/**
	 * Function Name: isEqualPoint
	 * Description: compares this point with given (other) point, return true if
	 * equal false otherwise.
	 * 
	 * @param other - the point being compared to this point.
	 * @return: true if both points are equal, false otherwise.
	 */
	public Boolean isEqualPoint(Point other) {
		if ((other.getColumn() == this.getColumn()) && (other.getRow() == this.getRow())) {
			return (true);
		}
		return (false);
	}

}
