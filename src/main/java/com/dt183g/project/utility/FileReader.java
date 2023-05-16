package com.dt183g.project.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class FileReader {
    private BufferedImage mazeImage;

    public FileReader() { }

    private void readMazeImage(String fileName) {
        URL url = getClass().getResource("/" + fileName);

        if (url != null) {
            try {
                mazeImage = ImageIO.read(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int[][] getMatrixFromImage(String imageFileName) {
        int[][] mazeMatrix;
        readMazeImage(imageFileName);
        mazeMatrix = new int[mazeImage.getHeight()][mazeImage.getWidth()];

        for (int i = 0; i < mazeImage.getHeight(); i++) {
            for (int j = 0; j < mazeImage.getWidth(); j++) {
                mazeMatrix[i][j] = mazeImage.getRGB(j, i) == -1 ? 0 : 1;

            }
        }
        return mazeMatrix;
    }
}
