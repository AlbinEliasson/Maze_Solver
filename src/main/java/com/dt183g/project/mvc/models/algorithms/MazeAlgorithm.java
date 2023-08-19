package com.dt183g.project.mvc.models.algorithms;

import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.mvc.models.Vertex;

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
    abstract public List<MazePoint> solve(int[][] mazeMatrix, MazePoint startLocation, MazePoint endLocation);

    /**
     * Helper method for dumping the current state of a currently running maze
     * path algorithm.
     *
     * @param mazeMatrix The maze.
     * @param startLocation The start location.
     * @param endLocation The end location.
     * @param vertexes A list of vertexes.
     */
    protected void dump(int[][] mazeMatrix, Point startLocation, Point endLocation, List<Vertex> vertexes) {
        // TODO: Remove.

        // Stupidly create a copy of the maze matrix, to ensure that
        // modification here don't interfere with the algorithm.
        int[][] copy = new int[mazeMatrix.length][mazeMatrix[0].length];
        for(int x = 0; x < mazeMatrix.length; x++)
            for(int y = 0; y < mazeMatrix[0].length; y++)
                copy[x][y] = mazeMatrix[x][y];

        // Set all the current vertexes to value 2 in the maze
        vertexes.forEach(v -> copy[v.getXCoordinate()][v.getYCoordinate()] = 2);

        // Set the current top vertex to 3
        copy[vertexes.get(vertexes.size() - 1).getXCoordinate()][vertexes.get(vertexes.size() - 1).getYCoordinate()] = 3;

        // Set the end and start positions
        copy[startLocation.x][startLocation.y] = 4;
        copy[endLocation.x][endLocation.y] = 5;
        System.out.println();
        for(int y = 0; y < mazeMatrix[0].length; y++) {
            for(int x = 0; x < mazeMatrix.length; x++) {
                System.out.print(
                        switch(copy[x][y]) {
                            case 0 -> "\u001b[32m";
                            case 1 -> "\u001b[31m";
                            case 2 -> "\u001b[34m";
                            case 3 -> "\u001b[35m";
                            case 4, 5 -> "\u001b[36m";
                            default -> "";
                        } +
                                copy[x][y] +
                                "\u001b[0m"
                );
            }
            System.out.println();
        }
    }
}
