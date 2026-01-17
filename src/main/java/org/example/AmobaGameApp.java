package org.example;


import org.example.database.repository.HighScoreRepository;
import org.example.display.BoardDisplayer;
import org.example.domain.*;
import org.example.init.PlayerInit;
import org.example.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AmobaGameApp
{
    public static void main(String[] args){
 /*       ConsoleService consoleService = new ConsoleService();
        PlayerInit playerInit = new PlayerInit(consoleService);
        Player humanPlayer = playerInit.createHumanPlayer();
        Player computerPlayer = new ComputerPlayer(Symbol.O, "Computer");
        GameLoadService gameLoadService = new GameLoadService();
        LoadGameDecider loadGameDecider = new LoadGameDecider(consoleService, gameLoadService);
        Board board = loadGameDecider.loadBoard();


        PlayerMoveService playerMoveService = new PlayerMoveService(consoleService);
        BoardService boardService = new BoardService(board);
        BoardDisplayer boardDisplayer = new BoardDisplayer(consoleService);
        MoveRuleService moveRuleService = new MoveRuleService();
        ComputerPlayerService computerPlayerService = new ComputerPlayerService(playerMoveService, moveRuleService);
        GameSaveService gameSaveService = new GameSaveService();
        HighScoreRepository highScoreRepository;
        HighScoreService highScoreService = new HighScoreService(consoleService, HighScoreRepository );
        GameService gameService = new GameService(boardDisplayer, consoleService, board, playerMoveService,
                boardService, computerPlayerService, gameSaveService, gameLoadService, highScoreService );

    gameService.startGame(humanPlayer, computerPlayer);
*/

        ApplicationContext context = SpringApplication.run(AmobaGameApp.class, args);

        GameService gameService = context.getBean(GameService.class);
        gameService.startGame();

    }


}
