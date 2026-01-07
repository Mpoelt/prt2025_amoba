package org.example.domain;

public class Board {

    private final int size;
    private final Symbol[][] fields;

    public Board(int size) {
        this.size = size;
        this.fields = new Symbol[size][size];
    }

    public int getSize() {
        return size;
    }

    public Symbol get(int row, int col){
        return fields[row][col];
    }

    public void set(int row, int col, Symbol symbol){
        fields[row][col] = symbol;
    }


}