package org.example.display;
import org.example.domain.Board;
import org.example.domain.Player;
import org.example.domain.Symbol;
import org.example.service.ConsoleService;
import org.springframework.stereotype.Component;

@Component
public class BoardDisplayer {

    private final ConsoleService consoleService;

    public BoardDisplayer(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    public void displayBoard(Board board){
        int size = board.getSize();
        StringBuilder stringBuilder = new StringBuilder();

        //Fejléc
        stringBuilder.append("   ");
        for (int col = 0; col < size; col++){
            stringBuilder.append((char) ('A' + col)).append(" ");
        }
        stringBuilder.append("\n");

        //Tábla
        for (int row = 0; row < size; row++){
            stringBuilder.append(String.format("%2d ", row));
            for (int col = 0; col < size; col++){
                Symbol symbol = board.get(row, col);
                stringBuilder.append(symbol == Symbol.EMPTY ? ". " : symbol + " ");
            }
            stringBuilder.append("\n");
        }
        consoleService.print("\n" + stringBuilder);
    }


    }


