package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.controllers.MazeController;

import java.awt.Point;
import java.util.ArrayList;

public class MazeModel {
    private final MazeController mazeController;
    private ArrayList<Point> test = new ArrayList<>();

    public MazeModel(MazeController controller) {
        this.mazeController = controller;

    }

    public void solveMaze(Point startPosition, Point endPosition) {
        // TODO Implement...
        test.add(startPosition);
        test.add(endPosition);
        for (int i = startPosition.x; i < 150; i++) {
            mazeController.displayPath(new Point(i, startPosition.y));
        }
    }
}
