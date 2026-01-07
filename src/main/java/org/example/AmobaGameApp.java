package org.example;


import org.example.display.BoardDisplayer;
import org.example.domain.Board;
import org.example.init.BoardInit;
import org.example.service.BoardService;
import org.example.service.ConsoleService;

import java.util.Scanner;

public class AmobaGameApp
{
    public static void main(String[] args){
        ConsoleService consoleService = new ConsoleService();
        Board board = BoardInit.createEmptyBoard(consoleService.readIntFromConsole("Add meg a pálya méretét: "));
        BoardService boardService = new BoardService(board);
        BoardDisplayer.boardPrint(board);

    }


}
