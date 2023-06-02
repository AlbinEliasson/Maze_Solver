package com.dt183g.project;

import com.dt183g.project.mvc.controllers.MazeController;
import com.dt183g.project.mvc.controllers.MazeControllerOBS;
import com.dt183g.project.utility.FileReader;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String... args) {
        System.out.println("Maze!");

        SwingUtilities.invokeLater(MazeController::new);

        SwingUtilities.invokeLater(() -> new MazeControllerOBS(new FileReader().readMazeImage("med7.jpg")));
    }
}
