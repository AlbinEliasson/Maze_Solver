package com.dt183g.project.mvc.models.algorithms;

import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.mvc.models.types.MazeVertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DijkstrasQueue extends MazeAlgorithm {
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

        List<MazePoint> path;
        distances[start.getX()][start.getY()] = start.getDistance();
        vertexes.add(start);

        int[] xDirections = {-1, 0, 1, 0};
        int[] yDirections = {0, 1, 0, -1};

        while(!vertexes.isEmpty()) {
            MazeVertex current = vertexes.remove();

            for(int i = 0; i < xDirections.length; i++) {
                int x = current.getX() + xDirections[i];
                int y = current.getY() + yDirections[i];

                if(
                        x >= 0 && x < mazeMatrix.length &&
                        y >= 0 && y < mazeMatrix[0].length &&
                        mazeMatrix[x][y] == 0 &&
                        distances[current.getX()][current.getY()] + 1 < distances[x][y]
                ) {
                    MazeVertex next = current.makeNext(x, y, end);
                    distances[x][y] = distances[current.getX()][current.getY()] + 1;

                    if(x == end.getX() && y == end.getY()) {
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
