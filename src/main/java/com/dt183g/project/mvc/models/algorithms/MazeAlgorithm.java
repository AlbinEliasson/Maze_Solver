package com.dt183g.project.mvc.models.algorithms;

import com.dt183g.project.mvc.models.types.MazePoint;

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
    abstract public List<MazePoint> solve(int[][] mazeMatrix, MazePoint startLocation, MazePoint endLocation);
}
