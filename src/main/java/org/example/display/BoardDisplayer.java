package org.example.display;
import org.example.domain.Board;
import org.example.domain.Player;
import org.example.service.ConsoleService;

public class BoardDisplayer {

    private final ConsoleService consoleService;

    public BoardDisplayer(ConsoleService consoleService, Player player) {
        this.consoleService = consoleService;
    }

    public void displayBoard(Board board, Player player){
        int size = board.getSize();
        char[][] matrix =emptySetup(size);
        addPlayerToDisplay(player, matrix, size);
        consoleService.print("\n" + getPrettyPrint(size, matrix));
    }



    private char[][] emptySetup(int boardSize) {
        char[][] matrix = new char[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                matrix[row][col] = '.';
            }
        }
        return matrix;
    }


    private String getPrettyPrint(int size, char[][] matrix) {
        StringBuilder sb = new StringBuilder();

        // fejléc
        sb.append("   "); // 3 karakter hely a sorindexnek
        for (int j = 0; j < size; j++) {
            sb.append((char) ('A' + j)).append(" ");
        }
        sb.append("\n");

        // tartalom
        for (int row = 0; row < size; row++) {
            sb.append(String.format("%2d ", row));
            for (int col = 0; col < size; col++) {
                sb.append(matrix[row][col]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }


    private void addPlayerToDisplay(Player player, char[][] matrix, int size){
        int horizontalCoordinate = player.getHorizontalCoordinate();
        int verticalCoordinate = player.getVertcalCoordinate();
        matrix[horizontalCoordinate][verticalCoordinate] = 'O';

        }
    }

















}
