package org.example.domain;



public abstract class Player {
    private final int horizontalCoordinate;
    private final int verticalCoordinate;

    protected Player(int horizontalCoordinate, int verticalCoordinate) {
        this.horizontalCoordinate = horizontalCoordinate;
        this.verticalCoordinate = verticalCoordinate;
    }


    public int getHorizontalCoordinate() {
        return horizontalCoordinate;
    }

    public int getVertcalCoordinate() {
        return getHorizontalCoordinate();
    }
}