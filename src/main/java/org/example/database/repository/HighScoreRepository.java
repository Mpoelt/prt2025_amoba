package org.example.database.repository;


import org.example.database.entity.HighScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighScoreRepository extends JpaRepository<HighScore, Long> {
    HighScore findByPlayerName(String playerName);
}
