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

    public GameService(BoardDisplayer boardDisplayer, ConsoleService consoleService, Board board,
                       PlayerMoveService playerMoveService, BoardService boardService, ComputerPlayerService computerPlayerService) {
        this.boardDisplayer = boardDisplayer;
        this.consoleService = consoleService;
        this.board = board;
        this.playerMoveService = playerMoveService;
        this.boardService = boardService;
        this.computerPlayerService = computerPlayerService;
    }


    public void startGame(Player humanPlayer, Player computerPlayer){

        while (!playerMoveService.checkWin(board, humanPlayer)){
        //1. lépés
            playerMoveService.readPlayerMove(humanPlayer, board.getSize());
        //logika
            if (!boardService.makeMove(humanPlayer)) {
                consoleService.print("Ez a mező foglalt!\n");
            }
        //megjelenítés
            boardDisplayer.displayBoard(board);

            consoleService.print("ComputerPlayer gondolkodik...");
            computerPlayerService.makeMove(computerPlayer, humanPlayer, board);
            boardService.makeMove(computerPlayer);
            boardDisplayer.displayBoard(board);

            if(playerMoveService.checkWin(board, computerPlayer)){
                consoleService.print("A ComputerPlayer nyert!");
                break;
            }

        }

        consoleService.print("Congratulation! You Win!");
    }


}
