package com.dt183g.project.mvc.models.types;

import com.dt183g.project.utility.MazeReader;

public class MazePoint {
    protected MazeReader maze;
    protected int mazeX;
    protected int mazeY;

    protected MazePoint(int x, int y, MazeReader maze, boolean isImageCoordinates) {
        this.maze = maze;
        if(isImageCoordinates) {
            this.setImageX(x);
            this.setImageY(y);
        } else {
            this.setMazeX(x);
            this.setMazeY(y);
        }
    }

    public static MazePoint fromMaze(int x, int y, MazeReader maze) {
        return new MazePoint(x, y, maze, false);
    }

    public static MazePoint fromImage(int x, int y, MazeReader maze) {
        return new MazePoint(x, y, maze, true);
    }

    public MazeReader getBackingMaze() {
        return this.maze;
    }

    public int getMazeX() {
        return this.mazeX;
    }

    public void setMazeX(int x) {
        if(x < 0 || x > maze.getMatrixWidth() - 1)
            throw new IndexOutOfBoundsException(String.format("Attempted to set maze X to %s, allowed range 0 - %s.", x, maze.getMatrixWidth() - 1));

        this.mazeX = x;
    }

    public int getImageX(MazeReader.Direction direction) {
        return maze.translateMatrixXToImageX(this.mazeX, direction);
    }

    public int getImageX() {
        return maze.translateMatrixXToImageX(this.mazeX);
    }

    public void setImageX(int x) {
        if(x < 0 || x > maze.getImageWidth() - 1)
            throw new IndexOutOfBoundsException(String.format("Attempted to set image X to %s, allowed range 0 - %s.", x, maze.getImageWidth() - 1));

        this.mazeX = maze.translateImageXToMatrixX(x);
    }

    public int getMazeY() {
        return this.mazeY;
    }

    public void setMazeY(int y) {
        if(y < 0 || y > maze.getMatrixHeight() - 1)
            throw new IndexOutOfBoundsException(String.format("Attempted to set maze Y to %s, allowed range 0 - %s.", y, maze.getMatrixHeight() - 1));

        this.mazeY = y;
    }

    public int getImageY(MazeReader.Direction direction) {
        return maze.translateMatrixYToImageY(this.mazeY, direction);
    }

    public int getImageY() {
        return maze.translateMatrixYToImageY(this.mazeY);
    }

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
        return Math.sqrt((mazeX - other.getMazeX()) ^ 2 + (mazeY - other.getMazeY()) ^ 2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if(!(object instanceof MazePoint point))
            return false;

        return maze == point.getBackingMaze() && mazeX == point.getMazeX() && mazeY == point.getMazeY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s[x=%d,y=%d]", getClass().getSimpleName(), mazeX, mazeY);
    }
}
