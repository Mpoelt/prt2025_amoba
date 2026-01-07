package org.example.init;


 import org.example.domain.Board;
 import org.example.domain.Symbol;


public class BoardInit {

    public static Board createEmptyBoard(int size) {
        Board board = new Board(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
               board.set(i, j, Symbol.EMPTY);
            }
        }
        return board;
    }


}


