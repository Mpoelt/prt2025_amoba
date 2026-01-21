package org.example.serviceTest;

import org.example.domain.Board;
import org.example.service.ConsoleService;
import org.example.service.GameLoadService;
import org.example.service.LoadGameDecider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoadGameDeciderTest {

    @Mock
    private ConsoleService consoleService;

    @Mock
    private GameLoadService gameLoadService;

    @InjectMocks
    private LoadGameDecider loadGameDecider;

    private Board loadedBoard;

    @Before
    public void setUp() {
        loadedBoard = new Board(5);
    }

    // --------------------------------------------------
    // 1️⃣ Betöltés fájlból (yes)
    // --------------------------------------------------
    @Test
    public void loadBoard_whenUserChoosesYes_shouldLoadFromFile() {
        // GIVEN
        when(consoleService.readStringFromConsole(anyString()))
                .thenReturn("yes");

        when(gameLoadService.loadBoardFromFile("mentes.txt"))
                .thenReturn(loadedBoard);

        // WHEN
        Board result = loadGameDecider.loadBoard();

        // THEN
        assertEquals(loadedBoard, result);

        verify(gameLoadService).loadBoardFromFile("mentes.txt");
        verify(consoleService, never()).readIntFromConsole(anyString());
    }

    // --------------------------------------------------
    // 2️⃣ Új játék (no)
    // --------------------------------------------------
    @Test
    public void loadBoard_whenUserChoosesNo_shouldCreateNewBoard() {
        // GIVEN
        when(consoleService.readStringFromConsole(anyString()))
                .thenReturn("no");

        when(consoleService.readIntFromConsole(anyString()))
                .thenReturn(4);

        // WHEN
        Board result = loadGameDecider.loadBoard();

        // THEN
        assertEquals(4, result.getSize());

        verify(gameLoadService, never()).loadBoardFromFile(anyString());
        verify(consoleService).readIntFromConsole(anyString());
    }
}
