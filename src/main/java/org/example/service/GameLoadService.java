package org.example.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.example.domain.Board;
import org.example.domain.Symbol;
import org.springframework.stereotype.Service;

@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.PreserveStackTrace"})
@Service
public class GameLoadService {

    public Board loadBoardFromFile(final String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            //Méret beolvasása
            final String sizeLine = reader.readLine();
            final int size = Integer.parseInt(sizeLine.split(" ")[1]);

            final Board board = new Board(size);

            //fejléc átugrása
            reader.readLine();

            int row = 0;
            String line;

            //sorok beolvasása
            while ((line = reader.readLine()) != null && row < size) {
                final String[] parts = line.trim().split(" ");

                int col = 0;
                for (int i = 1; i < parts.length && col < size; i++) {
                    final Symbol symbol = charToSymbol(parts[i].charAt(0));
                    board.set(row, col, symbol);
                    col++;
                }

                row++;

            }
            return board;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Hiba a fájl betöltésekor!");


        }
    }

    @SuppressWarnings("PMD.ShortVariable")
    private Symbol charToSymbol(final char c) {
        return switch (c) {
            case 'X' -> Symbol.X;
            case 'O' -> Symbol.O;
            default -> Symbol.EMPTY;
        };
    }


}
