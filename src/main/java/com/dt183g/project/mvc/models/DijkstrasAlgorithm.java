package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.controllers.MazeController;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class DijkstrasAlgorithm {
    private MazeController controller;

    public DijkstrasAlgorithm(MazeController controller) {this.controller = controller; }

    public int solveMaze(int[][] maze, Point startPosition, Point endPosition) {
        Stack<Vertex> resultPath = new Stack<>();
        Queue<Vertex> vertexQueue = new LinkedList<>();
        int[][] mazePath = new int[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazePath[i][j] = (int)(1e9);
                //mazePath[i][j] = 90;
            }
        }

        mazePath[startPosition.y][startPosition.x] = 0;
        vertexQueue.add(new Vertex(0, startPosition.x, startPosition.y));
        resultPath.push(new Vertex(0, startPosition.x, startPosition.y));
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        int xDistance = Math.abs(startPosition.x - endPosition.x);
        int yDistance = Math.abs(startPosition.y - endPosition.y);
        int distance = 0;
        while (!vertexQueue.isEmpty()) {
            Vertex vertex = vertexQueue.remove();
            //controller.displayPath(new Point(vertex.getXCoordinate(), vertex.getYCoordinate()));
            for(int i = 0; i < 4; i++) {
                int newX = vertex.getXCoordinate() + dr[i];
                int newY = vertex.getYCoordinate() + dc[i];

                if (newX >= 0 && newX < maze[0].length && newY >= 0 && newY < maze.length && maze[newY][newX] == 1
                        && vertex.getDistance() + 1 < mazePath[newY][newX]) {

                    mazePath[newY][newX] = 1 + vertex.getDistance();

                    if (newX == endPosition.x && newY == endPosition.y) {
                        System.out.format("Current X: %d Y: %d%n", newX, newY);
                        System.out.format("Destination X: %d Y: %d%n", endPosition.x, endPosition.y);
//                        for (int x = 0; x < maze.length; x++) {
//                            for (int j = 0; j < maze[0].length; j++) {
//                                System.out.print(mazePath[x][j]);
//                            }
//                            System.out.println();
//                        }
                        resultPath.push(new Vertex(vertex.getDistance() + 1, endPosition.x, endPosition.y));
                        int finalLength = vertex.getDistance();
                        int counter = 1;
                        Vertex test = resultPath.pop();
                        System.out.format("X: %d Y: %d distance: %d%n", test.getXCoordinate(), test.getYCoordinate(), test.getDistance());
                        controller.displayPath(new Point(test.getXCoordinate(), test.getYCoordinate()));
                        while (!resultPath.isEmpty()) {
                            Vertex vertex1 = resultPath.pop();
//                            if (vertex1.getXCoordinate() == endPosition.x && vertex1.getYCoordinate() == endPosition.y) {
//                                System.out.format("X: %d Y: %d distance: %d%n", vertex1.getXCoordinate(), vertex1.getYCoordinate(), vertex1.getDistance());
//                                controller.displayPath(new Point(vertex1.getXCoordinate(), vertex1.getYCoordinate()));
//                                finalLength--;
//
//                            } else if (vertex1.getDistance() == finalLength) {
//
//                                if ((vertex1.getXCoordinate() == endPosition.x - counter || vertex1.getXCoordinate() == endPosition.x)
//                                        && (vertex1.getYCoordinate() == endPosition.y - counter || vertex1.getYCoordinate() == endPosition.y)) {
//                                    counter++;
//                                    finalLength--;
//                                    System.out.format("X: %d Y: %d distance: %d%n", vertex1.getXCoordinate(), vertex1.getYCoordinate(), vertex1.getDistance());
//                                    controller.displayPath(new Point(vertex1.getXCoordinate(), vertex1.getYCoordinate()));
//                                }
//                            }
                            if (test.getXCoordinate() == vertex1.getXCoordinate() && test.getYCoordinate() - 1 == vertex1.getYCoordinate()) {
                                //if (finalLength == vertex1.getDistance()) {
                                test = vertex1;
                                finalLength--;
                                System.out.format("X: %d Y: %d distance: %d%n", vertex1.getXCoordinate(), vertex1.getYCoordinate(), vertex1.getDistance());
                                controller.displayPath(new Point(vertex1.getXCoordinate(), vertex1.getYCoordinate()));
                                    // }

                            } else if (test.getXCoordinate() - 1 == vertex1.getXCoordinate() && test.getYCoordinate() == vertex1.getYCoordinate()) {
                                test = vertex1;
                                System.out.format("X: %d Y: %d distance: %d%n", vertex1.getXCoordinate(), vertex1.getYCoordinate(), vertex1.getDistance());
                                controller.displayPath(new Point(vertex1.getXCoordinate(), vertex1.getYCoordinate()));

                            } else if (test.getXCoordinate() == vertex1.getXCoordinate() && test.getYCoordinate() + 1 == vertex1.getYCoordinate()) {
                                test = vertex1;
                                System.out.format("X: %d Y: %d distance: %d%n", vertex1.getXCoordinate(), vertex1.getYCoordinate(), vertex1.getDistance());
                                controller.displayPath(new Point(vertex1.getXCoordinate(), vertex1.getYCoordinate()));

                            } else if (test.getXCoordinate() + 1 == vertex1.getXCoordinate() && test.getYCoordinate() == vertex1.getYCoordinate()) {
                                test = vertex1;
                                System.out.format("X: %d Y: %d distance: %d%n", vertex1.getXCoordinate(), vertex1.getYCoordinate(), vertex1.getDistance());
                                controller.displayPath(new Point(vertex1.getXCoordinate(), vertex1.getYCoordinate()));
                            }
                        }
//                        resultPath.forEach(vertex1 -> {
//
//                            System.out.format("X: %d Y: %d distance: %d%n", vertex1.getXCoordinate(), vertex1.getYCoordinate(), vertex1.getDistance());
//                            controller.displayPath(new Point(vertex1.getXCoordinate(), vertex1.getYCoordinate()));
//
//                        });
                        return vertex.getDistance() + 1;
                    }

//                    if (vertex.getDistance() - resultPath.peek().getDistance() == 1 || vertex.getDistance() - resultPath.peek().getDistance() == 0) {
//                        if (Math.abs(newX - endPosition.x) < Math.abs(resultPath.peek().getXCoordinate() - endPosition.x) ||
//                        Math.abs(newY - endPosition.y) < Math.abs(resultPath.peek().getYCoordinate() - endPosition.y)) {
//                            resultPath.push(new Vertex(vertex.getDistance() + 1, newX, newY));
//                        }
//                    }

//                    if (Math.abs(newX - endPosition.x) <= Math.abs(vertex.getXCoordinate() - endPosition.x)
//                            && Math.abs(newY - endPosition.y) <= Math.abs(vertex.getYCoordinate() - endPosition.y)) {
//
//                    }
//                        if (Math.abs(newX - xDistance) > Math.abs(vertex.getXCoordinate() - xDistance)
//                                || Math.abs(newY - yDistance) > Math.abs(vertex.getYCoordinate() - yDistance)) {
//                            if (distance < vertex.getDistance() + 1) {
//                                distance = vertex.getDistance() + 1;
//                                System.out.format("old: X: %d Y: %d%n", vertex.getXCoordinate(), vertex.getYCoordinate());
//                                System.out.format("X: %d Y: %d distance: %d%n", newX, newY, vertex.getDistance() + 1);
//                                controller.displayPath(new Point(newX, newY));
//                            }
//                        }

                    if (Math.abs(newX - endPosition.x) < Math.abs(vertex.getXCoordinate() - endPosition.x)
                            && Math.abs(newY - endPosition.y) <= Math.abs(vertex.getYCoordinate() - endPosition.y)) {
                        if (newX == vertex.getXCoordinate() + 1 || newX == vertex.getXCoordinate()
                                && newY == vertex.getYCoordinate() + 1 || newY == vertex.getYCoordinate()) {

                           // resultPath.push(new Vertex(vertex.getDistance() + 1, newX, newY));
                        }
                    }
                    resultPath.push(new Vertex(vertex.getDistance() + 1, newX, newY));
                    vertexQueue.add(new Vertex(vertex.getDistance() + 1, newX, newY));
                }
            }
        }
        return -1;
    }
}
