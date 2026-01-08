package org.example;


import org.example.display.BoardDisplayer;
import org.example.domain.*;
import org.example.service.*;

public class AmobaGameApp
{
    public static void main(String[] args){
        ConsoleService consoleService = new ConsoleService();
        int size = consoleService.readIntFromConsole("Add meg a pálya méretét: ");
        Board board = new Board(size);
        Player humanPlayer = new HumanPlayer(Symbol.O);
        Player computerPlayer = new ComputerPlayer(Symbol.X);

        PlayerMoveService playerMoveService = new PlayerMoveService(consoleService);
        BoardService boardService = new BoardService(board);
        BoardDisplayer boardDisplayer = new BoardDisplayer(consoleService);
        ComputerPlayerService computerPlayerService = new ComputerPlayerService();
        GameService gameService = new GameService(boardDisplayer, consoleService, board, playerMoveService, boardService, computerPlayerService);

    gameService.startGame(humanPlayer, computerPlayer);


    }


}
