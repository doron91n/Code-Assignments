
/**
 * Board class - the game board.
 *
 * 
 * 
 */
import java.util.Vector;

public class Board {

    private enum Direction {
        NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
    };

    private int row_size_;
    private int column_size_;
    private Counter score_counter_ = new Counter();
    private GamePiece[][] game_matrix_;

    /**
     * Function Name: Board.
     * Description: The game board constructor, creates the game board with the
     * given parameters.
     * 
     * @param row_s - the number of rows in the game board.
     * @param column_s - the number of columns in the game board.
     * @return: void.
     */
    public Board(int row_s, int column_s) {
        setRowSize(row_s);
        setColumnSize(column_s);
        initializeBoard();
    }

    public Board(Board other_board) {
        this.setColumnSize(other_board.getColumnSize());
        this.setRowSize(other_board.getRowSize());
        this.score_counter_ = other_board.getScoreCounter();
        int row_s = getRowSize();
        int column_s = getColumnSize();
        GamePiece[][] other_matrix = other_board.getGameMatrix();
        GamePiece[][] temp_matrix = new GamePiece[row_s][column_s];
        for (int m = 0; m < row_s; m++) {
            for (int n = 0; n < column_s; n++) {
                temp_matrix[m][n] = new GamePiece(m, n, other_matrix[m][n].getGamePieceSymbol());
            }
        }

        this.setGameMatrix(temp_matrix);
    }

    /**
     * Function Name: initializeBoard
     * Description: creates a 2 dimensional matrix with the Board given size
     * (rows*columns),initialize the board matrix with 4 starting game pieces.
     * 
     * @param void
     * @return: void
     */
    private void initializeBoard() {
        int row_s = getRowSize();
        int column_s = getColumnSize();
        int empty_cells = 0;
        GamePiece[][] temp_matrix = new GamePiece[row_s][column_s];
        for (int m = 0; m < row_s; m++) {
            for (int n = 0; n < column_s; n++) {
                temp_matrix[m][n] = new GamePiece(m, n, GamePieceSymbol.EMPTY.getSymbolValue());
                empty_cells++;
            }
        }
        Counter counter = getScoreCounter();
        counter.setEmptyCells(empty_cells);
        setGameMatrix(temp_matrix);
        // initialize the first 4 game pieces.
        int x = row_s / 2;
        int y = column_s / 2;
        addGamePiece(x, y - 1, GamePieceSymbol.PLAYER_1.getSymbolValue());
        addGamePiece(x - 1, y, GamePieceSymbol.PLAYER_1.getSymbolValue());
        addGamePiece(x - 1, y - 1, GamePieceSymbol.PLAYER_2.getSymbolValue());
        addGamePiece(x, y, GamePieceSymbol.PLAYER_2.getSymbolValue());
    }

    /**
     * Function Name: printBoardLines
     * Description: used by printBoard ,prints the separating lines between
     * game board rows .
     * 
     * @param void
     * @return: void
     */
    private void printBoardLines() {
        int column_s = getColumnSize();
        String s5 = "-----";
        String s3 = "---";
        System.out.print("-");
        // Dynamically change the number of separating lines based on number of columns.
        for (int i = 0; i < column_s; i++) {
            if (i % 2 == 0) {
                System.out.print(s5);
            } else {
                System.out.print(s3);
            }
        }
        if (column_s % 2 == 0) {
            System.out.println("-");
        } else {
            System.out.println();
        }
    }

    /**
     * Function Name: printBoard
     * Description: prints the game board in a certain format.
     * 
     * @param void
     * @return: void
     */
    public void printBoard() {
        GamePiece[][] matrix = getGameMatrix();
        int row_s = getRowSize();
        int column_s = getColumnSize();
        System.out.println("current board:\n");
        // print the columns numbers
        for (int i = 0; i < column_s; i++) {
            System.out.print(" | " + i);
        }
        System.out.println(" |");
        // print each row of the matrix
        for (int m = 0; m < row_s; m++) {
            printBoardLines();
            System.out.print(m + "|");
            for (int n = 0; n < column_s; n++) {
                char current_symbol = (char) matrix[m][n].getGamePieceSymbol();
                System.out.print(" " + current_symbol + " |");
            }
            System.out.println("");
        }
        printBoardLines();
        Counter counter = getScoreCounter();
        System.out.print("Current game scores:   | Player X: " + counter.getPlayer1Score() + " | Player O: "
                + counter.getPlayer2Score() + " | empty cells: " + counter.getEmptyCells() + " |" + "\n\n");
    }

    /**
     * Function Name: checkDuplicateStrikes
     * Description: checks the given strike_vector if its last move
     * (the player chosen move) already exist in the player possible moves
     * vector,if so returns the index of that strike_vector in possible
     * moves so they will be merged into one bigger vector
     * (doing so enables one move to change more then one line) .
     *
     * @param possible_moves-a vector containing all strike_vector, each
     *            strike_vector contains the game pieces to be fliped in the strike path.
     * @param strike_vector- the vector to be checked.
     * @return: strike_index - the index of the strike_vector to be
     *          merged(from possible_moves) with the given strike_vector.
     *          (if -1 is returned - not merging and the strike_vector is added
     *          normaly).
     */
    private int checkDuplicateStrikes(Vector<Vector<GamePiece>> possible_moves, Vector<GamePiece> strike_Vector) {
        int strike_index = -1;
        Vector<GamePiece> current_Vector = possible_moves.get(0);
        int moves_size = possible_moves.size();
        int current_Vector_size = current_Vector.size();
        int strike_Vector_size = strike_Vector.size();
        Point current_strike_point = current_Vector.get(current_Vector_size - 1).getPieceLocationPoint();
        Point strike_Vector_strike_point = strike_Vector.get(strike_Vector_size - 1).getPieceLocationPoint();
        for (int i = 0; i < moves_size; i++) {
            current_Vector_size = possible_moves.get(i).size();
            current_strike_point = possible_moves.get(i).get(current_Vector_size - 1).getPieceLocationPoint();
            if (current_strike_point == strike_Vector_strike_point) {
                strike_index = i;
            }
        }
        return (strike_index);
    }

    /**
     * Function Name: printVector
     * Description: prints the given <GamePiece> vector in a certain format.
     * 
     * @param vector<GamePiece> v - the vector to print.
     * @return: void
     */
    private void printVector(Vector<GamePiece> vector) {
        if (vector.size() > 0) {
            System.out.println("************ Printing Vector<gamePiece> ************");
            System.out.println("Vector size is: " + vector.size());
            for (int i = 0; i < vector.size(); i++) {
                GamePiece g = vector.get(i);
                System.out.println("i is: " + i + " point: " + g.getPieceLocationPoint().toString());
            }
        }
        System.out.println("********************************************************");

    }

    /**
     * Function Name: isBoardEdgePiece
     * Description: checks if the given game piece is at the edge of the
     * board at the given direction.
     * 
     * @param piece- the game piece to check.
     * @param direction - the direction to check.
     * @return: true - if the game piece at the given direction is at
     *          the edge of the board false otherwise.
     */
    private Boolean isBoardEdgePiece(GamePiece piece, Direction direction) {
        int board_row_s = getRowSize();
        int board_column_s = getColumnSize();
        int piece_row = piece.getGamePieceRow();
        int piece_column = piece.getGamePieceColumn();
        boolean val = false;
        switch (direction) {
        case NORTH:
            if (piece_row == 0) {
                val = true;
            }
            break;
        case NORTH_EAST:
            if ((piece_row == 0) && (piece_column == board_column_s - 1)) {
                val = true;
            }
            break;
        case EAST:

            if (piece_column == board_column_s - 1) {
                val = true;
            }
            break;
        case SOUTH_EAST:
            if ((piece_row == board_row_s - 1) && (piece_column == board_column_s - 1)) {
                val = true;
            }
            break;
        case SOUTH:
            if (piece_row == board_row_s - 1) {
                val = true;
            }
            break;
        case SOUTH_WEST:
            if ((piece_row == board_row_s - 1) && (piece_column == 0)) {
                val = true;
            }
            break;
        case WEST:
            if (piece_column == 0) {
                val = true;
            }
            break;
        case NORTH_WEST:
            if ((piece_row == 0) && (piece_column == 0)) {
                val = true;
            }
            break;
        default:
            System.out.println("at direction switch default case");
            break;
        }
        return (val);
    }

    /**
     * Function Name: getChangedPieces.
     * Description:returns a vector with all changed pieces points in after the given move is played.
     *
     * @param player_choosen_move_vec-a vector containing the game pieces to be fliped in the strike
     *            path.
     * @return: changed_pieces_vec - a vector with all changed pieces points in after the given move is played.
     */
    public Vector<Point> getChangedPieces(Vector<GamePiece> player_choosen_move_vec) {
        int row_size = this.getRowSize();
        int column_size = this.getColumnSize();
        GamePiece[][] old_game_matrix = this.getGameMatrix();
        Vector<Point> changed_pieces_vec = new Vector<>();
        Board temp_board = new Board(this);
        temp_board.flipAllPiecesInRange(player_choosen_move_vec);
        GamePiece[][] changed_game_matrix = temp_board.getGameMatrix();
        for (int m = 0; m < row_size; m++) {
            for (int n = 0; n < column_size; n++) {
                if (old_game_matrix[m][n].getGamePieceSymbol() != changed_game_matrix[m][n].getGamePieceSymbol()) {
                    changed_pieces_vec.add(new Point(m, n));
                }

            }
        }
        return changed_pieces_vec;
    }

    /**
     * Function Name: checkMovesInDirection
     * Description: checks for a player game piece at(row,coulmn) if it has
     * possible moves in the given direction(checking the game rules),
     * if such moves are found (valid ones) creates a strike_vector
     * containing all the pieces to be filped in that strike,
     * this strike_vector is compared with other strike vectors in
     * the possible_moves vector (contains other strike vectors) if a strike
     * vector with the same end point is found merge them else add it to
     * the possible moves vector.
     *
     * @param row- the player game piece being checked location row.
     * @param column -the player game piece being checked location column.
     * @param direction- the direction to check moves for.
     * @param possible_moves-a vector containing all strike_vector, each
     *            strike_vector contains the game pieces to be fliped in the strike path.
     * @param rules -the game rules set for the game - for move/strike validation.
     * @return: void
     */
    private void checkMovesInDirection(int row, int column, Direction direction, int opponent,
            Vector<Vector<GamePiece>> possible_moves, GameRules rules) {
        GamePiece[][] matrix = getGameMatrix();
        Vector<GamePiece> strike_Vector = new Vector<GamePiece>(10, 2);
        int board_row_s = getRowSize();
        int board_column_s = getColumnSize();
        int i = 1;
        int end = 1;
        int m = row;
        int n = column;
        boolean valid_move = false;
        boolean opponent_encounterd = false;
        char player_symbol = (char) matrix[m][n].getGamePieceSymbol();
        char current_piece;
        do {
            // add the first game piece at the strike Vector (strike origin piece).
            if (i == 1) {
                strike_Vector.addElement(matrix[m][n]);
            }
            // direction to check
            switch (direction) {
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
                System.out.println("at direction switch default case");
                break;
            }
            // out of bounds.
            if ((n < 0) || (n >= board_column_s) || (m < 0) || (m >= board_row_s)) {
                strike_Vector.clear();
                break;
            }
            current_piece = (char) matrix[m][n].getGamePieceSymbol();
            if (current_piece == opponent) {
                opponent_encounterd = true;
            }
            valid_move = rules.checkValidMove(current_piece, player_symbol, opponent_encounterd,
                    isBoardEdgePiece(matrix[m][n], direction));
            if (!valid_move) {
                strike_Vector.clear();
                break;
            }
            if (valid_move) {
                strike_Vector.addElement(matrix[m][n]);
                // no valid strike Vector for less then 3 game pieces.
                if ((current_piece == GamePieceSymbol.EMPTY.getSymbolChar()) && (strike_Vector.size() > 2)) {
                    if (possible_moves.size() > 0) {
                        int strike_index = checkDuplicateStrikes(possible_moves, strike_Vector);
                        // merge the Vectors if duplicate found.
                        if (strike_index > -1) {
                            int strike_Vector_size = strike_Vector.size();
                            for (int j = 0; j < strike_Vector_size; j++) {
                                possible_moves.get(strike_index).addElement(strike_Vector.get(j));
                            }
                        } else {
                            possible_moves.addElement(strike_Vector);
                        }
                    } else {
                        // possible moves is empty, add the strike Vector.
                        possible_moves.addElement(strike_Vector);
                    }
                    break;
                }
            }
            i++;
        } while (end == 1);
    }

    /**
     * Function Name: getPlayerPossibleMoves.
     * Description: checks all the player possible moves for each game piece
     * he owns , and returns a possible moves vector.
     * 
     * @param player - the player to check moves for.
     * @param rules - the game rules to check valid moves by.
     * @return: possible_moves - a vector containing all the player
     *          possible moves(that will result at a strike).
     */
    public Vector<Vector<GamePiece>> getPlayerPossibleMoves(Player player, GameRules rules) {
        GamePiece[][] matrix = getGameMatrix();
        int row_s = getRowSize();
        int column_s = getColumnSize();
        int opponent_symbol = player.getOpponentSymbol();
        int player_symbol = player.getPlayerSymbol();
        Vector<Vector<GamePiece>> possible_moves = new Vector<Vector<GamePiece>>(10, 2);
        // check the whole game board for player valid moves.
        for (int m = 0; m < row_s; m++) {
            for (int n = 0; n < column_s; n++) {
                int current_piece = matrix[m][n].getGamePieceSymbol();
                // player game piece found,checks all directions for valid moves.
                if (current_piece == player_symbol) {
                    checkMovesInDirection(m, n, Direction.NORTH, opponent_symbol, possible_moves, rules);
                    checkMovesInDirection(m, n, Direction.NORTH_EAST, opponent_symbol, possible_moves, rules);
                    checkMovesInDirection(m, n, Direction.EAST, opponent_symbol, possible_moves, rules);
                    checkMovesInDirection(m, n, Direction.SOUTH_EAST, opponent_symbol, possible_moves, rules);
                    checkMovesInDirection(m, n, Direction.SOUTH, opponent_symbol, possible_moves, rules);
                    checkMovesInDirection(m, n, Direction.SOUTH_WEST, opponent_symbol, possible_moves, rules);
                    checkMovesInDirection(m, n, Direction.WEST, opponent_symbol, possible_moves, rules);
                    checkMovesInDirection(m, n, Direction.NORTH_WEST, opponent_symbol, possible_moves, rules);
                }
            }
        }
        return (possible_moves);
    }

    /**
     * Function Name: flipAllPiecesInRange.
     * Description:filpes all the game pieces to the right player symbol at
     * the strike vector located at move index inside the possible moves vector.
     *
     * @param moves-a vector containing the game pieces to be fliped in the strike
     *            path.
     * @return: void.
     */
    public void flipAllPiecesInRange(Vector<GamePiece> moves) {
        // the player symbol to change all other game pieces in the strike.
        int flipTo = moves.get(0).getGamePieceSymbol();
        for (int i = 0; i < moves.size(); i++) {
            addGamePiece(moves.get(i).getGamePieceRow(), moves.get(i).getGamePieceColumn(), flipTo);
        }
    }

    /**
     * Function Name: getColumnSize.
     * Description: returns the game board matrix column size.
     * 
     * @param void.
     * @return: column_size - the game board matrix column size.
     */
    private int getColumnSize() {
        return (this.column_size_);
    }

    /**
     * Function Name: setColumnSize.
     * Description: sets the game board matrix column size to given parameter.
     * 
     * @param columnSize - the game board matrix column size.
     * @return: void.
     */
    private void setColumnSize(int columnSize) {
        this.column_size_ = columnSize;
    }

    /**
     * Function Name:getRowSize.
     * Description: returns the game board matrix row size.
     * 
     * @param void.
     * @return: row_size - the game board matrix row size.
     */
    private int getRowSize() {
        return (this.row_size_);
    }

    /**
     * Function Name: setRowSize.
     * Description: sets the game board matrix row size to given parameter.
     * 
     * @param rowSize - the game board matrix row size.
     * @return: void.
     */
    private void setRowSize(int rowSize) {
        this.row_size_ = rowSize;
    }

    /**
     * Function Name: getGameMatrix.
     * Description: returns the game board matrix.
     * 
     * @param void.
     * @return: GamePiece** matrix - the game board matrix.
     */
    private GamePiece[][] getGameMatrix() {
        return (this.game_matrix_);
    }

    /**
     * Function Name: setGameMatrix.
     * Description: sets the game board matrix to given matrix.
     * 
     * @param gameMatrix- the game board matrix to set.
     * @return: void.
     */
    private void setGameMatrix(GamePiece[][] temp_matrix) {
        this.game_matrix_ = temp_matrix;
    }

    /**
     * Function Name: addGamePiece.
     * Description: changes the symbol for the game piece at the given
     * location and updates the game score counter of the change.
     *
     * @param row - the row on the game board to place the game piece.
     * @param column - the column on the game board to place the game piece.
     * @param symbol - the int representing the game piece symbol.
     * @return: void.
     */
    private void addGamePiece(int row, int column, int symbol) {
        GamePiece[][] matrix = getGameMatrix();
        Counter counter = getScoreCounter();
        int symbol_before = matrix[row][column].getGamePieceSymbol();
        matrix[row][column].setGamePieceSymbol(symbol);
        counter.addToPlayer(symbol_before, symbol);
    }

    /**
     * Function Name: getScoreCounter.
     * Description: returns the game score counter.
     * 
     * @param void.
     * @return: score counter - keeps the game board scores.
     */
    public Counter getScoreCounter() {
        return (this.score_counter_);
    }

}
