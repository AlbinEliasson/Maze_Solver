package com.dt183g.project.mvc.controllers;

import com.dt183g.project.mvc.models.MazeModel;
import com.dt183g.project.mvc.views.MazeView;
import com.dt183g.project.utility.FileReader;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class MazeController {
    private final FileReader fileReader;
    private final MazeModel mazeModel;
    private final MazeView mazeView;

    public MazeController() {
        //TODO Either utilize the controller directly for calling methods or use an observer pattern
        fileReader = new FileReader();

        BufferedImage mazeImage = fileReader.readMazeImage("med7.jpg");
        mazeView = new MazeView(mazeImage, this);

        BufferedImage rescaledMazeImage = fileReader.resizeImage(mazeImage, mazeImage.getWidth() / 10, mazeImage.getHeight() / 10);
        //mazeImage = fileReader.removeColorThreshold(mazeImage);

        int[][] maze = fileReader.getMatrixFromImage(rescaledMazeImage);

        mazeModel = new MazeModel(maze, this);

//
//        for (int i = 0; i < maze.length; i++) {
//            for (int j = 0; j < maze[i].length; j++) {
////                if (test[i][j] == 1) {
////                    System.out.printf("%d", test[i][j]);
////                } else {
////                    System.out.print(" ");
////                }
//                System.out.printf("%d", maze[i][j]);
//            }
//            System.out.println();
//        }
    }

    public void solveMaze(Point startPosition, Point endPosition) {
        System.out.printf("Start position X: %d Y: %d | End position X: %d Y: %d%n", startPosition.x, startPosition.y, endPosition.x, endPosition.y);
        mazeModel.solveMaze(startPosition, endPosition);
    }

    public void displayPath(Point position) {
        mazeView.displayMazePath(position);
    }

}
