package org.example;


import org.example.display.BoardDisplayer;
import org.example.domain.*;
import org.example.service.*;

public class AmobaGameApp
{
    public static void main(String[] args){
        ConsoleService consoleService = new ConsoleService();
        GameLoadService gameLoadService = new GameLoadService();
        LoadGameDecider loadGameDecider = new LoadGameDecider(consoleService, gameLoadService);
        Board board = loadGameDecider.loadBoard();
        Player humanPlayer = new HumanPlayer(Symbol.X);
        Player computerPlayer = new ComputerPlayer(Symbol.O);

        PlayerMoveService playerMoveService = new PlayerMoveService(consoleService);
        BoardService boardService = new BoardService(board);
        BoardDisplayer boardDisplayer = new BoardDisplayer(consoleService);
        ComputerPlayerService computerPlayerService = new ComputerPlayerService(playerMoveService);
        GameSaveService gameSaveService = new GameSaveService();
        GameService gameService = new GameService(boardDisplayer, consoleService, board, playerMoveService,
                boardService, computerPlayerService, gameSaveService, gameLoadService );

    gameService.startGame(humanPlayer, computerPlayer);


    }


}
