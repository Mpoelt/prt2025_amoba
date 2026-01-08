package org.example.service;

import org.example.domain.Board;
import org.example.domain.Player;

public class BoardService {
    private final Board board;


    public BoardService(Board board) {
        this.board = board;
    }

    public boolean makeMove(Player player){
        return board.placeSymbol(player.getRow(), player.getCol(), player.getSymbol());
    }


}


