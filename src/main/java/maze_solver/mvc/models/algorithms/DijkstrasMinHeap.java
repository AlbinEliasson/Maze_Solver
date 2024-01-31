package maze_solver.mvc.models.algorithms;

import maze_solver.mvc.models.types.MazePoint;
import maze_solver.mvc.models.types.MazeVertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstrasMinHeap extends MazeAlgorithm {
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

        List<MazePoint> path;
        distances[start.getX()][start.getY()] = start.getDistance();
        vertexes.add(start);

        int[] xDirections = {-1, 0, 1, 0};
        int[] yDirections = {0, 1, 0, -1};

        while(!vertexes.isEmpty()) {
            MazeVertex current = vertexes.remove();

            for(int i = 0; i < 4; i++) {
                int x = current.getX() + xDirections[i];
                int y = current.getY() + yDirections[i];

                if(
                        x >= 0 && x < mazeMatrix.length &&
                        y >= 0 && y < mazeMatrix[0].length &&
                        mazeMatrix[x][y] == 0 &&
                        current.getDistance() + 1 < distances[x][y]
                ) {
                    MazeVertex next = current.makeNext(x, y);
                    distances[x][y] = next.getDistance();

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
