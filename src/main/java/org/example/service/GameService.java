package org.example.service;

import org.example.database.entity.HighScore;
import org.example.display.BoardDisplayer;
import org.example.domain.Board;
import org.example.domain.ComputerPlayer;
import org.example.domain.Player;
import org.example.domain.Symbol;
import org.example.init.PlayerInit;
import org.springframework.stereotype.Service;

@SuppressWarnings({"PMD.LongVariable"})
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
    private final HighScoreService highScoreService;

    private Board board;
    private Player humanPlayer;
    private ComputerPlayer computerPlayer;

    public GameService(final BoardDisplayer boardDisplayer,
                       final ConsoleService consoleService,
                       final PlayerInit playerInit,
                       final LoadGameDecider loadGameDecider,
                       final PlayerMoveService playerMoveService,
                       final BoardService boardService,
                       final ComputerPlayerService computerPlayerService,
                       final GameSaveService gameSaveService,
                       final HighScoreService highScoreService) {

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

    @SuppressWarnings({"PMD.LiteralsFirstInComparisons"})
    public void startGame() {
        humanPlayer = playerInit.createHumanPlayer();
        computerPlayer = new ComputerPlayer(Symbol.O, "Computer");

        final HighScore highScore = highScoreService.findByPlayerNameOrCreate(humanPlayer.getName());

        while (true) {
            final boolean playerWon = playerSingleGame(highScore);

            if (playerWon) {
                highScore.setGamesWon(highScore.getGamesWon() + 1);
                highScoreService.save(highScore);
                consoleService.print("Nyertél! Összpontszám: " + highScore.getGamesWon());
            }
            final String answer = consoleService.readStringFromConsole("Szeretné új játékot? (yes/no)");
            if (!answer.equalsIgnoreCase("yes")) {
                consoleService.print("Kilépés. Viszlát!");
                return;
            }

        }
    }

    @SuppressWarnings({"PMD.UnusedFormalParameter", "PMD.OnlyOneReturn"})
    private boolean playerSingleGame(final HighScore highScore) {
        board = loadGameDecider.loadBoard();
        consoleService.printWithPlayerName("Hello {}, új játék indul!", humanPlayer.getName());

        //első lépés középre helyezése
        final int center = board.getSize() / 2;
        humanPlayer.setPosition(center, center);
        boardService.makeMove(board, humanPlayer);
        //megjelenítés
        boardDisplayer.displayBoard(board);

        consoleService.print("ComputerPlayer gondolkodik...");
        computerPlayerService.makeMove(computerPlayer, humanPlayer, board);
        boardService.makeMove(board, computerPlayer);
        boardDisplayer.displayBoard(board);


        while (true) {
            try {
                playerMoveService.readPlayerMove(humanPlayer, board.getSize());
            } catch (SaveCommandExceptionService e) {
                gameSaveService.saveBoardToFile(board, "mentes.txt");
                consoleService.print("Játék elmentve!");
                continue;
            }

            if (!boardService.makeMove(board, humanPlayer)) {
                consoleService.print("Ez a mező foglalt!\n");
                continue;
            }

            boardDisplayer.displayBoard(board);

            if (playerMoveService.checkWin(board, humanPlayer.getRow(), humanPlayer.getCol(), humanPlayer.getSymbol())) {
                consoleService.print("Gratulálok! Nyertél!");
                return true;
                }

            //computer move
            consoleService.print("ComputerPlayer gondolkodik...");
            computerPlayerService.makeMove(computerPlayer, humanPlayer, board);
            boardService.makeMove(board, computerPlayer);
            boardDisplayer.displayBoard(board);

            if (playerMoveService.checkWin(board, computerPlayer.getRow(), computerPlayer.getCol(), computerPlayer.getSymbol())) {
                consoleService.print("A ComputerPlayer nyert!");
                return false;
            }
        }
    }
}
