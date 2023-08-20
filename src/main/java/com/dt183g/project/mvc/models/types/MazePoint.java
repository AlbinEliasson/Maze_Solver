package com.dt183g.project.mvc.models.types;

import com.dt183g.project.utility.MazeReader;

/**
 * A class representing a point in the maze.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
public class MazePoint {
    protected MazeReader maze;
    protected int mazeX;
    protected int mazeY;

    /**
     * Internal constructor for instantiating a new point.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param maze The backing maze to which the point corresponds.
     * @param isImageCoordinates Whether the coordinates are image coordinates
     *                           or maze matrix coordinates.
     */
    protected MazePoint(int x, int y, MazeReader maze, boolean isImageCoordinates) {
        this.maze = maze;
        if(isImageCoordinates) {
            this.setImageX(x);
            this.setImageY(y);
        } else {
            this.setX(x);
            this.setY(y);
        }
    }

    /**
     * Method for creating a new point using image coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param maze The backing maze.
     *
     * @return A new point.
     */
    public static MazePoint fromImage(int x, int y, MazeReader maze) {
        return new MazePoint(x, y, maze, true);
    }

    /**
     * Method for getting the point's x coordinate for the maze matrix.
     *
     * @return The matrix x coordinate.
     */
    public int getX() {
        return this.mazeX;
    }

    /**
     * Method for setting the point's x coordinate using a maze matrix value.
     *
     * @param x The matrix x coordinate.
     */
    public void setX(int x) {
        if(x < 0 || x > maze.getMatrixWidth() - 1)
            throw new IndexOutOfBoundsException(String.format("Attempted to set maze X to %s, allowed range 0 - %s.", x, maze.getMatrixWidth() - 1));

        this.mazeX = x;
    }

    /**
     * Method for getting the point's x coordinate for the maze image.
     *
     * @return The image x coordinate.
     */
    public int getImageX() {
        return maze.translateMatrixXToImageX(this.mazeX);
    }

    /**
     * Method for setting the point's x coordinate using a maze image value.
     *
     * @param x The image x coordinate.
     */
    public void setImageX(int x) {
        if(x < 0 || x > maze.getImageWidth() - 1)
            throw new IndexOutOfBoundsException(String.format("Attempted to set image X to %s, allowed range 0 - %s.", x, maze.getImageWidth() - 1));

        this.mazeX = maze.translateImageXToMatrixX(x);
    }

    /**
     * Method for getting the point's y coordinate for the maze matrix.
     *
     * @return The matrix y coordinate.
     */
    public int getY() {
        return this.mazeY;
    }

    /**
     * Method for setting the point's y coordinate using a maze matrix value.
     *
     * @param y The matrix y coordinate.
     */
    public void setY(int y) {
        if(y < 0 || y > maze.getMatrixHeight() - 1)
            throw new IndexOutOfBoundsException(String.format("Attempted to set maze Y to %s, allowed range 0 - %s.", y, maze.getMatrixHeight() - 1));

        this.mazeY = y;
    }

    /**
     * Method for getting the point's y coordinate for the maze image.
     *
     * @return The image y coordinate.
     */
    public int getImageY() {
        return maze.translateMatrixYToImageY(this.mazeY);
    }

    /**
     * Method for setting the point's y coordinate using a maze image value.
     *
     * @param y The image y coordinate.
     */
    public void setImageY(int y) {
        if(y < 0 || y > maze.getImageHeight() - 1)
            throw new IndexOutOfBoundsException(String.format("Attempted to set image Y to %s, allowed range 0 - %s.", y, maze.getImageHeight() - 1));

        this.mazeY = maze.translateImageYToMatrixY(y);
    }

    /**
     * Method for calculating the absolute distance between two points.
     *
     * Note that the method does not account for the "direction" in which the
     * distance exists, I.E. a 5 point distance to the top-left and bottom-right
     * will both return the same value. Therefore, this method should *NOT* be
     * used to determine the "value" of the coordinates (less/greater than), and
     * only for the exact distance.
     *
     * @param other Other point.
     *
     * @return The distance between the points.
     */
    public double proximityTo(MazePoint other) {
        return Math.sqrt((mazeY - other.getY()) * (mazeY - other.getY()) + (mazeX - other.getX()) * (mazeX - other.getX()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if(!(object instanceof MazePoint point))
            return false;

        return mazeX == point.getX() && mazeY == point.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s[x=%d,y=%d]", getClass().getSimpleName(), mazeX, mazeY);
    }
}
