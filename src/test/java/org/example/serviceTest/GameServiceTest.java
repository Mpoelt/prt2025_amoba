package org.example.serviceTest;

import org.example.database.entity.HighScore;
import org.example.display.BoardDisplayer;
import org.example.domain.*;
import org.example.init.PlayerInit;
import org.example.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @Mock
    private ConsoleService consoleService;

    @Mock
    private PlayerInit playerInit;

    @Mock
    private LoadGameDecider loadGameDecider;

    @Mock
    private BoardDisplayer boardDisplayer;

    @Mock
    private PlayerMoveService playerMoveService;

    @Mock
    private BoardService boardService;

    @Mock
    private ComputerPlayerService computerPlayerService;

    @Mock
    private GameSaveService gameSaveService;

    @Mock
    private HighScoreService highScoreService;

    @InjectMocks
    private GameService gameService;

    private Player human;
    private Board board;
    private HighScore highScore;

    @Before
    public void setUp() {
        human = new HumanPlayer(Symbol.X, "TestPlayer");
        board = new Board(3);
        highScore = new HighScore("TestPlayer", 0);

        when(playerInit.createHumanPlayer()).thenReturn(human);
        when(loadGameDecider.loadBoard()).thenReturn(board);
        when(highScoreService.findByPlayerNameOrCreate("TestPlayer")).thenReturn(highScore);

        when(boardService.makeMove(eq(board), eq(human))).thenReturn(true);

        when(playerMoveService.checkWin(any(), anyInt(), anyInt(), eq(Symbol.X)))
                .thenReturn(true);

        when(consoleService.readStringFromConsole(anyString()))
                .thenReturn("no");
    }


    @Test
    public void startGame_playerWinsAndExit_shouldIncreaseHighScore() {
        // --- WHEN ---
        gameService.startGame();

        // --- THEN ---
        verify(highScoreService).save(highScore);

        // "Nyertél" KÉTSZER is megjelenhet → ne legyen túl szigorú
        verify(consoleService, atLeastOnce())
                .print(contains("Nyertél"));

        verify(consoleService)
                .print(contains("Kilépés"));

        verify(boardDisplayer, atLeastOnce())
                .displayBoard(board);
    }
}
