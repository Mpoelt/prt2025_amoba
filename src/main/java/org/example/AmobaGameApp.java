package org.example;


import org.example.display.BoardDisplayer;
import org.example.domain.Board;
import org.example.domain.HumanPlayer;
import org.example.domain.Player;
import org.example.init.BoardInit;
import org.example.service.BoardService;
import org.example.service.ConsoleService;
import org.example.service.PlayerMoveService;

public class AmobaGameApp
{
    public static void main(String[] args){
        ConsoleService consoleService = new ConsoleService();
        Board board = BoardInit.createEmptyBoard(consoleService.readIntFromConsole("Add meg a pálya méretét: "));
        Player humanPlayer = new HumanPlayer();

        PlayerMoveService playerMoveService = new PlayerMoveService(consoleService);
        BoardDisplayer boardDisplayer = new BoardDisplayer(consoleService);
        BoardService boardService = new BoardService(board);

        playerMoveService.readPlayerMove(humanPlayer, board.getSize());
        boardDisplayer.displayBoard(board, humanPlayer);

    }


}
