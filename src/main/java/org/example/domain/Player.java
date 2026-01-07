package org.example.domain;

public abstract class Player {

    protected final Symbol symbol;

    protected Player(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public abstract Move nextMove(Board board);
}
