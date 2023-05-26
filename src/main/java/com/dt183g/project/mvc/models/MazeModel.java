package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.controllers.MazeController;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class MazeModel {
    private final MazeController mazeController;
    private boolean pathFound = false;

    public MazeModel(MazeController controller) {
        this.mazeController = controller;
    }

    public void solveMaze(Point startPosition, Point endPosition, int[][] maze, String algorithm) {
        switch (algorithm) {
            case "Dijkstras algorithm queue" -> dijkstrasAlgorithmQueue(maze, startPosition, endPosition);
            case "Dijkstras algorithm minHeap" -> dijkstrasAlgorithmMinHeap(maze, startPosition, endPosition);
            case "A* algorithm" -> aStarAlgorithm(maze, new Vertex(startPosition.x, startPosition.y), new Vertex(endPosition.x, endPosition.y));
        }
    }

    public void dijkstrasAlgorithmQueue(int[][] maze, Point startPosition, Point endPosition) {
        // TODO make algorithm more efficient!
        Stack<Vertex> resultPath = new Stack<>();
        Queue<Vertex> vertexQueue = new LinkedList<>();
        int[][] mazePathDistance = new int[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazePathDistance[i][j] = (int)(1e5);
                //mazePath[i][j] = 90;
            }
        }
        Vertex startVertex = new Vertex(startPosition.x, startPosition.y);
        startVertex.setDistance(0);

        mazePathDistance[startPosition.y][startPosition.x] = 0;
        vertexQueue.add(startVertex);
        resultPath.push(startVertex);
        int[] possibleDirectionX = {-1, 0, 1, 0};
        int[] possibleDirectionY = {0, 1, 0, -1};

        while (!vertexQueue.isEmpty()) {
            Vertex nextVertex = vertexQueue.remove();
            for(int i = 0; i < 4; i++) {
                int newX = nextVertex.getXCoordinate() + possibleDirectionX[i];
                int newY = nextVertex.getYCoordinate() + possibleDirectionY[i];

                if (newX >= 0 && newX < maze[0].length && newY >= 0 && newY < maze.length && maze[newY][newX] == 1
                        && nextVertex.getDistance() + 1 < mazePathDistance[newY][newX]) {

                    mazePathDistance[newY][newX] = 1 + nextVertex.getDistance();

                    if (newX == endPosition.x && newY == endPosition.y) {
                        pathFound = true;
                        System.out.format("Current X: %d Y: %d%n", newX, newY);
                        System.out.format("Destination X: %d Y: %d%n", endPosition.x, endPosition.y);

                        Vertex endVertex = new Vertex(newX, newY);
                        endVertex.setDistance(nextVertex.getDistance() + 1);

                        resultPath.push(endVertex);
                        Vertex vertexEndPosition = resultPath.pop();
                        System.out.format("X: %d Y: %d distance: %d%n", vertexEndPosition.getXCoordinate(), vertexEndPosition.getYCoordinate(), vertexEndPosition.getDistance());
                        displayPath(new Point(vertexEndPosition.getXCoordinate(), vertexEndPosition.getYCoordinate()));
                        while (!resultPath.isEmpty()) {
                            Vertex vertex = resultPath.pop();

                            if (vertexEndPosition.getXCoordinate() == vertex.getXCoordinate() && vertexEndPosition.getYCoordinate() - 1 == vertex.getYCoordinate()) {
                                vertexEndPosition = vertex;
                                System.out.format("X: %d Y: %d distance: %d%n", vertex.getXCoordinate(), vertex.getYCoordinate(), vertex.getDistance());
                                displayPath(new Point(vertex.getXCoordinate(), vertex.getYCoordinate()));

                            } else if (vertexEndPosition.getXCoordinate() - 1 == vertex.getXCoordinate() && vertexEndPosition.getYCoordinate() == vertex.getYCoordinate()) {
                                vertexEndPosition = vertex;
                                System.out.format("X: %d Y: %d distance: %d%n", vertex.getXCoordinate(), vertex.getYCoordinate(), vertex.getDistance());
                                displayPath(new Point(vertex.getXCoordinate(), vertex.getYCoordinate()));

                            } else if (vertexEndPosition.getXCoordinate() == vertex.getXCoordinate() && vertexEndPosition.getYCoordinate() + 1 == vertex.getYCoordinate()) {
                                vertexEndPosition = vertex;
                                System.out.format("X: %d Y: %d distance: %d%n", vertex.getXCoordinate(), vertex.getYCoordinate(), vertex.getDistance());
                                displayPath(new Point(vertex.getXCoordinate(), vertex.getYCoordinate()));

                            } else if (vertexEndPosition.getXCoordinate() + 1 == vertex.getXCoordinate() && vertexEndPosition.getYCoordinate() == vertex.getYCoordinate()) {
                                vertexEndPosition = vertex;
                                System.out.format("X: %d Y: %d distance: %d%n", vertex.getXCoordinate(), vertex.getYCoordinate(), vertex.getDistance());
                                displayPath(new Point(vertex.getXCoordinate(), vertex.getYCoordinate()));
                            }
                        }
                        System.out.println("Final distance: " + (nextVertex.getDistance() + 1));
                        break;
                    }
                    Vertex newVertex = new Vertex(newX, newY);
                    newVertex.setDistance(nextVertex.getDistance() + 1);
                    resultPath.push(newVertex);
                    vertexQueue.add(newVertex);
                }
            }
        }
        if (!pathFound) {
            System.out.println("End not found!");
        }
    }

    public void dijkstrasAlgorithmMinHeap(int[][] maze, Point startPosition, Point endPosition) {
       System.out.println("Not implemented");
    }

    public void aStarAlgorithm(int[][] maze, Vertex startPosition, Vertex endPosition) {
        // TODO make algorithm more efficient!
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();

        int[][] mazePathDistance = new int[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazePathDistance[i][j] = (int)(1e5);
                //mazePath[i][j] = 90;
            }
        }
        startPosition.setDistance(0);
        startPosition.setF(startPosition.getDistance() + heuristic(
                startPosition.getXCoordinate(), startPosition.getYCoordinate(),
                endPosition.getXCoordinate(), endPosition.getYCoordinate()));

        mazePathDistance[startPosition.getXCoordinate()][startPosition.getYCoordinate()] = startPosition.getDistance();

        vertexQueue.add(startPosition);

        int[] possibleDirectionX = {-1, 0, 1, 0};
        int[] possibleDirectionY = {0, 1, 0, -1};

        while (!vertexQueue.isEmpty()) {
            Vertex nextVertex = vertexQueue.remove();

            for(int i = 0; i < 4; i++) {
                int newX = nextVertex.getXCoordinate() + possibleDirectionX[i];
                int newY = nextVertex.getYCoordinate() + possibleDirectionY[i];

                if (newX >= 0 && newX < maze[0].length && newY >= 0 && newY < maze.length && maze[newY][newX] == 1
                        && nextVertex.getDistance() + 1 < mazePathDistance[newY][newX]) {

                    mazePathDistance[newY][newX] = nextVertex.getDistance() + 1;

                    if (newX == endPosition.getXCoordinate() && newY == endPosition.getYCoordinate()) {
                        pathFound = true;
                        Vertex vertexEndPosition = new Vertex(newX, newY);
                        vertexEndPosition.setDistance(nextVertex.getDistance() + 1);
                        vertexEndPosition.setPrevious(nextVertex);

                        while (vertexEndPosition != null) {
                            System.out.format("X: %d Y: %d distance: %d%n", vertexEndPosition.getXCoordinate(), vertexEndPosition.getYCoordinate(), vertexEndPosition.getDistance());
                            displayPath(new Point(vertexEndPosition.getXCoordinate(), vertexEndPosition.getYCoordinate()));
                            vertexEndPosition = vertexEndPosition.getPrevious();
                        }

                        System.out.println("Final distance: " + (nextVertex.getDistance() + 1));
                        break;
                    }

                    Vertex newVertex = new Vertex(newX, newY);
                    newVertex.setDistance(nextVertex.getDistance() + 1);
                    newVertex.setF(newVertex.getDistance() + heuristic(newX, newY, endPosition.getXCoordinate(), endPosition.getYCoordinate()));
                    newVertex.setPrevious(nextVertex);

                    vertexQueue.add(newVertex);
                }
            }
        }
        if (!pathFound) {
            System.out.println("End not found!");
        }
    }

    private static double heuristic(int newX, int newY, int endX, int endY) {
        return Math.sqrt((newY - endY) * (newY - endY) + (newX - endX) * (newX - endX));
    }

//    public static double a_Star(int[][] maze, double[][] heuristic, Vertex start, Vertex goal) {
//
//        //This contains the distances from the start node to all other nodes
//        int[][] distances = new int[maze.length][maze[0].length];
//        //Initializing with a distance of "Infinity"
//        //Arrays.fill(distances, Integer.MAX_VALUE);
//        Arrays.stream(distances).forEach(distance -> Arrays.fill(distance, Integer.MAX_VALUE));
//        //The distance from the start node to itself is of course 0
//        distances[start.getXCoordinate()][start.getYCoordinate()] = 0;
//
//        //This contains the priorities with which to visit the nodes, calculated using the heuristic.
//        double[][] priorities = new double[maze.length][maze[0].length];
//        //Initializing with a priority of "Infinity"
//        //Arrays.fill(priorities, Integer.MAX_VALUE);
//        Arrays.stream(priorities).forEach(priority -> Arrays.fill(priority, Integer.MAX_VALUE));
//        //start node has a priority equal to straight line distance to goal. It will be the first to be expanded.
//        priorities[start.getXCoordinate()][start.getYCoordinate()] = heuristic(start, goal);
//
//        //This contains whether a node was already visited
//        boolean[] visited = new boolean[maze.length];
//
//        //While there are nodes left to visit...
//        while (true) {
//
//            // ... find the node with the currently lowest priority...
//            double lowestPriority = Integer.MAX_VALUE;
//            int xIndex = -1;
//            int yIndex = -1;
//            for (int i = 0; i < priorities.length; i++) {
//                for (int x = 0; x < priorities[i].length; x++) {
//                    //... by going through all nodes that haven't been visited yet
//                    if (priorities[i][x] < lowestPriority && !visited[i]) {
//                        lowestPriority = priorities[i][x];
//                        xIndex = i;
//                        yIndex = x;
//                    }
//                }
//            }
//
//            if (xIndex == -1) {
//                // There was no node not yet visited --> Node not found
//                return -1;
//            } else if (xIndex == goal.getXCoordinate() && yIndex == goal.getYCoordinate()) {
//                // Goal node found
//                System.out.println("Goal node found!");
//                return distances[xIndex][yIndex];
//            }
//
//            //System.out.println("Visiting node " + lowestPriorityIndex + " with currently lowest priority of " + lowestPriority);
//
//            //...then, for all neighboring nodes that haven't been visited yet....
//            for (int i = 0; i < maze[xIndex].length; i++) {
//                if (maze[xIndex][i] != 0 && !visited[i]) {
//                    //...if the path over this edge is shorter...
//                    if (distances[lowestPriorityIndex] + maze[lowestPriorityIndex][i] < distances[i]) {
//                        //...save this path as new shortest path
//                        distances[i] = distances[lowestPriorityIndex] + maze[lowestPriorityIndex][i];
//                        //...and set the priority with which we should continue with this node
//                        priorities[i] = distances[i] + heuristic(new Vertex(distances[i], lowestPriorityIndex, i), goal);
//                        System.out.println("Updating distance of node " + i + " to " + distances[i] + " and priority to " + priorities[i]);
//                    }
//                }
//            }
//
//            // Lastly, note that we are finished with this node.
//            visited[lowestPriorityIndex] = true;
//            //System.out.println("Visited nodes: " + Arrays.toString(visited));
//            //System.out.println("Currently lowest distances: " + Arrays.toString(distances));
//
//        }
//    }

    private void displayPath(Point position) {
        mazeController.displayPath(position);
    }

    public void resetMaze() {
        pathFound = false;
    }
}
