package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.controllers.MazeController;

import java.awt.Point;

public class MazeModel {
    private final MazeController mazeController;

    public MazeModel(MazeController controller) {
        this.mazeController = controller;

    }

    public void solveMaze(Point startPosition, Point endPosition) {
        // TODO Implement...
    }
}
