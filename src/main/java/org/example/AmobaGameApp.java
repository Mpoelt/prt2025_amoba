package org.example;


import org.example.display.BoardDisplayer;
import org.example.domain.Board;
import org.example.domain.HumanPlayer;
import org.example.domain.Player;
import org.example.domain.Symbol;
import org.example.init.BoardInit;
import org.example.service.BoardService;
import org.example.service.ConsoleService;
import org.example.service.PlayerMoveService;

public class AmobaGameApp
{
    public static void main(String[] args){
        ConsoleService consoleService = new ConsoleService();
        int size = consoleService.readIntFromConsole("Add meg a pálya méretét: ");
        Board board = new Board(size);
        Player humanPlayer = new HumanPlayer(Symbol.O);

        PlayerMoveService playerMoveService = new PlayerMoveService(consoleService);
        BoardService boardService = new BoardService(board);
        BoardDisplayer boardDisplayer = new BoardDisplayer(consoleService);

        //1. lépés
        playerMoveService.readPlayerMove(humanPlayer, size);

        //logika
        if (!boardService.makeMove(humanPlayer)){
            consoleService.print("Ez a mező foglalt!\n");
        }

        //megjelenítés
        boardDisplayer.displayBoard(board);

    }


}
