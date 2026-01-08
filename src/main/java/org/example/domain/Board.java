package org.example.domain;

public class Board {

    private final Symbol[][] fields;

    public Board(int size) {
        fields = new Symbol[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                fields[row][col] = Symbol.EMPTY;
            }
        }
    }

    public int getSize() {
        return fields.length;
    }

    public Symbol get(int row, int col) {
        return fields[row][col];
    }

    public boolean placeSymbol(int row, int col, Symbol symbol) {
        if (fields[row][col] != Symbol.EMPTY) {
            return false;
        }
        fields[row][col] = symbol;
        return true;
    }
    public void set(int row, int col, Symbol symbol) {
        fields[row][col] = symbol;
    }

}
