package org.example.service;

import org.example.domain.Board;
import org.example.domain.Symbol;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class GameSaveService {
    public void saveBoardToFile(Board board, String fileName){

        try (FileWriter writer = new FileWriter(fileName)){
            int size = board.getSize();

            writer.write("SIZE " + size + "\n");

            //fejléc (A B C ...)
            writer.write("   ");
            for (int c = 0; c < size; c++){
                writer.write((char) ('A' + c) + " ");
            }
            writer.write("\n");

            //tábla tartalma
            for (int r = 0; r < size; r++){
                writer.write(String.format("%2d ", r));
                for (int c = 0; c < size; c++){
                    Symbol symbol = board.get(r,c);
                    writer.write(symbolToChar(symbol) + " ");
                }
                writer.write("\n");
            }
            writer.flush();

        } catch (IOException e){
            System.out.println("Hiba a fájl megírásakor!");
            e.printStackTrace();
        }

    }

    private char symbolToChar(Symbol symbol) {
            return switch (symbol){
                case O -> 'O';
                case X -> 'X';
                case EMPTY -> '.';
            };
    }
}
