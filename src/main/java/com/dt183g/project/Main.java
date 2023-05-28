package com.dt183g.project;

import com.dt183g.project.mvc.controllers.MazeController;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String... args) {
        System.out.println("Maze!");

        SwingUtilities.invokeLater(MazeController::new);
    }
}
