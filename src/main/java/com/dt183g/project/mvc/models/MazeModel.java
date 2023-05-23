package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.controllers.MazeController;
import com.dt183g.project.utility.FileReader;

import java.awt.Point;
import java.util.Stack;

public class MazeModel {
    private final MazeController mazeController;
    private int[][] maze;
    private int[][] mazePath;
    private Stack<int[]> stack = new Stack<>();
    private boolean findingPath = true;
    private String previousMove = "";

    public MazeModel(int[][] maze, MazeController controller) {
        this.maze = maze;
        this.mazeController = controller;
    }

    public void solveMaze(Point startPosition, Point endPosition) {
        stack.push(new int[]{startPosition.y / 10, startPosition.x / 10});
        mazePath = new int[maze.length][maze[0].length];
        mazePath[startPosition.y / 10][startPosition.x / 10] = maze[startPosition.y / 10][startPosition.x / 10];
        findMazePath(endPosition);
        // TODO Implement...
    }

    private void printMaze() {
            int rowCounter = 0;
            for (int i = 0; i < mazePath.length; i++) {
                for (int j = 0; j < mazePath[i].length; j++) {
                    System.out.printf("%d", mazePath[i][j]);
                }
                System.out.print(" " + rowCounter);
                rowCounter++;
                System.out.println();
            }

            rowCounter = 0;
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    System.out.printf("%d", maze[i][j]);
                }
                System.out.print(" " + rowCounter);
                rowCounter++;
                System.out.println();
            }
    }

    public void findMazePath(Point endPosition) {
        while (findingPath) {
            // Get current x-position and y-position
            if (stack.isEmpty()) {
                printMaze();
            }
            int xPosition = stack.peek()[1];
            int yPosition = stack.peek()[0];

            // If x and y-position is in the bottom right corner of the maze (the exit)
            if (xPosition == endPosition.x / 10 && yPosition == endPosition.y / 10) {
                // Stop searching and write the resulting path to the file
                findingPath = false;
                System.out.println("FOUND!");
            } else {
                checkAdjacentPaths(xPosition, yPosition, stack.peek());
            }
        }
        printMaze();
    }

    private boolean test(final int currentXPos, final int currentYPos, final int[] currentPos) {
        switch (previousMove) {
            case "right" -> {
                if (checkUp(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkDown(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkRight(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkLeft(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                return false;
            }
            case "left" -> {
                if (checkLeft(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkUp(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkDown(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkRight(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                // If no path exists, stop loop and write to file
                return false;
            }
            case "up" -> {
                if (checkRight(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkLeft(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkUp(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkDown(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                return false;
            }
            case "down" -> {
                if (checkDown(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkRight(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkLeft(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                if (checkUp(currentXPos, currentYPos, currentPos)) {
                    return true;
                }
                // If no path exists, stop loop and write to file
                return false;
            }
            default -> {
                return false;
            }
        }
    }

    private void checkAdjacentPaths(final int currentXPos, final int currentYPos, final int[] currentPos) {
        // Check right position is not outside the maze and if moving right is possible (1)
        if (test(currentXPos, currentYPos, currentPos)) {
            return;
        }
        if (checkRight(currentXPos, currentYPos, currentPos)) {
            return;
        }
        if (checkUp(currentXPos, currentYPos, currentPos)) {
            return;
        }
        if (checkLeft(currentXPos, currentYPos, currentPos)) {
            return;
        }
        if (checkDown(currentXPos, currentYPos, currentPos)) {
            return;
        }

        // If no path exists, stop loop and write to file
        findingPath = false;
        System.out.println("NOT FOUND!");
    }

    private boolean checkRight(final int currentXPos, final int currentYPos, final int[] currentPos) {
        if (currentXPos < getMazeColumns() - 1 && maze[currentYPos][currentXPos + 1] == 2) {
            System.out.println("RIGHT!");
            previousMove = "right";
            move(currentXPos + 1, currentYPos, currentPos);
            return true;
        }
        return false;
    }

    private boolean checkLeft(final int currentXPos, final int currentYPos, final int[] currentPos) {
        if (currentXPos > 0 && maze[currentYPos][currentXPos - 1] == 2) {
            System.out.println("LEFT!");
            previousMove = "left";
            move(currentXPos - 1, currentYPos, currentPos);
            return true;
        }
        return false;
    }

    private boolean checkUp(final int currentXPos, final int currentYPos, final int[] currentPos) {
        if (currentYPos > 0 && maze[currentYPos - 1][currentXPos] == 2) {
            System.out.println("UP!");
            previousMove = "up";
            move(currentXPos, currentYPos - 1, currentPos);
            return true;
        }
        return false;
    }

    private boolean checkDown(final int currentXPos, final int currentYPos, final int[] currentPos) {
        if (currentYPos < getMazeRows() - 1 && maze[currentYPos + 1][currentXPos] == 2) {
            System.out.println("DOWN!");
            previousMove = "down";
            move(currentXPos, currentYPos + 1, currentPos);
            return true;
        }
        return false;
    }

    private void move(final int nextXPos, final int nextYPos, final int[] currentPosition) {
        // If the stack has already added the next coordinates before
        //mazeController.displayPath(new Point(nextXPos * 10, nextYPos * 10));
        System.out.format("X: %d Y: %d%n", nextXPos, nextYPos);

        if (mazePath[nextYPos][nextXPos] == 2) {
            // Set the path to non-walkable
            maze[currentPosition[0]][currentPosition[1]] = 8;
            // Remove the coordinates
            stack.pop();

        } else {
            mazeController.displayPath(new Point(nextXPos * 10, nextYPos * 10));
            //System.out.format("X: %d Y: %d%n", nextXPos, nextYPos);
            // Add the new path to the "empty" maze and push the coordinates to the stack
            mazePath[nextYPos][nextXPos] = 2;
            stack.push(new int[]{nextYPos, nextXPos});
        }
    }

    public void resetMaze(int[][] maze) {
        this.maze = maze;
        stack.clear();
        findingPath = true;
    }

    private int getMazeRows() {
        return maze.length;
    }

    private int getMazeColumns() {
        return maze[0].length;
    }
}
