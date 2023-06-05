package com.dt183g.project.mvc.models.algorithms;

import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.mvc.models.Vertex;
import com.dt183g.project.mvc.models.types.MazeVertex;

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
        System.out.print("[MODEL] Running Dijkstras Queue algorithm!\n");

        Queue<Vertex> vertexes = new LinkedList<>();
        int[][] distances = new int[mazeMatrix.length][mazeMatrix[0].length];
        for (int[] sub : distances)
            Arrays.fill(sub, Integer.MAX_VALUE);

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

    @Override
    public List<MazePoint> solve(int[][] mazeMatrix, MazePoint startLocation, MazePoint endLocation) {
        MazeVertex start = MazeVertex.fromPoint(startLocation);
        MazeVertex end = MazeVertex.fromPoint(endLocation);

        System.out.printf("""
            [MODEL] Running Dijkstras Queue algorithm!
                Points:
                    Start: %s
                    End: %s
                Vertexes:
                    Start: %s
                    End: %s
            """,
            startLocation, endLocation,
            start, end);

        Queue<MazeVertex> vertexes = new LinkedList<>();
        int[][] distances = new int[mazeMatrix.length][mazeMatrix[0].length];
        for(int[] part : distances)
            Arrays.fill(part, Integer.MAX_VALUE);

        List<MazePoint> path = null;
        distances[start.getMazeX()][start.getMazeY()] = start.getDistance();
        vertexes.add(start);

        int[] xDirections = {-1, 0, 1, 0};
        int[] yDirections = {0, 1, 0, -1};

        while(!vertexes.isEmpty()) {
            MazeVertex current = vertexes.remove();

            for(int i = 0; i < xDirections.length; i++) {
                int x = current.getMazeX() + xDirections[i];
                int y = current.getMazeY() + yDirections[i];

                if(
                        x >= 0 && x < mazeMatrix.length &&
                        y >= 0 && y < mazeMatrix[0].length &&
                        mazeMatrix[x][y] == 0 &&
                        distances[current.getMazeX()][current.getMazeY()] + 1 < distances[x][y]
                ) {
                    //MazeVertex next = MazeVertex.fromMaze(x, y, current.getBackingMaze());
                    //next.setPrevious(current);
                    MazeVertex next = current.makeNext(x, y, end);
                    distances[x][y] = distances[current.getMazeX()][current.getMazeY()] + 1;

                    if(x == end.getMazeX() && y == end.getMazeY()) {
                        path = new ArrayList<>();

                        while(next != null) {
                            path.add(next);
                            next = next.getPrevious();
                        }

                        return path;
                    }

                    vertexes.add(next);
                }
            }
        }

        return null;
    }
}
