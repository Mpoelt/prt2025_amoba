package org.example.domain;

public class Board {

    private final Symbol[][] fields;

    public Board(final int size) {
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

    public Symbol get(final int row, final  int col) {
        return fields[row][col];
    }

@SuppressWarnings("PMD.OnlyOneReturn")
    public boolean placeSymbol(final int row, final int col, final Symbol symbol) {
        if (fields[row][col] != Symbol.EMPTY) {
            return false;
        }
        fields[row][col] = symbol;
        return true;
    }

    public void set(final int row, final int col, final Symbol symbol) {
        fields[row][col] = symbol;
    }

}
