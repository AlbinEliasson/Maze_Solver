package com.dt183g.project.mvc.controllers;

import com.dt183g.project.mvc.models.MazeModel;
import com.dt183g.project.mvc.views.MazeView;
import com.dt183g.project.utility.FileReader;

public class MazeController {
    private FileReader fileReader;
    private MazeModel mazeModel;
    private MazeView mazeView;

    public MazeController() {
        fileReader = new FileReader();
        mazeModel = new MazeModel();
        mazeView = new MazeView(fileReader.readMazeImage("med7.jpg"));
    }
}
