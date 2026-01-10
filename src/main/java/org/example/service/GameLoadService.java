package org.example.service;

import org.example.domain.Board;
import org.example.domain.Symbol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class GameLoadService {

    public Board loadBoardFromFile( String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

           //Méret beolvasása
            String sizeLine = reader.readLine();
            int size = Integer.parseInt(sizeLine.split(" ")[1]);

            Board board = new Board(size);

            //fejléc átugrása
            reader.readLine();

            int row = 0;
            String line;

            //sorok beolvasása
            while ((line = reader.readLine()) != null && row < size){
                String[] parts = line.trim().split(" ");

                int col = 0;
                for (int i = 1; i < parts.length && col < size; i++){
                    Symbol symbol = charToSymbol(parts[i].charAt(0));
                    board.set(row, col, symbol);
                    col++;
                }

                row++;

            }
            return board;

        } catch (IOException | NumberFormatException e){
            throw new RuntimeException("Hiba a fájl betöltésekor!");


        }
    }

    private Symbol charToSymbol(char c) {
        return switch (c) {
            case 'X' -> Symbol.X;
            case 'O' -> Symbol.O;
            default -> Symbol.EMPTY;
        };
    }


}
