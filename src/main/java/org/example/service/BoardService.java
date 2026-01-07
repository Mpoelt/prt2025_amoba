package org.example.service;

import org.example.domain.Board;
import org.example.domain.Move;
import org.example.domain.Symbol;

public class BoardService {
    private final Board board;


    public BoardService(Board board) {
        this.board = board;
    }

    public boolean isValidMove(Move move){
        return move.row() >= 0 && move.row() < board.getSize() &&
                move.col() >= 0 && move.col() < board.getSize() &&
                board.get(move.row(), move.col()) == Symbol.EMPTY;
    }

    public void place(Move move, Symbol symbol){
        board.set(move.row(), move.col(), symbol);
    }

    public boolean isFull() {
        for(int i = 0; i < board.getSize(); i++){
            for(int j = 0; j < board.getSize(); j++){
                if (board.get(i,j) == Symbol.EMPTY){
                    return false;
                }
            }
        }
        return true;
    }
    public static void boardPrint(Board board){
        for(int i = 0; i < board.getSize(); i++){
            for(int j = 0; j < board.getSize(); j++){
                Symbol s = board.get(i,j);
                System.out.print(s == Symbol.EMPTY ? ". " : s + " ");
            }
            System.out.println();
        }
    }
}


