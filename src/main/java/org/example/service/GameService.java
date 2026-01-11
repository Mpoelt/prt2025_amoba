package org.example.service;

import org.example.display.BoardDisplayer;
import org.example.domain.Board;
import org.example.domain.Player;

public class GameService {
    private final BoardDisplayer boardDisplayer;
    private final ConsoleService consoleService;
    private final Board board;
    private final PlayerMoveService playerMoveService;
    private final BoardService boardService;
    ComputerPlayerService computerPlayerService;
    private final GameSaveService gameSaveService;
    private final GameLoadService gameLoadService;

    public GameService(BoardDisplayer boardDisplayer, ConsoleService consoleService, Board board,
                       PlayerMoveService playerMoveService, BoardService boardService, ComputerPlayerService computerPlayerService, GameSaveService gameSaveService, GameLoadService gameLoadService) {
        this.boardDisplayer = boardDisplayer;
        this.consoleService = consoleService;
        this.board = board;
        this.playerMoveService = playerMoveService;
        this.boardService = boardService;
        this.computerPlayerService = computerPlayerService;
        this.gameSaveService = gameSaveService;

        this.gameLoadService = gameLoadService;
    }


    public void startGame(Player humanPlayer, Player computerPlayer){


        while (true){
            consoleService.print("Ha el szeretnéd menteni a játék állását írd be a 'save' parancsot! ");

            //első lépés középre helyezése
            int center = board.getSize() / 2 ;
            humanPlayer.setPosition(center, center);
            boardService.makeMove(humanPlayer);
            //megjelenítés
            boardDisplayer.displayBoard(board);

            try {
                //1. bekért lépés
                playerMoveService.readPlayerMove(humanPlayer, board.getSize());
            } catch (SaveCommandExceptionService e) {
                gameSaveService.saveBoardToFile(board, "mentes.txt");
                consoleService.print("Játék elmentve!");
                continue;
            }
        //logika
            if (!boardService.makeMove(humanPlayer)) {
                consoleService.print("Ez a mező foglalt!\n");
                continue;
            }


            if (playerMoveService.checkWin(board, humanPlayer.getRow(), humanPlayer.getCol(), humanPlayer.getSymbol())){
                consoleService.print("Congratulation! You Win!");
                boardDisplayer.displayBoard(board);
                return;
            }

            //ComputerPlayer Move
            consoleService.print("ComputerPlayer gondolkodik...");
            computerPlayerService.makeMove(computerPlayer, humanPlayer, board);
            boardService.makeMove(computerPlayer);
            boardDisplayer.displayBoard(board);

            if(playerMoveService.checkWin(board, computerPlayer.getRow(), computerPlayer.getCol(), computerPlayer.getSymbol())){
                consoleService.print("A ComputerPlayer nyert!");
                return;
            }

        }
    }


}
