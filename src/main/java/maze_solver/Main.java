package maze_solver;

import maze_solver.mvc.controllers.MazeController;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Class containing the main entry point of the application.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
public class Main {
    public static void main(String... args) {
        System.out.println("Maze!");

        final String imageResourceName = "med7.jpg";

        try {
            // Read the actual maze image into an object.
            BufferedImage mazeImage = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/" + imageResourceName)));

            // Start an instance of the "new" MVC implementation, providing the
            // image file as a parameter.
            SwingUtilities.invokeLater(() -> new MazeController(mazeImage));
        } catch (IOException e) {
            System.out.println("Failed to read maze image resource. This is should never happen, and means that there is a bug, or that the jar is broken.");
            System.exit(1);
        }
    }
}
