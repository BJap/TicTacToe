package com.robertjap.tictactoe;

import com.robertjap.tictactoe.gamelogic.Game;

public class TicTacToe {
    private static final int DEFAULT_BOARD_SIZE = 3;

    public static void main(String[] args) {
        int boardSize = 0 < args.length ? Integer.parseInt(args[0]) : DEFAULT_BOARD_SIZE;

        Game.usingConsoleUI(boardSize);
    }
}
