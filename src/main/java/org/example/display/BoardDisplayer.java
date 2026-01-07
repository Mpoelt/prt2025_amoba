package org.example.display;
import org.example.domain.Board;
import org.example.domain.Player;
import org.example.domain.Symbol;
import org.example.service.ConsoleService;

public class BoardDisplayer {

    private final ConsoleService consoleService;

    public BoardDisplayer(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    public void diplayBoard(Board board){
        int size = board.getSize();
        final Player player;
    }


    public static void boardPrint(Board board){

        System.out.print("  ");
        for (int j = 0; j < board.getSize(); j++){
            char col = (char) ('A' + j);
            System.out.print(col + " ");
        }
        System.out.println();

        for(int i = 0; i < board.getSize(); i++){
            System.out.print(i + " ");
            for(int j = 0; j < board.getSize(); j++){
                Symbol s = board.get(i,j);
                System.out.print(s == Symbol.EMPTY ? ". " : s + " ");
            }
            System.out.println();
        }
    }

}
