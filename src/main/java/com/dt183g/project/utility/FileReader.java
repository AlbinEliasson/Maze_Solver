package com.dt183g.project.utility;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
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

    public BufferedImage removeColorThreshold(BufferedImage mazeImage) {
        short[] threshold = new short[256];

        for (int i = 0; i < 256; i++)
            threshold[i] = (i < 128) ? (short) 0 : (short) 255;

        BufferedImageOp thresholdOp = new LookupOp(new ShortLookupTable(0, threshold), null);
        BufferedImage destination = new BufferedImage(mazeImage.getWidth(), mazeImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        destination = thresholdOp.filter(mazeImage, destination);

        return destination;
    }

    public int[][] getMatrixFromImage(BufferedImage mazeImage) {
        //mazeImage = removeColorThreshold(mazeImage);
        int[][] mazeMatrix;
        mazeMatrix = new int[mazeImage.getHeight()][mazeImage.getWidth()];

         for (int i = 0; i < mazeImage.getHeight(); i++) {
            for (int j = 0; j < mazeImage.getWidth(); j++) {
                mazeMatrix[i][j] = mazeImage.getRGB(j, i) == -1 ? 2 : 1;
            }
        }
        return mazeMatrix;
    }

    public BufferedImage resizeImage(BufferedImage image, int width, int height) {
        image = removeColorThreshold(image);
        Image tmpImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmpImage, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }
}
