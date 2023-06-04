package com.dt183g.project.mvc.models.types;

import com.dt183g.project.utility.MazeReader;

public class MazeVertex extends MazePoint implements Comparable<MazeVertex> {
    public enum CompareMode {
        DistanceOnly,
        DirectDistanceOnly,
        Combined
    }

    /* ********************************************************************** */

    protected CompareMode compareMode;

    protected MazeVertex previous;
    protected int distance;
    protected double directDistance;

    /* ********************************************************************** */

    protected MazeVertex(int x, int y, MazeReader maze, boolean isImageCoordinates, CompareMode compareMode) {
        super(x, y, maze, isImageCoordinates);

        this.previous = null;
        this.distance = 0;
        this.compareMode = compareMode;
    }

    public MazeVertex makeNext(int x, int y) {
        MazeVertex vertex = MazeVertex.fromMaze(x, y, maze, compareMode);
        vertex.setPrevious(this);
        return vertex;
    }

    public MazeVertex makeNext(int x, int y, MazeVertex end) {
        MazeVertex vertex = MazeVertex.fromMaze(x, y, maze, compareMode);
        vertex.setPrevious(this);
        vertex.setDirectDistanceUsing(end);
        return vertex;
    }

    public static MazeVertex fromMaze(int x, int y, MazeReader maze, CompareMode compareMode) {
        return new MazeVertex(x, y, maze, false, compareMode);
    }

    public static MazeVertex fromMaze(int x, int y, MazeReader maze) {
        return fromMaze(x, y, maze, CompareMode.DistanceOnly);
    }

    public static MazeVertex fromImage(int x, int y, MazeReader maze, CompareMode compareMode) {
        return new MazeVertex(x, y, maze, true, compareMode);
    }

    public static MazeVertex fromImage(int x, int y, MazeReader maze) {
        return fromImage(x, y, maze, CompareMode.DistanceOnly);
    }

    public static MazeVertex fromPoint(MazePoint point, CompareMode compareMode) {
        return new MazeVertex(point.getMazeX(), point.getMazeY(), point.getBackingMaze(), false, compareMode);
    }

    public static MazeVertex fromPoint(MazePoint point) {
        return fromPoint(point, CompareMode.DistanceOnly);
    }

    /* ********************************************************************** */

    public CompareMode getCompareMode() {
        return this.compareMode;
    }

    public void setCompareMode(CompareMode compareMode) {
        this.compareMode = compareMode;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getDirectDistance() {
        return this.directDistance;
    }

    public void setDirectDistance(double directDistance) {
        this.directDistance = directDistance;
    }

    public void setDirectDistanceUsing(MazePoint end) {
        this.directDistance = proximityTo(end);
    }

    public MazeVertex getPrevious() {
        return this.previous;
    }

    public void setPrevious(MazeVertex previous) {
        this.previous = previous;
        this.distance = previous.getDistance();
    }

    /* ********************************************************************** */

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(MazeVertex other) {
        return switch(compareMode) {
            case DistanceOnly -> Integer.compare(this.getDistance(), other.getDistance());
            case DirectDistanceOnly -> Double.compare(this.getDirectDistance(), other.getDirectDistance());
            case Combined -> Double.compare(
                    this.getDistance() + this.getDirectDistance(),
                    other.getDistance() + this.getDirectDistance()
                );
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s[x=%d,y=%d]", getClass().getSimpleName(), mazeX, mazeY);
    }
}
