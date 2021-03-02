package com.robertjap.tictactoe.gameinterface;

import com.robertjap.tictactoe.gamelogic.Game;
import com.robertjap.tictactoe.gamelogic.Player;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

public final class ConsoleUI implements UserInterface {
    private List<List<Player>> boardData;
    private boolean gameIsOver;

    @Override
    public void endGame(Player player) {
        gameIsOver = true;

        System.out.println("The game is over");

        if (player == null) {
            System.out.println("The game is a draw");
        } else {
            System.out.println("The " + player + "'s win");
        }
    }

    @Override
    public void update(Player player, Point square) {
        if (player == null) {
            throw new IllegalArgumentException("Cannot update for player of type 'null'");
        } else if (square == null) {
            System.out.println("That was not a valid move, an empty square is required");
        } else {
            List<Player> col = boardData.get(square.x);
            col.set(square.y, player);

            printBoard(boardData);
        }
    }

    @Override
    public void startGame(Game game, List<List<Player>> boardData) {
        // Validate input
        if (boardData == null) {
            throw new IllegalArgumentException("Cannot start game with board of type 'null'");
        }

        this.boardData = boardData;

        gameIsOver = false;

        System.out.println("Welcome to a game of Tic Tac Toe");

        printBoard(boardData);

        Scanner scanner = new Scanner(System.in);

        while (!gameIsOver) {
            Player currentPlayer = game.getCurrentPlayer();

            System.out.println("It's " + currentPlayer + "'s turn to move.");
            System.out.print("\nEnter the square to move in format 'row column' (1 - " + boardData.size() + "): ");

            int row = scanner.nextInt();
            int col = scanner.nextInt();

            while (row < 1 || boardData.size() < row || col < 1 || boardData.size() < col) {
                System.out.print("\n\nInvalid row and/or column\n\nTry again: ");

                row = scanner.nextInt();
                col = scanner.nextInt();
            }

            Point square = new Point(row - 1, col - 1);

            game.move(square);
        }

        System.out.print("\nWould you like to play a new game? 'Y' or 'N': ");

        char newGameAnswer = scanner.next().charAt(0);

        while (newGameAnswer != 'Y' && newGameAnswer != 'N') {
            System.out.print("\nInvalid response\n\nTry again: ");

            newGameAnswer = scanner.next().charAt(0);
        }

        if (newGameAnswer == 'Y') {
            System.out.println();

            game.startNew();
        }
    }

    /**
     * Display the board in the console.
     * @param boardData the data to print
     */
    private void printBoard(List<List<Player>> boardData) {
        System.out.println();

        for (int i = 0; i < boardData.size(); i++) {
            List<Player> col = boardData.get(i);

            for (int j = 0; j < col.size(); j++) {
                Player player = col.get(j);

                if (player == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(player);
                }

                if (j < col.size() - 1) {
                    System.out.print("|");
                }
            }

            System.out.println();

            if (i < boardData.size() - 1) {

                for (int j = 0; j < col.size() - 1; j++) {
                    System.out.print("-+");
                }

                System.out.println("-");
            }
        }

        System.out.println();
    }
}
