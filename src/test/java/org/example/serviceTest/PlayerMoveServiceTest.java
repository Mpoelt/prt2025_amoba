package org.example.serviceTest;

import org.example.domain.Board;
import org.example.domain.ComputerPlayer;
import org.example.domain.Player;
import org.example.domain.Symbol;
import org.example.service.ConsoleService;
import org.example.service.PlayerMoveService;
import org.example.service.SaveCommandExceptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerMoveServiceTest {

    @Mock
    private ConsoleService consoleService;

    private PlayerMoveService playerMoveService;
    private Board board;
    private Player player;

    @Before
    public void setUp() {
        playerMoveService = new PlayerMoveService(consoleService);
        board = new Board(6);
        player = new ComputerPlayer(Symbol.X, "TestPlayer");
    }

    // ----------------------------
    // readPlayerMove tesztek
    // ----------------------------

    @Test
    public void readPlayerMove_validInput_shouldSetPlayerPosition() {
        // Sor és oszlop valid input
        when(consoleService.readStringFromConsole(anyString()))
                .thenReturn("2") // row
                .thenReturn("C"); // column

        playerMoveService.readPlayerMove(player, board.getSize());

        assertEquals(2, player.getRow());
        assertEquals(2, player.getCol()); // C -> index 2
    }

    @Test(expected = SaveCommandExceptionService.class)
    public void readPlayerMove_whenSaveCommand_shouldThrowException() {
        when(consoleService.readStringFromConsole(anyString()))
                .thenReturn("save");

        playerMoveService.readPlayerMove(player, board.getSize());
    }

    @Test
    public void readPlayerMove_invalidRow_thenValid_shouldRetry() {
        when(consoleService.readStringFromConsole(anyString()))
                .thenReturn("abc")  // invalid
                .thenReturn("7")    // out of range
                .thenReturn("1")    // valid row
                .thenReturn("B");   // column

        playerMoveService.readPlayerMove(player, board.getSize());

        assertEquals(1, player.getRow());
        assertEquals(1, player.getCol()); // B -> index 1
        // Ellenőrzés, hogy hibák esetén is kiírt valamit
        verify(consoleService, atLeast(2)).print(anyString());
    }

    // ----------------------------
    // checkWin tesztek
    // ----------------------------

    @Test
    public void checkWin_horizontal_shouldReturnTrue() {
        // vízszintes sor: index 0
        for (int c = 0; c < 4; c++) {
            board.set(0, c, Symbol.X);
        }

        boolean result = playerMoveService.checkWin(board, 0, 2, Symbol.X);
        assertTrue(result);
    }

    @Test
    public void checkWin_vertical_shouldReturnTrue() {
        for (int r = 0; r < 4; r++) {
            board.set(r, 1, Symbol.O);
        }

        boolean result = playerMoveService.checkWin(board, 2, 1, Symbol.O);
        assertTrue(result);
    }

    @Test
    public void checkWin_diagonalDown_shouldReturnTrue() {
        for (int i = 0; i < 4; i++) {
            board.set(i, i, Symbol.X);
        }

        boolean result = playerMoveService.checkWin(board, 2, 2, Symbol.X);
        assertTrue(result);
    }

    @Test
    public void checkWin_diagonalUp_shouldReturnTrue() {
        for (int i = 0; i < 4; i++) {
            board.set(3 - i, i, Symbol.O);
        }

        boolean result = playerMoveService.checkWin(board, 1, 2, Symbol.O);
        assertTrue(result);
    }

    @Test
    public void checkWin_noWin_shouldReturnFalse() {
        board.set(0, 0, Symbol.X);
        board.set(0, 1, Symbol.X);
        board.set(0, 2, Symbol.O); // megszakítja a sort
        board.set(0, 3, Symbol.X);

        boolean result = playerMoveService.checkWin(board, 0, 1, Symbol.X);
        assertFalse(result);
    }
}
