package org.example.service;

import org.example.domain.Board;
import org.example.domain.Player;
import org.example.domain.Symbol;

import java.util.Random;

public class ComputerPlayerService {
    private final Random random = new Random();

    public void makeMove(Player computerPlayer, Board board){
        int size = board.getSize();

        while (true) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);

            if (board.get(row, col) == Symbol.EMPTY){
                computerPlayer.setPosition(row,col);
                return;
            }
        }

    }

}
