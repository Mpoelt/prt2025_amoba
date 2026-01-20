package org.example.service;


import org.example.database.entity.HighScore;
import org.example.database.repository.HighScoreRepository;
import org.springframework.stereotype.Service;

@SuppressWarnings({"PMD.LongVariable"})
@Service
public class HighScoreService {
    private final ConsoleService consoleService;
    private final HighScoreRepository highScoreRepository;


    public HighScoreService(final ConsoleService consoleService, final HighScoreRepository highScoreRepository) {
        this.consoleService = consoleService;
        this.highScoreRepository = highScoreRepository;
    }

    public HighScore findByPlayerNameOrCreate(final String playerName) {
        HighScore highScore = highScoreRepository.findByPlayerName(playerName);
        if (highScore == null) {
            highScore = highScoreRepository.save(new HighScore(playerName, 0));

        }
        consoleService.print("Hello " + playerName + " a pontszámod " + highScore.getGamesWon());
        return  highScore;
    }

    public HighScore save(final HighScore highScore) {
        return highScoreRepository.save(highScore);
    }

    public void addHighScoreWhenPlayerWin(final HighScore highScore) {
        highScore.setGamesWon(highScore.getGamesWon() + 1);
        highScoreRepository.save(highScore);
        consoleService.print("Pontszám fissítve! Jelenlegi pontszám: " + highScore.getGamesWon());
    }

}
