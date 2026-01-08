package org.example.domain;



public abstract class Player {
    private int row;
    private int col;
    private final Symbol symbol;

    protected Player(Symbol symbol){
        this.symbol = symbol;
    }

    public void setPosition(int row, int col) {
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
