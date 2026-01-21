package org.example.service;

import java.util.Random;

import org.example.domain.Board;
import org.example.domain.Player;
import org.example.domain.Symbol;
import org.springframework.stereotype.Service;

@Service

public class ComputerPlayerService {
    private final Random random = new Random();
    private final PlayerMoveService playerMoveService;
    private final MoveRuleService moveRuleService;

    public ComputerPlayerService(final PlayerMoveService playerMoveService,
                                 final MoveRuleService moveRuleService) {
        this.playerMoveService = playerMoveService;
        this.moveRuleService = moveRuleService;
    }

    @SuppressWarnings({"PMD.LongVariable", "PMD.OnlyOneReturn"})
    public void makeMove(final Player computerPlayer, final Player humanPlayer, final Board board) {
        final Symbol computerPlayerSymbol = computerPlayer.getSymbol();
        final Symbol humanPlayerSymbol = humanPlayer.getSymbol();

        final int size = board.getSize();
        final int originalRow = computerPlayer.getRow();
        final int originalCol = computerPlayer.getCol();

        //Ha nincs symbólum, akkor első lépés random
        if (!moveRuleService.hasAnySymbol(board, computerPlayerSymbol)) {
            makeRandomMove(computerPlayer, board);
            return;
        }

        //Try win
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.get(row, col) == Symbol.EMPTY && moveRuleService.isAdjecentToSymbol(board, row, col, computerPlayerSymbol)) {

                    board.set(row, col, computerPlayerSymbol);
                    computerPlayer.setPosition(row, col);

                    if (playerMoveService.checkWin(board, computerPlayer.getRow(), computerPlayer.getCol(), computerPlayer.getSymbol())) {
                        return; //wining move
                    }
                    board.set(row, col, Symbol.EMPTY);
                    computerPlayer.setPosition(originalRow, originalCol);
                }
            }
        }
        //Try block
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.get(row, col) == Symbol.EMPTY) {

                    board.set(row, col, humanPlayerSymbol);
                    humanPlayer.setPosition(row, col);

                    if (playerMoveService.checkWin(board, humanPlayer.getRow(), humanPlayer.getCol(), humanPlayer.getSymbol())) {
                        board.set(row, col, computerPlayerSymbol);
                        computerPlayer.setPosition(row, col);
                        return; //block
                    }
                    board.set(row, col, Symbol.EMPTY);
                }
            }
        }
        //Random move
        makeRandomAdjacentMove(computerPlayer, board);
    }

     private void makeRandomMove(final Player computerPlayer, final Board board) {
         final int size = board.getSize();

        while (true) {
            final int row = random.nextInt(size);
            final int col = random.nextInt(size);

          if (board.get(row, col) == Symbol.EMPTY) {
              board.set(row, col, computerPlayer.getSymbol());
              computerPlayer.setPosition(row, col);
              return;
              }
        }
    }


    private void makeRandomAdjacentMove(final Player computerPlayer, final Board board) {
        final int size = board.getSize();

        while (true) {
            final int row = random.nextInt(size);
            final int col = random.nextInt(size);

            if (board.get(row, col) == Symbol.EMPTY && moveRuleService.isAdjecentToSymbol(board, row, col, computerPlayer.getSymbol())) {
                board.set(row, col, computerPlayer.getSymbol());
                computerPlayer.setPosition(row, col);
                return;
            }
        }
    }


}
