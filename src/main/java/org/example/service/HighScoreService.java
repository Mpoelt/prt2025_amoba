package org.example.service;


import lombok.NoArgsConstructor;
import org.example.database.entity.HighScore;
import org.example.database.repository.HighScoreRepository;
import org.springframework.stereotype.Service;

@Service
public class HighScoreService {
    private final ConsoleService consoleService;
    private final HighScoreRepository highScoreRepository;


    public HighScoreService(ConsoleService consoleService, HighScoreRepository highScoreRepository) {
        this.consoleService = consoleService;
        this.highScoreRepository = highScoreRepository;
    }

    public HighScore findByPlayerNamerOrCreate(String playerName){
        HighScore highScore = highScoreRepository.findByPlayerName(playerName);
        if (highScore == null){
            highScore = highScoreRepository.save(new HighScore(playerName, 0));

        }
        consoleService.print("Hello " + playerName + " a pontszámod " + highScore.getGamesWon());
        return  highScore;
    }

    public HighScore save(HighScore highScore){
        return highScoreRepository.save(highScore);
    }

}
