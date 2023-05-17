package com.dt183g.project.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import java.io.IOException;
import java.net.URL;

public class FileReader {
    private BufferedImage mazeImage;

    public FileReader() { }

    public BufferedImage readMazeImage(String fileName) {
        URL url = getClass().getResource("/" + fileName);

        if (url != null) {
            try {
                mazeImage = ImageIO.read(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return mazeImage;
    }

    private BufferedImage removeColorThreshold(BufferedImage mazeImage) {
        short[] threshold = new short[256];

        for (int i = 0; i < 256; i++)
            threshold[i] = (i < 128) ? (short) 0 : (short) 255;

        BufferedImageOp thresholdOp = new LookupOp(new ShortLookupTable(0, threshold), null);
        BufferedImage destination = new BufferedImage(mazeImage.getWidth(), mazeImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        destination = thresholdOp.filter(mazeImage, destination);

        return destination;
    }

    public int[][] getMatrixFromImage(BufferedImage mazeImage) {
        mazeImage = removeColorThreshold(mazeImage);
        int[][] mazeMatrix;
        mazeMatrix = new int[mazeImage.getHeight()][mazeImage.getWidth()];

         for (int i = 0; i < mazeImage.getHeight(); i++) {
            for (int j = 0; j < mazeImage.getWidth(); j++) {
                mazeMatrix[i][j] = mazeImage.getRGB(j, i) == -1 ? 0 : 1;
            }
        }
        return mazeMatrix;
    }
}
