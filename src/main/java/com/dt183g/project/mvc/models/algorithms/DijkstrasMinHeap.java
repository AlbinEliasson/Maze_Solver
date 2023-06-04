package com.dt183g.project.mvc.models.algorithms;

import com.dt183g.project.mvc.models.Vertex;
import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.mvc.models.types.MazeVertex;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstrasMinHeap extends MazeAlgorithm {
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
        System.out.print("[MODEL] Running Dijkstras Min Heap algorithm!\n");

        PriorityQueue<Vertex> vertexes = new PriorityQueue<>();
        int[][] distances = new int[mazeMatrix.length][mazeMatrix[0].length];
        for (int[] sub : distances)
            Arrays.fill(sub, Integer.MAX_VALUE);

        List<Point> path = null;
        Vertex start = new Vertex(startLocation.x, startLocation.y);
        Vertex end = new Vertex(endLocation.x, endLocation.y);

        start.setDistance(0);
        start.setCompareDistance(true);

        distances[start.getXCoordinate()][start.getYCoordinate()] = start.getDistance();
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
                    dunno.setCompareDistance(true);
                    dunno.setPrevious(next);

                    distances[x][y] = next.getDistance() + 1;

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
        MazeVertex start = MazeVertex.fromPoint(startLocation, MazeVertex.CompareMode.DistanceOnly);
        MazeVertex end = MazeVertex.fromPoint(endLocation, MazeVertex.CompareMode.DistanceOnly);

        System.out.printf("""
            [MODEL] Running Dijkstras Min Heap algorithm!
                Points:
                    Start: %s
                    End: %s
                Vertexes:
                    Start: %s
                    End: %s
            """,
                startLocation, endLocation,
                start, end);

        PriorityQueue<MazeVertex> vertexes = new PriorityQueue<>();
        int[][] distances = new int[mazeMatrix.length][mazeMatrix[0].length];
        for (int[] sub : distances)
            Arrays.fill(sub, Integer.MAX_VALUE);

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
                        distances[current.getMazeX()][current.getMazeY()] < distances[x][y]
                ) {
                    MazeVertex next2 = MazeVertex.fromMaze(x, y, current.getBackingMaze());
                    next2.setPrevious(current);
                    MazeVertex next = current.makeNext(x, y);
                    distances[x][y] = next.getDistance();

                    System.out.printf("ASDDASASD\n\t%s %s %s %s %s\n\t%s %s %s %s %s\n\t%s %s %s %s %s\n",
                            current.getMazeX(), current.getMazeY(), current.getDistance(), current.getCompareMode(), current.getPrevious(),
                            next.getMazeX(), next.getMazeY(), next.getDistance(), next.getCompareMode(), next.getPrevious(),
                            next2.getMazeX(), next2.getMazeY(), next2.getDistance(), next2.getCompareMode(), next2.getPrevious());

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
