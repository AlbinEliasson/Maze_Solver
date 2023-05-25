package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.controllers.MazeController;

import java.awt.Point;
import java.util.LinkedList;
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
        }
    }

    public void dijkstrasAlgorithmQueue(int[][] maze, Point startPosition, Point endPosition) {
        // TODO make algorithm more efficient!
        Stack<Vertex> resultPath = new Stack<>();
        Queue<Vertex> vertexQueue = new LinkedList<>();
        int[][] mazePath = new int[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazePath[i][j] = (int)(1e5);
                //mazePath[i][j] = 90;
            }
        }

        mazePath[startPosition.y][startPosition.x] = 0;
        vertexQueue.add(new Vertex(0, startPosition.x, startPosition.y));
        resultPath.push(new Vertex(0, startPosition.x, startPosition.y));
        int[] possibleDirectionX = {-1, 0, 1, 0};
        int[] possibleDirectionY = {0, 1, 0, -1};

        while (!vertexQueue.isEmpty()) {
            Vertex nextVertex = vertexQueue.remove();
            for(int i = 0; i < 4; i++) {
                int newX = nextVertex.getXCoordinate() + possibleDirectionX[i];
                int newY = nextVertex.getYCoordinate() + possibleDirectionY[i];

                if (newX >= 0 && newX < maze[0].length && newY >= 0 && newY < maze.length && maze[newY][newX] == 1
                        && nextVertex.getDistance() + 1 < mazePath[newY][newX]) {

                    mazePath[newY][newX] = 1 + nextVertex.getDistance();

                    if (newX == endPosition.x && newY == endPosition.y) {
                        pathFound = true;
                        System.out.format("Current X: %d Y: %d%n", newX, newY);
                        System.out.format("Destination X: %d Y: %d%n", endPosition.x, endPosition.y);

                        resultPath.push(new Vertex(nextVertex.getDistance() + 1, endPosition.x, endPosition.y));
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
                    }
                    resultPath.push(new Vertex(nextVertex.getDistance() + 1, newX, newY));
                    vertexQueue.add(new Vertex(nextVertex.getDistance() + 1, newX, newY));
                }
            }
        }
        if (!pathFound) {
            System.out.println("End not found!");
        }
    }

    private void displayPath(Point position) {
        mazeController.displayPath(position);
    }

    public void resetMaze() {
        pathFound = false;
    }
}
