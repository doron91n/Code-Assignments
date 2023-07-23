
/**
 * GameFlow class - responsible for the game flow .
 *
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class GameFlow implements Game {

    private int row_size_;
    private int column_size_;
    private Board game_board_;
    private Player player_1_ = new LocalPlayer(GamePieceSymbol.PLAYER_1.getSymbolChar());
    private Player player_2_ = new LocalPlayer(GamePieceSymbol.PLAYER_2.getSymbolChar());
    private GameRules rules_ = new BasicGameRules();
    private static final String GAME_OVER_FILE_TXT = "gameOver.txt";

    /**
     * Function Name: GameFlow
     * Description: GameFlow constructor.
     * 
     * @param row - the number of rows on the game board.
     * @param column - the number of columns on the game board.
     * @return: void.
     */
    public GameFlow(int row, int column) {
        game_board_ = new Board(row, column);
        getPlayer1().setPlayerTurn(true);
        boolean game_over = false;
        Counter score_counter = getGameBoard().getScoreCounter();
        // plays one turn until game over conditions are met.
        do {
            game_board_.printBoard();
            playOneTurn();
            game_over = getGameRules().isGameOver(score_counter, getPlayer1().isPlayerTurn(),
                    getPlayer2().isPlayerTurn());
        } while (!game_over);
        gameOver();
        try {
            saveGameOverFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gameOver() {
        System.out.println("\n\n********   Game Over   ********\n");
        char winner_symbol = (char) getGameRules().getWinnerSymbol();
        int winner_score = getGameRules().getWinnerScore();
        if ((winner_symbol == GamePieceSymbol.EMPTY.getSymbolChar()) && (winner_score == -10)) {
            System.out.println("        It`s a Tie!! there are no winners!!!\n");
        } else {
            System.out.println(
                    "        The Winner is Player : " + winner_symbol + "  with a score of: " + winner_score + "\n");
        }
    }

    /**
     * Function: saveSettings
     * Description: saves to the settings file the given game settings from the given settings_values Vector with all
     * the needed game settings in order: vector[0] = board size,vector[1] = opening player,vector[2] = player 1
     * color,vector[3] = player 2 color.
     *
     * @param settings_values - the vector containing all the needed game settings values.
     * @return void.
     * @throws Exception "Error: Failed to save game settings , missing parameters.".
     * @throws IOException "Error - the save to a file has failed" .
     */
    public void saveGameOverFile() throws Exception {
        BufferedWriter writer = null;
        try {
            String game_over_text = "";
            char winner_symbol = (char) getGameRules().getWinnerSymbol();
            int winner_score = getGameRules().getWinnerScore();
            if ((winner_symbol == GamePieceSymbol.EMPTY.getSymbolChar()) && (winner_score == -10)) {
                game_over_text = "It`s a Tie!! there are no winners!!!";
            } else {
                game_over_text = "The Winner is Player: " + winner_symbol + "  with a score of: " + winner_score;
            }
            writer = new BufferedWriter(new FileWriter(GAME_OVER_FILE_TXT));
            writer.write(game_over_text);
        } catch (IOException e) {
            throw new IOException("Error - the save to a file has failed");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                ;
            }
        }
    }

    @Override
    public Point getUserInput() {
        int row = -1;
        int column = -1;
        int i = 1;
        String line;
        // gets the user input for next player move.
        do {
            System.out.println("choose a move (row,column) to play: [ input example: point " + "(1,2) will be: 1 2 ]");
            Scanner input_scanner = new Scanner(System.in);
            if (input_scanner.hasNextLine()) {
                line = input_scanner.nextLine();
                String[] splited = line.split(" ");
                row = Integer.parseInt(splited[0]);
                column = Integer.parseInt(splited[1]);

            } else {
                input_scanner.close();
            }
            if ((row != -1) && (column != -1)) {
                i = 0;
            } else {
                System.out.println("Error: input not in format - only 2 numbers are allowed seperated by space.");
                row = column = -1;
            }
        } while (i == 1);
        return (new Point(row, column));
    }

    @Override
    public int getValidMoveIndex(Vector<Vector<GamePiece>> player_moves) {
        int end = 1;
        int move_index = -1;
        int player_moves_size = player_moves.size();
        do {
            // get the user input (next player move) and check if its a valid move.
            Point p = getUserInput();
            for (int i = 0; i < player_moves_size; i++) {
                Point p1 = player_moves.get(i).lastElement().getPieceLocationPoint();
                if (p1.isEqualPoint(p)) {
                    end = 0;
                    move_index = i;
                }
            }
            if (end == 1) {
                System.out.println("Error: entered invalid move, try again.");
            }
        } while (end == 1);
        return (move_index);
    }

    @Override
    public void playOneTurn() {
        GameRules rules = getGameRules();
        Player player = null;
        Player opponent = null;
        Player p1 = getPlayer1();
        Player p2 = getPlayer2();
        if (p1.isPlayerTurn() && !p2.isPlayerTurn()) {
            player = p1;
            opponent = p2;

        }
        if (!p1.isPlayerTurn() && p2.isPlayerTurn()) {
            opponent = p1;
            player = p2;
        }
        char player_symbol = (char) player.getPlayerSymbol();
        Vector<Vector<GamePiece>> player_moves = getGameBoard().getPlayerPossibleMoves(player, rules);
        if (!player_moves.isEmpty()) {
            System.out.println("Player " + player_symbol + ": it's your turn.");
            System.out.print("your possible moves are: ");
            int player_moves_size = player_moves.size();
            // print valid player moves
            for (int i = 0; i < player_moves_size; i++) {
                Vector<GamePiece> v = player_moves.get(i);
                GamePiece g = v.lastElement();
                System.out.print(g.getPieceLocationPoint().toString());
                if (i < player_moves.size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("");
            int move_index = getValidMoveIndex(player_moves);

            ///////////////////////////////////////////////////////////////////////////////////
            System.out.println("*************************************************************");
            Vector<Point> p = getGameBoard().getChangedPieces(player_moves.get(move_index));
            for (Point point : p) {
                System.out.println(point.toString());
            }

            System.out.println("*************************************************************");

            ///////////////////////////////////////////////////////////////////////////////////
            getGameBoard().flipAllPiecesInRange(player_moves.get(move_index));
            opponent.setPlayerTurn(true);
        } else {
            System.out.println("no possible moves found for player " + (char) player.getPlayerSymbol());
            // if the player has no more moves check if his opponent has moves.
            Vector<Vector<GamePiece>> opponent_moves = getGameBoard().getPlayerPossibleMoves(opponent, rules);
            if (!opponent_moves.isEmpty()) {
                opponent.setPlayerTurn(true);
            } else {
                System.out.print("no possible moves found for player " + (char) opponent.getPlayerSymbol());
            }
        }
        player.setPlayerTurn(false);
    }

    @Override
    public GameRules getGameRules() {
        return (this.rules_);
    }

    @Override
    public int getColumnSize() {
        return (this.column_size_);
    }

    @Override
    public Board getGameBoard() {
        return (this.game_board_);
    }

    @Override
    public int getRowSize() {
        return (this.row_size_);
    }

    @Override
    public Player getPlayer1() {
        return (this.player_1_);
    }

    @Override
    public Player getPlayer2() {
        return (this.player_2_);
    }

}
