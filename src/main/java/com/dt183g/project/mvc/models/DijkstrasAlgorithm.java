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
                mazePath[i][j] = (int)(1e9);
            }
        }

        mazePath[startPosition.y / 10][startPosition.x / 10] = 0;
        vertexQueue.add(new Vertex(0, startPosition.x / 10, startPosition.y / 10));
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        while (!vertexQueue.isEmpty()) {
            Vertex vertex = vertexQueue.remove();
            controller.displayPath(new Point(vertex.getXCoordinate() * 10, vertex.getYCoordinate() * 10));
            for(int i = 0; i < 4;i++) {
                int newX = vertex.getXCoordinate() + dr[i];
                int newY = vertex.getYCoordinate() + dc[i];

                if (newX >= 0 && newX < maze[0].length && newY >= 0 && newY < maze.length && maze[newY][newX] == 1
                        && vertex.getDistance() + 1 < mazePath[newY][newX]) {

                    mazePath[newY][newX] = 1 + vertex.getDistance();

                    if (newX == endPosition.x / 10 && newY == endPosition.y / 10) {
                        return vertex.getDistance() + 1;
                    }
                    vertexQueue.add(new Vertex(vertex.getDistance() + 1, newX, newY));
                }
            }
        }
        return -1;
    }
}
