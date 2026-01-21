package org.example.serviceTest;

import org.example.domain.Board;
import org.example.domain.Player;
import org.example.domain.Symbol;
import org.example.service.BoardService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardServiceTest {

    @Test
    public void makeMove_placeSymbolOnBoardTest() {
        BoardService boardService = new BoardService();
        Board board = new Board(3);

        Player player = new Player(Symbol.X, "TestPlayer"){};
        player.setPosition(1, 1);

        //WHEN
        boolean result = boardService.makeMove(board, player);

        //THEN
        assertTrue(result);
        assertEquals(Symbol.X, board.get(1, 1));

    }

}

