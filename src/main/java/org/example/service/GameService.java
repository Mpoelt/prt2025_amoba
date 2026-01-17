package org.example.service;

import org.example.database.entity.HighScore;
import org.example.display.BoardDisplayer;
import org.example.domain.*;
import org.example.init.PlayerInit;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final ConsoleService consoleService;
    private final PlayerInit playerInit;
    private final LoadGameDecider loadGameDecider;
    private final BoardDisplayer boardDisplayer;
    private final PlayerMoveService playerMoveService;
    private final BoardService boardService;
    private final ComputerPlayerService computerPlayerService;
    private final GameSaveService gameSaveService;
    //private final GameLoadService gameLoadService;
    private final HighScoreService highScoreService;

    private Board board;
    private Player humanPlayer;
    private ComputerPlayer computerPlayer;

    public GameService(BoardDisplayer boardDisplayer, ConsoleService consoleService, PlayerInit playerInit, LoadGameDecider loadGameDecider,
                       PlayerMoveService playerMoveService, BoardService boardService, ComputerPlayerService computerPlayerService, GameSaveService gameSaveService, HighScoreService highScoreService) {
        this.boardDisplayer = boardDisplayer;
        this.consoleService = consoleService;
        this.playerInit = playerInit;
        this.loadGameDecider = loadGameDecider;
        this.playerMoveService = playerMoveService;
        this.boardService = boardService;
        this.computerPlayerService = computerPlayerService;
        this.gameSaveService = gameSaveService;

        this.highScoreService = highScoreService;
    }


    public void startGame(){
        board = loadGameDecider.loadBoard();
        humanPlayer = playerInit.createHumanPlayer();
        computerPlayer = new ComputerPlayer(Symbol.O, "Computer");

        HighScore highScore = highScoreService.findByPlayerNamerOrCreate(humanPlayer.getName());
        consoleService.printWithPlayerName("Hello {}, A játék elkezdődött, ez a te pályád: ", humanPlayer.getName());

        //első lépés középre helyezése
        int center = board.getSize() / 2 ;
        humanPlayer.setPosition(center, center);
        boardService.makeMove(board, humanPlayer);
        //megjelenítés
        boardDisplayer.displayBoard(board);
        while (true){
            consoleService.print("Ha el szeretnéd menteni a játék állását írd be a 'save' parancsot! ");

            try {
                //1. bekért lépés
                playerMoveService.readPlayerMove(humanPlayer, board.getSize());
            } catch (SaveCommandExceptionService e) {
                gameSaveService.saveBoardToFile(board, "mentes.txt");
                consoleService.print("Játék elmentve!");
                continue;
            }
        //logika
            if (!boardService.makeMove(board, humanPlayer)) {
                consoleService.print("Ez a mező foglalt!\n");
                continue;
            }


            if (playerMoveService.checkWin(board, humanPlayer.getRow(), humanPlayer.getCol(), humanPlayer.getSymbol())){
                highScore.setGamesWon(highScore.getGamesWon() + 1);
                highScoreService.save(highScore);
                consoleService.print("Congratulation! You Win!");
                boardDisplayer.displayBoard(board);
                return;
            }

            //ComputerPlayer Move
            consoleService.print("ComputerPlayer gondolkodik...");
            computerPlayerService.makeMove(computerPlayer, humanPlayer, board);
            boardService.makeMove(board, computerPlayer);
            boardDisplayer.displayBoard(board);

            if(playerMoveService.checkWin(board, computerPlayer.getRow(), computerPlayer.getCol(), computerPlayer.getSymbol())){
                consoleService.print("A ComputerPlayer nyert!");
                return;
            }

        }
    }


}
