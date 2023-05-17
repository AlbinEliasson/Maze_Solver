package com.dt183g.project.utility;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
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

        short[] threshold = new short[256];
         for (int i = 0; i < 256; i++)
               threshold[i] = (i < 128) ? (short) 0 : (short) 255;

         BufferedImageOp thresholdOp = new LookupOp(new ShortLookupTable(0, threshold), null);
         BufferedImage destination = new BufferedImage(mazeImage.getWidth(), mazeImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
         destination = thresholdOp.filter(mazeImage, destination);

         for (int i = 0; i < mazeImage.getHeight(); i++) {
            for (int j = 0; j < mazeImage.getWidth(); j++) {
//                Color color = new Color(mazeImage.getRGB(j, i));
//                if (color.equals(color.get)) {
//
//                }
                mazeMatrix[i][j] = destination.getRGB(j, i) == -1 ? 0 : 1;
                //mazeMatrix[i][j] = destination.getRGB(j, i);

            }
        }

        return mazeMatrix;
    }
}
