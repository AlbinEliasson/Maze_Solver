package com.dt183g.project.mvc.models;

public class Vertex {
    private int xCoordinate;
    private int yCoordinate;

    public Vertex(int x, int y) {
        // TODO Dont know if we need this class, if not delete
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int x) {
        xCoordinate = x;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int y) {
        yCoordinate = y;
    }
}
