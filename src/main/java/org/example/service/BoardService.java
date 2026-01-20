package org.example.service;

import org.example.domain.Board;
import org.example.domain.Player;
import org.springframework.stereotype.Service;

@SuppressWarnings("PMD.AtLeastOneConstructor")
@Service
public class BoardService {


    public boolean makeMove(final Board board, final Player player) {
        return board.placeSymbol(player.getRow(), player.getCol(), player.getSymbol());
    }



}


