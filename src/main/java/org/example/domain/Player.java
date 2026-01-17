package org.example.domain;



public abstract class Player {
    private int row;
    private int col;
    private final Symbol symbol;
    private final String name;

    protected Player(Symbol symbol, String name){
        this.symbol = symbol;
        this.name = name;
    }

    public String getName() {
        return name;
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
