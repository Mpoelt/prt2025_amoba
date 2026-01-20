package org.example.service;

import org.example.domain.Board;
import org.example.domain.Player;
import org.example.domain.Symbol;
import org.springframework.stereotype.Service;

@SuppressWarnings({"PMD.ShortVariable", "PMD.LiteralsFirstInComparisons"})
@Service
public class PlayerMoveService {
    private final ConsoleService consoleService;

    public PlayerMoveService(final ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    public void readPlayerMove(final Player player, final int boardSize) {
        final int row = readRow(boardSize);
        final int col = readColumn(boardSize);
        
        player.setPosition(row, col);
    }


    public boolean checkWin(final Board board, final int row, final int col, final Symbol symbol) {

        return checkHorizontal(board, row, col, symbol)
              || checkVertical(board, row, col, symbol)
              || checkDiagonalDown(board, row, col, symbol)
              || checkDiagonalUp(board, row, col, symbol);
    }


    private int readColumn(final int boardSize) {
        while (true) {
            final String input = consoleService.readStringFromConsole(
                    "Add meg az oszlop betűjét (A - " + (char) ('A' + boardSize - 1) + "): ").toUpperCase();
            if (input.length() != 1) {
                consoleService.print("Egyetlen egy betűt adj meg!");
                continue;
            }
            final char c = input.charAt(0);
            final int col = c - 'A';

            if (col >= 0 && col < boardSize) {
                return col;
            }
            consoleService.print("Hibás betű!");
        }
    }

    private int readRow(final int boardSize) {
        while (true) {
            final String input = consoleService.readStringFromConsole(
                    "Add meg a sor számát (0 - " + (boardSize - 1) + "):");
            if (input.equalsIgnoreCase("save")) {
                throw new SaveCommandExceptionService();
            }

            try {
                final int row = Integer.parseInt(input);
                if (row >= 0 && row < boardSize) {
                    return row;
                }
                consoleService.print("Hibás tartomány!");
            } catch (NumberFormatException e) {
                consoleService.print("Egy számot adj meg!");
            }
        }

    }

    private boolean checkHorizontal(final Board board, final int row, final int col, final Symbol symbol) {
         int count = 1;

        //check left
        for (int c = col - 1; c >= 0; c--) {
            if (board.get(row, c) == symbol) {
                count++;
            } else {
                break;
            }
        }

        //check right
        for (int c = col + 1; c < board.getSize(); c++) {
            if (board.get(row, c) == symbol) {
                count++;
            } else {
                break;
            }
        }

        return count >= 4;
    }


    private boolean checkVertical(final Board board, final int row, final int col, final Symbol symbol) {
        int count = 1;

        // check up
        for (int r = row - 1; r >= 0; r--) {
            if (board.get(r, col) == symbol) {
                count++;
            } else {
                break;
            }
        }

        //check down
        for (int r = row + 1; r < board.getSize(); r++) {
            if (board.get(r, col) == symbol) {
                count++;
            } else {
                break;
            }
        }
        return count >= 4;
    }

    private boolean checkDiagonalDown(final Board board, final int row, final int col, final Symbol symbol) {
       int count = 1;

        //left-up
        int r = row - 1;
        int c = col - 1;

        while (r >= 0 && c >= 0 && board.get(r, c) == symbol) {
            count++;
            r--;
            c--;
        }

        //right-down
        r = row + 1;
        c = col + 1;
        while (r < board.getSize() && c < board.getSize() && board.get(r, c) == symbol) {
            count++;
            r++;
            c++;
        }
        return count >= 4;
    }

    private boolean checkDiagonalUp(final Board board, final int row, final int col, final Symbol symbol) {
        int count = 1;

        //left-down
        int r = row + 1;
        int c = col - 1;
        while (r < board.getSize() && c >= 0 && board.get(r, c) == symbol) {
            count++;
            r++;
            c--;
        }

        //right-up
        r = row - 1;
        c = col + 1;

        while (r >= 0 && c < board.getSize() && board.get(r, c) == symbol) {
            count++;
            r--;
            c++;
        }
        return count >= 4;
    }
}
