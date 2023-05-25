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
    private final BufferedImage mazeImage;

    public MazeController() {
        //TODO Either utilize the controller directly for calling methods or use an observer pattern
        fileReader = new FileReader();
        mazeImage = fileReader.readMazeImage("med7.jpg");
        mazeView = new MazeView(mazeImage, this);
        mazeModel = new MazeModel(this);
    }

    public void solveMaze(Point startPosition, Point endPosition, String algorithm) {
        System.out.printf("Start position X: %d Y: %d | End position X: %d Y: %d%n", startPosition.x, startPosition.y, endPosition.x, endPosition.y);
        mazeModel.solveMaze(startPosition, endPosition, getMaze(), algorithm);
    }

    public void displayPath(Point position) {
        mazeView.displayMazePath(position);
    }

    public void resetMaze() {
        mazeView.resetImage();
        mazeModel.resetMaze();
    }

    public int[][] getMaze() {
        return fileReader.getMatrixFromImage(fileReader.removeColorThreshold(mazeImage));
    }
}
