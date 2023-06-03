package com.dt183g.project.mvc.models.algorithms;

import com.dt183g.project.mvc.models.Vertex;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DijkstrasQueue extends MazeAlgorithm {
    /**
     * Method for finding a path through a maze using the algorithm.
     *
     * @param mazeMatrix    The maze to solve.
     * @param startLocation The starting location.
     * @param endLocation   The end location.
     * @return The path as a list of points.
     */
    @Override
    public List<Point> solve(int[][] mazeMatrix, Point startLocation, Point endLocation) {
        Queue<Vertex> vertexes = new LinkedList<>();
        int[][] distances = new int[mazeMatrix.length][mazeMatrix[0].length];
        for (int[] distance : distances)
            Arrays.fill(distance, Integer.MAX_VALUE);

        List<Point> path = null;
        Vertex start = new Vertex(startLocation.x, startLocation.y);
        Vertex end = new Vertex(endLocation.x, endLocation.y);

        distances[start.getXCoordinate()][start.getYCoordinate()] = 0;
        vertexes.add(start);

        int[] xDirections = {-1, 0, 1, 0};
        int[] yDirections = {0, 1, 0, -1};

        while(!vertexes.isEmpty()) {
            Vertex next = vertexes.remove();

            for(int i = 0; i < 4; i++) {
                int x = next.getXCoordinate() + xDirections[i];
                int y = next.getYCoordinate() + yDirections[i];

                if(
                        x >= 0 && x < mazeMatrix.length &&
                        y >= 0 && y < mazeMatrix[0].length &&
                        mazeMatrix[x][y] == 0 &&
                        next.getDistance() + 1 < distances[x][y]
                ) {
                    Vertex dunno = new Vertex(x, y);
                    dunno.setDistance(next.getDistance() + 1);
                    dunno.setPrevious(next);

                    distances[x][y] = dunno.getDistance();

                    if(x == end.getXCoordinate() && y == end.getYCoordinate()) {
                        path = new ArrayList<>();

                        while(dunno != null) {
                            path.add(new Point(dunno.getXCoordinate(), dunno.getYCoordinate()));
                            dunno = dunno.getPrevious();
                        }

                        return path;
                    }

                    vertexes.add(dunno);
                }
            }
        }

        return null;
    }
}
