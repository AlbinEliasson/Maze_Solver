package com.dt183g.project.mvc.models;

public class Vertex implements Comparable<Vertex> {
    private int xCoordinate;
    private int yCoordinate;
    private int distance;
    private double f;
    private Vertex previous;


    public Vertex(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
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

    @Override
    public int compareTo(Vertex o) {
        return Double.compare(this.getF(), o.getF());
    }
}
