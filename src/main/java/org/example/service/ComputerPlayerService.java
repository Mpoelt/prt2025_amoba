package org.example.service;

import org.example.domain.Board;
import org.example.domain.Player;
import org.example.domain.Symbol;

import java.util.Random;

public class ComputerPlayerService {
    private final Random random = new Random();
    private final PlayerMoveService playerMoveService;

    public ComputerPlayerService(PlayerMoveService playerMoveService) {
        this.playerMoveService = playerMoveService;
    }

    public void makeMove(Player computerPlayer, Player humanPlayer, Board board){
        Symbol computerPlayerSymbol = computerPlayer.getSymbol();
        Symbol humanPlayerSymbol = humanPlayer.getSymbol();

        int size = board.getSize();
        int originalRow = computerPlayer.getRow();
        int originalCol = computerPlayer.getCol();

        //Try win
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                if (board.get(row, col) == Symbol.EMPTY){

                    board.set(row, col, computerPlayerSymbol);
                    computerPlayer.setPosition(row, col);

                    if (playerMoveService.checkWin(board, computerPlayer.getRow(), computerPlayer.getCol(), computerPlayer.getSymbol())){
                        return; //wining move
                    }
                    board.set(row, col, Symbol.EMPTY);
                    computerPlayer.setPosition(originalRow, originalCol);
                }
            }
        }
        //Try block
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                if (board.get(row, col) == Symbol.EMPTY){

                    board.set(row, col, humanPlayerSymbol);
                    humanPlayer.setPosition(row, col);

                    if (playerMoveService.checkWin(board, humanPlayer.getRow(), humanPlayer.getCol(), humanPlayer.getSymbol())){
                        board.set(row, col, computerPlayerSymbol);
                        computerPlayer.setPosition(row, col);
                        return; //block
                    }
                    board.set(row, col, Symbol.EMPTY);
                }
            }
        }
        //Random move
        makeRandommove(computerPlayer, board);
    }

    private void makeRandommove(Player computerPlayer, Board board) {
        int size = board.getSize();

        while (true){
          int row = random.nextInt(size);
          int col = random.nextInt(size);

          if (board.get(row, col) == Symbol.EMPTY){
              board.set(row, col, computerPlayer.getSymbol());
              computerPlayer.setPosition(row, col);
              return;
              }

        }


    }

}
