package com.dt183g.project;

import com.dt183g.project.mvc.controllers.MazeController;
import com.dt183g.project.mvc.controllers.MazeControllerOBS;
import com.dt183g.project.utility.FileReader;

import javax.swing.SwingUtilities;

/**
 * Class containing the main entry point of the application.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
public class Main {
    public static void main(String... args) {
        System.out.println("Maze!");

        // Start an instance of the "old" non-MVC implementation.
        SwingUtilities.invokeLater(MazeController::new);

        // Start an instance of the "new" MVC implementation, providing the
        // image file as a parameter.
        SwingUtilities.invokeLater(() -> new MazeControllerOBS(new FileReader().readMazeImage("med7.jpg")));
    }
}
