package com.dt183g.project.mvc.models;

public class Vertex {
    private int xCoordinate;
    private int yCoordinate;
    private int distance;

    public Vertex(int distance, int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.distance = distance;
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

    public void setDistance(int distance) {this.distance = distance;}

    public int getDistance() {return distance;}
}
