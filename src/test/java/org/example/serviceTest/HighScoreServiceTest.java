package org.example.serviceTest;

import org.example.database.entity.HighScore;
import org.example.database.repository.HighScoreRepository;
import org.example.service.ConsoleService;
import org.example.service.HighScoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HighScoreServiceTest {

    @Mock
    private ConsoleService consoleService;

    @Mock
    private HighScoreRepository highScoreRepository;

    @InjectMocks
    private HighScoreService highScoreService;

    private HighScore highScore;

    @Before
    public void setUp() {
        highScore = new HighScore("TestPlayer", 3);
    }

    // --------------------------------------------------
    // 1️⃣ Meglévő játékos megtalálása
    // --------------------------------------------------
    @Test
    public void findByPlayerNameOrCreate_existingPlayer_shouldReturnHighScore() {
        // GIVEN
        when(highScoreRepository.findByPlayerName("TestPlayer"))
                .thenReturn(highScore);

        // WHEN
        HighScore result = highScoreService.findByPlayerNameOrCreate("TestPlayer");

        // THEN
        assertEquals(highScore, result);
        verify(highScoreRepository, never()).save(any());
        verify(consoleService).print(
                contains("TestPlayer")
        );
    }

    // --------------------------------------------------
    // 2️⃣ Új játékos létrehozása
    // --------------------------------------------------
    @Test
    public void findByPlayerNameOrCreate_newPlayer_shouldCreateHighScore() {
        // GIVEN
        when(highScoreRepository.findByPlayerName("NewPlayer"))
                .thenReturn(null);

        when(highScoreRepository.save(any(HighScore.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        HighScore result = highScoreService.findByPlayerNameOrCreate("NewPlayer");

        // THEN
        assertEquals("NewPlayer", result.getPlayerName());
        assertEquals(0, result.getGamesWon());

        verify(highScoreRepository).save(any(HighScore.class));
        verify(consoleService).print(
                contains("NewPlayer")
        );
    }

    // --------------------------------------------------
    // 3️⃣ Save metódus teszt
    // --------------------------------------------------
    @Test
    public void save_shouldDelegateToRepository() {
        // GIVEN
        when(highScoreRepository.save(highScore)).thenReturn(highScore);

        // WHEN
        HighScore result = highScoreService.save(highScore);

        // THEN
        assertEquals(highScore, result);
        verify(highScoreRepository).save(highScore);
    }

    // --------------------------------------------------
    // 4️⃣ Pontszám növelése győzelemnél
    // --------------------------------------------------
    @Test
    public void addHighScoreWhenPlayerWin_shouldIncreaseScoreAndSave() {
        // GIVEN
        HighScore score = new HighScore("Winner", 5);

        // WHEN
        highScoreService.addHighScoreWhenPlayerWin(score);

        // THEN
        assertEquals(6, score.getGamesWon());
        verify(highScoreRepository).save(score);
        verify(consoleService).print(
                contains("Pontszám")
        );
    }
}
