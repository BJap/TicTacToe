package com.robertjap.tictactoe.gamelogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Board {
    // Board information
    private final List<List<Player>> boardData;
    private int squaresFilled = 0;

    // Winner status
    private final List<Integer> rows;
    private final List<Integer> cols;
    private int diagonal = 0;
    private int antiDiagonal = 0;

    /**
     * Construct a Tic Tac Toe board.
     * @param boardSize the board size to create
     */
    public Board(int boardSize) {
        // Validate input
        if (boardSize < 3) {
            throw new IllegalArgumentException("Board size must be a minimum size of '3'");
        }

        // Create the board data
        boardData = new ArrayList<>(boardSize);

        for (int i = 0; i < boardSize; i++) {
            List<Player> col = new ArrayList<>(boardSize);

            for (int j = 0; j < boardSize; j++) {
                col.add(null);
            }

            boardData.add(col);
        }

        // Create the precomputed data that is used to determine a winner
        rows = new ArrayList<>(boardSize);

        for (int i = 0; i < boardSize; i++) {
            rows.add(0);
        }

        cols = new ArrayList<>(boardSize);

        for (int i = 0; i < boardSize; i++) {
            cols.add(0);
        }
    }

    /**
     * Get a copy of the current state of the board.
     * @return a duplicate of the board data
     */
    public List<List<Player>> getBoardData() {
        List<List<Player>> boardDataCopy = new ArrayList<>(boardData.size());

        for (int i = 0; i < boardData.size(); i++) {
            List<Player> colCopy = new ArrayList<>(boardData.size());
            List<Player> colOriginal = boardData.get(i);

            for (int j = 0; j < boardData.size(); j++) {
                Player squareValue = colOriginal.get(j);

                colCopy.add(squareValue);
            }

            boardDataCopy.add(colCopy);
        }

        return boardDataCopy;
    }

    /**
     * Determine if there is no possible winner.
     * @return if there are no more possible moves to win
     */
    public boolean hasDraw() {
        // There is no draw if there minimum amount of moves to draw haven't been made
        if (boardData.size() * 2 - 1 <= squaresFilled) {
            return squaresFilled == boardData.size() * boardData.size();
        }

        return false;
    }

    /**
     * Determine if there is a winner and whom if there is.
     * @param square the square from which to check wins go through
     * @return the winner ('X' or 'O') or 'NONE'
     */
    public Player hasWinner(Point square) {
        // Validate input
        if (square == null) {
            throw new IllegalArgumentException("Cannot determine winner from square of type 'null'");
        }

        // There is no winner if there minimum amount of moves to win haven't been made
        if (squaresFilled < boardData.size() * 2 - 1) {
            return null;
        }

        // Check all the rows for a winner
        int rowCount = rows.get(square.y);

        if (rowCount == boardData.size()) {
            return Player.X;
        } else if (rowCount == -boardData.size()) {
            return Player.O;
        }

        // Check all the columns for a winner
        int colCount = cols.get(square.x);

        if (colCount == boardData.size()) {
            return Player.X;
        } else if (colCount == -boardData.size()) {
            return Player.O;
        }

        // Check the diagonal and anti-diagonal for a winner
        if (diagonal == boardData.size() || antiDiagonal == boardData.size()) {
            return Player.X;
        } else if (diagonal == -boardData.size() || antiDiagonal == -boardData.size()) {
            return Player.O;
        }

        return null;
    }

    /**
     * Make a move for a player in the desired square.
     * @param player the player to put in the square
     * @param square the square location (counting from '0')
     * @return if the move was successful
     */
    public boolean move(Player player, Point square) {
        // Validate inputs
        if (player == null) {
            throw new IllegalArgumentException("Cannot move player of type 'null'");
        }

        if (square == null) {
            throw new IllegalArgumentException("Cannot move player to square of type 'null'");
        }

        if (square.x < 0 || boardData.size() - 1 < square.x || square.y < 0 || boardData.size() - 1 < square.y) {
            throw new IllegalArgumentException(
                    "Square (" + square.x + ", " + square.y + ") must be greater than (0, 0)" +
                            " and less than (" + boardData.size() + ", " + boardData.size() + ")"
            );
        }

        // Get square
        List<Player> col = boardData.get(square.x);
        Player squareContent = col.get(square.y);

        // Make a move if the square is empty
        if (squareContent == null) {
            col.set(square.y, player);

            squaresFilled++;

            // Update the data used to determine if there's a winner
            int count = player == Player.X ? 1 : -1;

            int rowCount = rows.get(square.y) + count;
            rows.set(square.y, rowCount);

            int colCount = cols.get(square.x) + count;
            cols.set(square.x, colCount);

            if (square.x == square.y) {
                diagonal += count;
            }

            if (square.x + square.y == boardData.size() - 1) {
                antiDiagonal += count;
            }

            return true;
        }

        return false;
    }
}
