package com.robertjap.tictactoe.gamelogic;

import com.robertjap.tictactoe.gameinterface.ConsoleUI;
import com.robertjap.tictactoe.gameinterface.UserInterface;

import java.awt.*;

public final class Game {
    private final UserInterface userInterface;
    private final int boardSize;

    private Board board;
    private Player currentPlayer = Player.X;

    /**
     * The logic for the game.
     * @param userInterface the view for the game
     * @param boardSize the size of the game board
     */
    private Game(UserInterface userInterface, int boardSize) {
        // Validate inputs
        if (userInterface == null) {
            throw new IllegalArgumentException("Cannot create a game with a user interface of type 'null'");
        }

        this.userInterface = userInterface;
        this.boardSize = boardSize;

        startNew();
    }

    /**
     * Start a new game using the console as an interface.
     * @param boardSize the size of the game board
     */
    public static void usingConsoleUI(int boardSize) {
        new Game(new ConsoleUI(), boardSize);
    }

    /**
     * Get the current player.
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Make a move for a player in the desired square.
     * @param square the square location (counting from '0')
     */
    public void move(Point square) {
        boolean moveIsValid = board.move(currentPlayer, square);

        if (moveIsValid) {
            userInterface.update(currentPlayer, square);

            Player winner = board.hasWinner(square);

            // Game won, game draw, or next turn
            if (winner != null) {
                userInterface.endGame(winner);
            } else if (board.hasDraw()) {
                userInterface.endGame(null);
            } else {
                currentPlayer = currentPlayer == Player.X ? Player.O : Player.X;
            }
        } else {
            userInterface.update(currentPlayer, null);
        }
    }

    /**
     * Start a new game.
     */
    public void startNew() {
        board = new Board(boardSize);

        userInterface.startGame(this, board.getBoardData());
    }
}
