package org.example.domain;

public class ComputerPlayer {

    private final String name;
    private final int highScore;

    public ComputerPlayer(String name, int highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", highScore=" + highScore +
                '}';
    }
}
