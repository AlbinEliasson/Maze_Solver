package com.dt183g.project.mvc.models.algorithms;

import java.awt.Point;
import java.util.List;

abstract public class MazeAlgorithm {
    /**
     * Method for finding a path through a maze using the algorithm.
     *
     * @param mazeMatrix The maze to solve.
     * @param startLocation The starting location.
     * @param endLocation The end location.
     *
     * @return The path as a list of points.
     */
    abstract public List<Point> solve(int[][] mazeMatrix, Point startLocation, Point endLocation);
}
