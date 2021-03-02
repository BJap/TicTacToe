package com.robertjap.tictactoe;

import com.robertjap.tictactoe.gamelogic.Board;
import com.robertjap.tictactoe.gamelogic.Player;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.List;

public class BoardTests {
    @Test
    public void testBoardTooSmall() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Board(2)
        );
    }

    @Test
    public void testCopyBoardData() {
        Board board = new Board(3);
        List<List<Player>> boardData = board.getBoardData();
        List<Player> col = boardData.get(0);
        Player squareContents = col.get(0);

        Assert.assertNull(squareContents);

        col.set(0, Player.X);

        boardData = board.getBoardData();
        col = boardData.get(0);
        squareContents = col.get(0);

        Assert.assertNull(squareContents);
    }

    @Test
    public void testHasDraw() {
        Board board = new Board(3);
        board.move(Player.X, new Point(0, 0));
        board.move(Player.O, new Point(0, 1));
        board.move(Player.X, new Point(0, 2));
        board.move(Player.X, new Point(1, 0));
        board.move(Player.O, new Point(1, 1));
        board.move(Player.O, new Point(1, 2));
        board.move(Player.O, new Point(2, 0));
        board.move(Player.X, new Point(2, 1));
        board.move(Player.O, new Point(2, 2));

        Assert.assertTrue(board.hasDraw());
    }

    @Test
    public void testMove() {
        Board board = new Board(3);
        List<List<Player>> boardData = board.getBoardData();
        List<Player> col = boardData.get(0);
        Player squareContents = col.get(0);

        Assert.assertNull(squareContents);

        board.move(Player.X, new Point(0, 0));

        boardData = board.getBoardData();
        col = boardData.get(0);
        squareContents = col.get(0);

        Assert.assertEquals(Player.X, squareContents);
    }

    @Test()
    public void testMoveInvalid() {
        Board board = new Board(3);
        Assert.assertThrows(IllegalArgumentException.class, () ->
                board.move(Player.X, new Point(-1, 4))
        );
    }

    @Test
    public void testWinAntiDiagonal() {
        Board board = new Board(3);
        board.move(Player.X, new Point(2, 0));
        board.move(Player.O, new Point(0, 0));
        board.move(Player.X, new Point(1, 1));
        board.move(Player.O, new Point(0, 1));
        board.move(Player.X, new Point(0, 2));

        Player winner = board.hasWinner(new Point(0, 2));

        Assert.assertEquals(Player.X, winner);
    }

    @Test
    public void testWinDiagonal() {
        Board board = new Board(3);
        board.move(Player.X, new Point(0, 0));
        board.move(Player.O, new Point(2, 0));
        board.move(Player.X, new Point(1, 1));
        board.move(Player.O, new Point(2, 1));
        board.move(Player.X, new Point(2, 2));

        Player winner = board.hasWinner(new Point(2, 2));

        Assert.assertEquals(Player.X, winner);
    }

    @Test
    public void testWinHorizontal() {
        Board board = new Board(3);
        board.move(Player.X, new Point(0, 0));
        board.move(Player.O, new Point(0, 1));
        board.move(Player.X, new Point(1, 0));
        board.move(Player.O, new Point(1, 1));
        board.move(Player.X, new Point(2, 0));

        Player winner = board.hasWinner(new Point(2, 0));

        Assert.assertEquals(Player.X, winner);
    }

    @Test
    public void testWinVertical() {
        Board board = new Board(3);
        board.move(Player.X, new Point(0, 0));
        board.move(Player.O, new Point(1, 0));
        board.move(Player.X, new Point(0, 1));
        board.move(Player.O, new Point(1, 1));
        board.move(Player.X, new Point(0, 2));

        Player winner = board.hasWinner(new Point(0, 2));

        Assert.assertEquals(Player.X, winner);
    }

    @Test
    public void testWinDraw() {
        int boardSize = 3;

        Board board = new Board(boardSize);
        board.move(Player.X, new Point(0, 0));
        board.move(Player.O, new Point(0, 1));
        board.move(Player.X, new Point(0, 2));
        board.move(Player.X, new Point(1, 0));
        board.move(Player.O, new Point(1, 1));
        board.move(Player.O, new Point(1, 2));
        board.move(Player.O, new Point(2, 0));
        board.move(Player.X, new Point(2, 1));
        board.move(Player.O, new Point(2, 2));

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Player winner = board.hasWinner(new Point(i, j));

                Assert.assertNull(winner);
            }
        }
    }
}
