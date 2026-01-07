package org.example.service;

import org.example.domain.Player;

public class PlayerMoveService {
    private final ConsoleService consoleService;

    public PlayerMoveService(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    public void readPlayerMove(Player player, int boardSize){
        int row = readRow(boardSize);
        int col = readColumn(boardSize);
        
        player.setPosition(row, col);
    }

    private int readColumn(int boardSize) {
        while (true) {
            String input = consoleService.readStringFromConsole("Add meg az oszlop betűjét (A - " + (char)('A' + boardSize - 1) + "): ").toUpperCase();
            if (input.length() != 1){
                consoleService.print("Egyetlen egy betűt adj meg!");
                continue;
            }
            char c = input.charAt(0);
            int col = c - 'A';

            if (col >= 0 && col < boardSize){
                return col;
            }
            consoleService.print("Hibás betű!");
        }
    }

    private int readRow(int boardSize) {
        while (true){
            String input = consoleService.readStringFromConsole("Add meg a sor számát (0 - " + (boardSize - 1) + "):");
            try{
                int row = Integer.parseInt(input);
                if ( row >= 0 && row < boardSize){
                    return row;
                }
                consoleService.print("Hibás tartomány!");
            } catch (NumberFormatException e) {
                consoleService.print("Egy számot adj meg!");
            }
        }

    }


}
