package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.controllers.MazeController;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class DijkstrasAlgorithm {
    private MazeController controller;

    public DijkstrasAlgorithm(MazeController controller) {this.controller = controller; }

    public int solveMaze(int[][] maze, Point startPosition, Point endPosition) {
        Queue<Vertex> vertexQueue = new LinkedList<>();
        int[][] mazePath = new int[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                //mazePath[i][j] = (int)(1e9);
                mazePath[i][j] = 90;
            }
        }

        mazePath[startPosition.y][startPosition.x] = 0;
        vertexQueue.add(new Vertex(0, startPosition.x, startPosition.y));
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        while (!vertexQueue.isEmpty()) {
            Vertex vertex = vertexQueue.remove();
            controller.displayPath(new Point(vertex.getXCoordinate(), vertex.getYCoordinate()));
            for(int i = 0; i < 4; i++) {
                int newX = vertex.getXCoordinate() + dr[i];
                int newY = vertex.getYCoordinate() + dc[i];

                if (newX >= 0 && newX < maze[0].length && newY >= 0 && newY < maze.length && maze[newY][newX] == 1
                        && vertex.getDistance() + 1 < mazePath[newY][newX]) {

                    mazePath[newY][newX] = 1 + vertex.getDistance();

                    if (newX == endPosition.x && newY == endPosition.y) {
                        System.out.format("Current X: %d Y: %d%n", newX, newY);
                        System.out.format("Destination X: %d Y: %d%n", endPosition.x, endPosition.y);
                        for (int x = 0; x < maze.length; x++) {
                            for (int j = 0; j < maze[0].length; j++) {
                                System.out.print(mazePath[x][j]);
                            }
                            System.out.println();
                        }
                        return vertex.getDistance() + 1;
                    }
                    vertexQueue.add(new Vertex(vertex.getDistance() + 1, newX, newY));
                }
            }
        }
        return -1;
    }
}
