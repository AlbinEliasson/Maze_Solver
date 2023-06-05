package com.dt183g.project.mvc.models.types;

import com.dt183g.project.utility.MazeReader;

/**
 * A class representing a point in the maze, but which contains extra
 * functionality useful when performing calculations using the values.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
public class MazeVertex extends MazePoint implements Comparable<MazeVertex> {
    /**
     * Modes in which maze vertexes can be compared.
     */
    public enum CompareMode {
        /**
         * Compare only the "distance" property.
         */
        DistanceOnly,
        /**
         * Compare only the "direct distance" property.
         */
        DirectDistanceOnly,
        /**
         * Compare both the "distance" and "direct distance" properties by
         * summing them before comparison.
         */
        Combined
    }

    /* ********************************************************************** */

    protected CompareMode compareMode;

    protected MazeVertex previous;
    protected int distance;
    protected double directDistance;

    /* ********************************************************************** */

    /**
     * Internal constructor for instantiating a new vertex.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param maze The backing maze to which the vertex corresponds.
     * @param isImageCoordinates Whether the coordinates are image coordinates
     *                           or maze matrix coordinates.
     * @param compareMode The compare mode to use.
     */
    protected MazeVertex(int x, int y, MazeReader maze, boolean isImageCoordinates, CompareMode compareMode) {
        super(x, y, maze, isImageCoordinates);

        this.previous = null;
        this.distance = 0;
        this.compareMode = compareMode;
    }

    /**
     * Helper method for instantiating a new vertex that is a direct neighbor
     * of an existing vertex; by automatically setting the "previous" property
     * and calculating the distance of the new vertex.
     *
     * @param x The matrix x coordinate.
     * @param y The matrix y coordinate.
     *
     * @return A new neighbor vertex.
     */
    public MazeVertex makeNext(int x, int y) {
        MazeVertex vertex = MazeVertex.fromMaze(x, y, maze, compareMode);
        vertex.setPrevious(this);
        return vertex;
    }

    /**
     * Helper method for instantiating a new vertex that is a direct neighbor
     * of an existing vertex; by automatically setting the "previous" property
     * and calculating the distance of the new vertex. And lastly by calculating
     * the "direct distance" property, using the provided end point.
     *
     * @param x The matrix x coordinate.
     * @param y The matrix y coordinate.
     * @param end A point to use to calculate the "direct distance" property.
     *
     * @return A new neighbor vertex.
     */
    public MazeVertex makeNext(int x, int y, MazeVertex end) {
        MazeVertex vertex = MazeVertex.fromMaze(x, y, maze, compareMode);
        vertex.setPrevious(this);
        vertex.setDirectDistanceUsing(end);
        return vertex;
    }

    /**
     * Method for creating a new vertex using matrix coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param maze The backing maze.
     * @param compareMode The compare mode to use.
     *
     * @return A new vertex.
     */
    public static MazeVertex fromMaze(int x, int y, MazeReader maze, CompareMode compareMode) {
        return new MazeVertex(x, y, maze, false, compareMode);
    }

    /**
     * Method for creating a new vertex using matrix coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param maze The backing maze.
     *
     * @return A new vertex.
     */
    public static MazeVertex fromMaze(int x, int y, MazeReader maze) {
        return fromMaze(x, y, maze, CompareMode.DistanceOnly);
    }

    /**
     * Method for creating a new vertex using image coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param maze The backing maze.
     * @param compareMode The compare mode to use.
     *
     * @return A new vertex.
     */
    public static MazeVertex fromImage(int x, int y, MazeReader maze, CompareMode compareMode) {
        return new MazeVertex(x, y, maze, true, compareMode);
    }

    /**
     * Method for creating a new vertex using image coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param maze The backing maze.
     *
     * @return A new vertex.
     */
    public static MazeVertex fromImage(int x, int y, MazeReader maze) {
        return fromImage(x, y, maze, CompareMode.DistanceOnly);
    }

    /**
     * Method for creating a new vertex from a point.
     *
     * @param point The point to use.
     * @param compareMode The compare mode to use.
     *
     * @return A new vertex.
     */
    public static MazeVertex fromPoint(MazePoint point, CompareMode compareMode) {
        return new MazeVertex(point.getMazeX(), point.getMazeY(), point.maze, false, compareMode);
    }

    /**
     * Method for creating a new vertex from a point.
     *
     * @param point The point to use.
     *
     * @return A new vertex.
     */
    public static MazeVertex fromPoint(MazePoint point) {
        return fromPoint(point, CompareMode.DistanceOnly);
    }

    /* ********************************************************************** */

    /**
     * Method for getting the current compare mode.
     *
     * @return The compare mode.
     */
    public CompareMode getCompareMode() {
        return this.compareMode;
    }

    /**
     * Method for setting the compare mode.
     *
     * @param compareMode The compare mode to use.
     */
    public void setCompareMode(CompareMode compareMode) {
        this.compareMode = compareMode;
    }

    /**
     * Method for getting the distance property.
     *
     * @return The distance.
     */
    public int getDistance() {
        return this.distance;
    }

    /**
     * Method for setting the distance property.
     *
     * @param distance The distance.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Method for getting the direct distance property.
     *
     * @return The direct distance.
     */
    public double getDirectDistance() {
        return this.directDistance;
    }

    /**
     * Method for setting the direct distance property.
     *
     * @param directDistance The direct distance.
     */
    public void setDirectDistance(double directDistance) {
        this.directDistance = directDistance;
    }

    /**
     * Method for calculating and setting the direct distance property using a
     * provided end point/vertex.
     *
     * @param end The end point/vertex to calculate against.
     */
    public void setDirectDistanceUsing(MazePoint end) {
        this.directDistance = proximityTo(end);
    }

    /**
     * Method for getting the previous connected vertex.
     *
     * @return The previous vertex.
     */
    public MazeVertex getPrevious() {
        return this.previous;
    }

    /**
     * Method for setting the previous connected vertex, and automatically
     * calculating the "distance" property.
     *
     * @param previous The previous vertex.
     */
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
