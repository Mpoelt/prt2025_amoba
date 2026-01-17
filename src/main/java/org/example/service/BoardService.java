package org.example.service;

import org.example.domain.Board;
import org.example.domain.Player;
import org.springframework.stereotype.Service;

@Service
public class BoardService {


    public boolean makeMove(Board board, Player player){
        return board.placeSymbol(player.getRow(), player.getCol(), player.getSymbol());
    }



}


