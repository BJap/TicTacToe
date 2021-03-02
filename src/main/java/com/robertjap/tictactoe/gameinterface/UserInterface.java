package com.robertjap.tictactoe.gameinterface;

import com.robertjap.tictactoe.gamelogic.Game;
import com.robertjap.tictactoe.gamelogic.Player;

import java.awt.*;
import java.util.List;

public interface UserInterface {
    /**
     * Tell the interface that the game is over.
     * @param winner the player that won the game or none or null if a draw
     */
    void endGame(Player winner);

    /**
     * Update the interface with a player piece in a square.
     * @param player the player to place
     * @param square the square to place the player within or null if invalid move
     */
    void update(Player player, Point square);

    /**
     * Tell the interface that the game has started.
     * @param game the game to handle input
     * @param boardData a deep copy of the data for the initial state of the board
     */
    void startGame(Game game, List<List<Player>> boardData);
}
