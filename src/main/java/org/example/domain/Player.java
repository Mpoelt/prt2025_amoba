package org.example.domain;



public class Player {
    private int row;
    private int col;
    private final Symbol symbol;
    private final String name;

    protected Player(final Symbol symbol, final String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPosition(final int row, final int col) {
        this.row = row;
        this.col = col;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
