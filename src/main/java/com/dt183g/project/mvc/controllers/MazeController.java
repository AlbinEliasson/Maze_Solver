package com.dt183g.project.mvc.controllers;

import com.dt183g.project.mvc.models.MazeModel;
import com.dt183g.project.mvc.views.MazeView;
import com.dt183g.project.utility.FileReader;

import java.awt.Point;

public class MazeController {
    private FileReader fileReader;
    private MazeModel mazeModel;
    private MazeView mazeView;

    public MazeController() {
        //TODO Either utilize the controller directly for calling methods or use an observer pattern
        fileReader = new FileReader();
        mazeModel = new MazeModel(this);
        mazeView = new MazeView(fileReader.readMazeImage("med7.jpg"), this);
    }

    public void solveMaze(Point startPosition, Point endPosition) {
        System.out.printf("Start position X: %d Y: %d | End position X: %d Y: %d%n", startPosition.x, startPosition.y, endPosition.x, endPosition.y);
        mazeModel.solveMaze(startPosition, endPosition);
    }

}
