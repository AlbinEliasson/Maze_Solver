package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.controllers.MazeController;

import java.awt.Point;
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
            case "Dijkstras algorithm minHeap" -> dijkstrasAlgorithmMinHeap(maze, new Vertex(startPosition.x, startPosition.y), new Vertex(endPosition.x, endPosition.y));
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

    public void dijkstrasAlgorithmMinHeap(int[][] maze, Vertex startPosition, Vertex endPosition) {
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
        startPosition.setCompareDistance(true);

        mazePathDistance[startPosition.getYCoordinate()][startPosition.getXCoordinate()] = startPosition.getDistance();
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

                    mazePathDistance[newY][newX] = 1 + nextVertex.getDistance();

                    if (newX == endPosition.getXCoordinate() && newY == endPosition.getYCoordinate()) {
                        pathFound = true;
                        System.out.format("Current X: %d Y: %d%n", newX, newY);
                        System.out.format("Destination X: %d Y: %d%n", endPosition.getXCoordinate(), endPosition.getYCoordinate());

                        Vertex endVertex = new Vertex(newX, newY);
                        endVertex.setDistance(nextVertex.getDistance() + 1);
                        endVertex.setCompareDistance(true);
                        endVertex.setPrevious(nextVertex);

                        while (endVertex != null) {
                            System.out.format("X: %d Y: %d distance: %d%n", endVertex.getXCoordinate(), endVertex.getYCoordinate(), endVertex.getDistance());
                            displayPath(new Point(endVertex.getXCoordinate(), endVertex.getYCoordinate()));
                            endVertex = endVertex.getPrevious();
                        }
                        System.out.println("Final distance: " + (nextVertex.getDistance() + 1));
                        break;
                    }
                    Vertex newVertex = new Vertex(newX, newY);
                    newVertex.setDistance(nextVertex.getDistance() + 1);
                    newVertex.setCompareDistance(true);
                    newVertex.setPrevious(nextVertex);
                    vertexQueue.add(newVertex);
                }
            }
        }
        if (!pathFound) {
            System.out.println("End not found!");
        }
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

    private void displayPath(Point position) {
        mazeController.displayPath(position);
    }

    public void resetMaze() {
        pathFound = false;
    }
}
