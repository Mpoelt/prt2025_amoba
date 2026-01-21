package org.example.serviceTest;

import org.example.domain.*;
import org.example.service.ComputerPlayerService;
import org.example.service.MoveRuleService;
import org.example.service.PlayerMoveService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComputerPlayerServiceTest {

    @Mock
    private PlayerMoveService playerMoveService;

    @Mock
    private MoveRuleService moveRuleService;

    @InjectMocks
    private ComputerPlayerService computerPlayerService;

    private Board board;
    private ComputerPlayer computer;
    private HumanPlayer human;

    @Before
    public void setup() throws Exception {
        board = new Board(3);
        computer = new ComputerPlayer(Symbol.X, "Computer");
        human = new HumanPlayer(Symbol.O, "Human");

        Field randomField = ComputerPlayerService.class.getDeclaredField("random");
        randomField.setAccessible(true);

        Random mockRandom = mock(Random.class);
        randomField.set(computerPlayerService, mockRandom);

        when(mockRandom.nextInt(3)).thenReturn(0, 1, 2, 0, 1, 2);
    }

    // -------------------------
    // 1. Random első lépés
    // -------------------------
    @Test
    public void makeMove_randomFirstMove_shouldPlaceSymbol() {
        when(moveRuleService.hasAnySymbol(board, Symbol.X)).thenReturn(false);

        computerPlayerService.makeMove(computer, human, board);

        assertEquals(Symbol.X, board.get(computer.getRow(), computer.getCol()));
    }

    // -------------------------
    // 2. Winning move
    // -------------------------
    @Test
    public void makeMove_winningMove_shouldWin() {
        // X-ek az első sorban
        board.set(0, 0, Symbol.X);
        board.set(0, 1, Symbol.X);

        when(moveRuleService.hasAnySymbol(board, Symbol.X)).thenReturn(true);
        when(moveRuleService.isAdjecentToSymbol(any(Board.class), anyInt(), anyInt(), eq(Symbol.X))).thenReturn(true);
        when(playerMoveService.checkWin(board, 0, 2, Symbol.X)).thenReturn(true);

        computerPlayerService.makeMove(computer, human, board);

        assertEquals(Symbol.X, board.get(0, 2));
    }

    // -------------------------
    // 3. Block human win
    // -------------------------
    @Test
    public void makeMove_blockHumanWin_shouldBlock() {
        // Human nyerhetne
        board.set(1, 0, Symbol.O);
        board.set(1, 1, Symbol.O);

        when(moveRuleService.hasAnySymbol(board, Symbol.X)).thenReturn(true);
        when(moveRuleService.isAdjecentToSymbol(any(Board.class), anyInt(), anyInt(), eq(Symbol.X))).thenReturn(false);
        when(playerMoveService.checkWin(board, 1, 2, Symbol.O)).thenReturn(true);

        computerPlayerService.makeMove(computer, human, board);

        assertEquals(Symbol.X, board.get(1, 2));
    }

    // -------------------------
    // 4. Random adjacent move
    // -------------------------
    @Test
    public void makeMove_randomAdjacentMove_shouldPlaceAdjacent() {
        // GIVEN
        board.set(1, 1, Symbol.X);

        when(moveRuleService.hasAnySymbol(board, Symbol.X)).thenReturn(true);

        // Csak (0,1) legyen adjacent
        when(moveRuleService.isAdjecentToSymbol(board, 0, 1, Symbol.X)).thenReturn(true);

        when(moveRuleService.isAdjecentToSymbol(board, 0, 0, Symbol.X)).thenReturn(false);
        when(moveRuleService.isAdjecentToSymbol(board, 0, 2, Symbol.X)).thenReturn(false);
        when(moveRuleService.isAdjecentToSymbol(board, 1, 0, Symbol.X)).thenReturn(false);
        when(moveRuleService.isAdjecentToSymbol(board, 1, 2, Symbol.X)).thenReturn(false);
        when(moveRuleService.isAdjecentToSymbol(board, 2, 0, Symbol.X)).thenReturn(false);
        when(moveRuleService.isAdjecentToSymbol(board, 2, 1, Symbol.X)).thenReturn(false);
        when(moveRuleService.isAdjecentToSymbol(board, 2, 2, Symbol.X)).thenReturn(false);

        // WHEN
        computerPlayerService.makeMove(computer, human, board);

        // THEN
        assertEquals(Symbol.X, board.get(0, 1));
        assertEquals(0, computer.getRow());
        assertEquals(1, computer.getCol());
    }

    // -------------------------
    // 5. Private makeRandomMove teszt
    // -------------------------
    @Test
    public void makeRandomMove_shouldPlaceSymbol() throws Exception {
        // Random mezőt mockoltuk, így determinista lesz
        Field randomField = ComputerPlayerService.class.getDeclaredField("random");
        randomField.setAccessible(true);
        Random mockRandom = (Random) randomField.get(computerPlayerService);
        when(mockRandom.nextInt(3)).thenReturn(0, 1);

        // Reflection a private metódushoz
        var method = ComputerPlayerService.class.getDeclaredMethod("makeRandomMove", Player.class, Board.class);
        method.setAccessible(true);
        method.invoke(computerPlayerService, computer, board);

        assertEquals(Symbol.X, board.get(computer.getRow(), computer.getCol()));
    }

    // -------------------------
    // 6. Private makeRandomAdjacentMove teszt
    // -------------------------
    @Test
    public void makeRandomAdjacentMove_shouldPlaceSymbolAdjacent() throws Exception {
        board.set(1, 1, Symbol.X);

        lenient().when(moveRuleService.isAdjecentToSymbol(board, 0, 1, Symbol.X)).thenReturn(true);

        lenient().when(moveRuleService.isAdjecentToSymbol(board, 0, 0, Symbol.X)).thenReturn(false);
        lenient().when(moveRuleService.isAdjecentToSymbol(board, 0, 2, Symbol.X)).thenReturn(false);
        lenient().when(moveRuleService.isAdjecentToSymbol(board, 1, 0, Symbol.X)).thenReturn(false);
        lenient().when(moveRuleService.isAdjecentToSymbol(board, 1, 2, Symbol.X)).thenReturn(false);
        lenient().when(moveRuleService.isAdjecentToSymbol(board, 2, 0, Symbol.X)).thenReturn(false);
        lenient().when(moveRuleService.isAdjecentToSymbol(board, 2, 1, Symbol.X)).thenReturn(false);
        lenient().when(moveRuleService.isAdjecentToSymbol(board, 2, 2, Symbol.X)).thenReturn(false);

        var method = ComputerPlayerService.class
                .getDeclaredMethod("makeRandomAdjacentMove", Player.class, Board.class);
        method.setAccessible(true);
        method.invoke(computerPlayerService, computer, board);

        assertEquals(Symbol.X, board.get(computer.getRow(), computer.getCol()));
        assertEquals(0, computer.getRow());
        assertEquals(1, computer.getCol());
    }

}
