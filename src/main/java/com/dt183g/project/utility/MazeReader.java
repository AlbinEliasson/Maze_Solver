package com.dt183g.project.utility;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Class implementing a reader for the maze image; used to convert the maze
 * image to an int-matrix representation used for solving.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
public class MazeReader {
    // How many points on each size of the maze to probe when attempting to find
    // the border thickness, offset and block size.
    private static final int PROBE_FACTOR = 16;

    // The backing maze image
    private final BufferedImage backingImage;

    // The size of the image itself.
    private final int imageHeight;
    private final int imageWidth;

    // Offset is the space between the start/end of the image and the outer
    // border of the maze in the image.
    private int offsetStartX;
    private int offsetEndX;
    private int offsetStartY;
    private int offsetEndY;

    // The maze size is the size of the maze matrix representation.
    private int mazeWidth;
    private int mazeHeight;

    // 0 = Open cell, 1 = Normal wall
    private int[][] mazeMatrix;

    /**
     * Constructor for creating a new maze object from an image.
     *
     * @param backingImage Image of a maze.
     */
    public MazeReader(BufferedImage backingImage) {
        this.backingImage = backingImage;

        this.imageHeight = this.backingImage.getHeight();
        this.imageWidth = this.backingImage.getWidth();

        this.findOffsets();
        this.findMazeDimensions();
        this.readMaze();
    }

    /**
     * Method for loading a maze from a resource.
     *
     * @param file Name of the resource.
     *
     * @return A maze object.
     *
     * @throws IOException If the resource can't be found.
     */
    public static MazeReader fromResource(String file) throws IOException {
        return new MazeReader(ImageIO.read(Objects.requireNonNull(MazeReader.class.getResource("/" + file))));
    }

    /**
     * Method for getting the backing image.
     *
     * @return The backing image.
     */
    public BufferedImage getBackingImage() {
        return this.backingImage;
    }

    /**
     * Method for getting the raw maze matrix.
     *
     * @return The maze matrix.
     */
    public int[][] getMazeMatrix() {
        return this.mazeMatrix;
    }

    /**
     * Method for getting the value in a specific cell.
     *
     * @param x The (matrix) x-coordinate.
     * @param y The (matrix) y-coordinate.
     * @return The value of the cell.
     */
    public int getCell(int x, int y) {
        if(x < 0 || x > mazeWidth - 1 || y < 0 || y > mazeHeight - 1) {
            throw new IndexOutOfBoundsException("Invalid cell coordinates provided!");
        }

        return this.mazeMatrix[x][y];
    }

    /**
     * Method for getting the width of the maze matrix.
     *
     * @return The width of the matrix.
     */
    public int getMatrixWidth() {
        return this.mazeWidth;
    }

    /**
     * Method for getting the height of the maze matrix.
     *
     * @return The height of the matrix.
     */
    public int getMatrixHeight() {
        return this.mazeHeight;
    }

    /**
     * Method for getting the width of the backing image.
     *
     * @return The width of the image.
     */
    public int getImageWidth() {
        return this.imageWidth;
    }

    /**
     * Method for getting the height of the backing image.
     *
     * @return The height of the image.
     */
    public int getImageHeight() {
        return this.imageHeight;
    }

    /**
     * Method for translating an X coordinate on the backing image to a
     * corresponding X coordinate in the maze matrix.
     *
     * @param imageX Image X coordinate.
     *
     * @return Maze matrix X coordinate.
     */
    public int translateImageXToMatrixX(int imageX) {
        return (imageX - this.offsetStartX);
    }

    /**
     * Method for translating a Y coordinate on the backing image to a
     * corresponding Y coordinate in the maze matrix.
     *
     * @param imageY Image Y coordinate.
     *
     * @return Maze matrix Y coordinate
     */
    public int translateImageYToMatrixY(int imageY) {
        return (imageY - this.offsetStartY);
    }

    /**
     * Method for validating that an (image) x-coordinate in withing a valid
     * maze cell.
     *
     * @param imageX The (image) x-coordinate.
     * @return Whether the location is inside a cell.
     */
    public boolean isValidImageX(int imageX) {
        return imageX >= this.offsetStartX && imageX < this.imageWidth - this.offsetEndX;
    }

    /**
     * Method for validating that an (image) y-coordinate in withing a valid
     * maze cell.
     *
     * @param imageY The (image) y-coordinate.
     * @return Whether the location is inside a cell.
     */
    public boolean isValidImageY(int imageY) {
        return imageY >= this.offsetStartY && imageY < this.imageHeight - this.offsetEndY;
    }

    /**
     * Method for translating an X coordinate in the maze matrix to a
     * corresponding X coordinate on the backing image.
     *
     * @param matrixX Maze matrix X coordinate.
     *
     * @return Image X coordinate.
     */
    public int translateMatrixXToImageX(int matrixX) {
        return matrixX + this.offsetStartX;
    }

    /**
     * Method for translating a Y coordinate in the maze matrix to a
     * corresponding Y coordinate on the backing image.
     *
     * @param matrixY Maze matrix Y coordinate.
     *
     * @return Image Y coordinate.
     */
    public int translateMatrixYToImageY(int matrixY) {
        return matrixY + this.offsetStartY;
    }

    /**
     * Internal method for finding the maze offsets, AKA. the spacing between
     * the start of the image and the edge of the maze in all four directions.
     */
    private void findOffsets() {
        int[] probeResults = new int[MazeReader.PROBE_FACTOR];
        int[] probeYs = IntStream.range(0, MazeReader.PROBE_FACTOR).map(x -> (imageHeight / (MazeReader.PROBE_FACTOR + 1)) * (x + 1)).toArray();
        int[] probeXs = IntStream.range(0, MazeReader.PROBE_FACTOR).map(x -> (imageWidth / (MazeReader.PROBE_FACTOR + 1)) * (x + 1)).toArray();

        // STEP 1: Find starting X offset
        for(int yi = 0; yi < probeYs.length; yi++) {
            int y = probeYs[yi];
            for(int x = 0; x < this.imageWidth; x++) {
                if(this.isBorderColor(this.backingImage.getRGB(x, y))) {
                    probeResults[yi] = x;
                    break;
                }
            }
        }
        Arrays.sort(probeResults);
        if(!this.isSubArrayEqual(probeResults, probeResults.length / 2 - 1, probeResults.length / 2 + 1 + probeResults.length % 2))
            throw new RuntimeException("Unable to find maze image starting x offset!");
        this.offsetStartX = probeResults[probeResults.length / 2];

        // STEP 2: Find ending X offset
        for(int yi = 0; yi < probeYs.length; yi++) {
            int y = probeYs[yi];
            for(int x = 0; x < this.imageWidth; x++) {
                if(this.isBorderColor(this.backingImage.getRGB(this.imageWidth - x - 1, y))) {
                    probeResults[yi] = x;
                    break;
                }
            }
        }
        Arrays.sort(probeResults);
        if(!this.isSubArrayEqual(probeResults, probeResults.length / 2 - 1, probeResults.length / 2 + 1 + probeResults.length % 2))
            throw new RuntimeException("Unable to find maze image ending x offset!");
        this.offsetEndX = probeResults[probeResults.length / 2];

        // STEP 3: Find starting Y offset
        for(int xi = 0; xi < probeXs.length; xi++) {
            int x = probeXs[xi];
            for(int y = 0; y < this.imageHeight; y++) {
                if(this.isBorderColor(this.backingImage.getRGB(x, y))) {
                    probeResults[xi] = y;
                    break;
                }
            }
        }
        Arrays.sort(probeResults);
        if(!this.isSubArrayEqual(probeResults, probeResults.length / 2 - 1, probeResults.length / 2 + 1 + probeResults.length % 2))
            throw new RuntimeException("Unable to find maze image starting y offset!");
        this.offsetStartY = probeResults[probeResults.length / 2];

        // STEP 4: Find ending Y offset
        for(int xi = 0; xi < probeXs.length; xi++) {
            int x = probeXs[xi];
            for(int y = 0; y < this.imageHeight; y++) {
                if(this.isBorderColor(this.backingImage.getRGB(x, this.imageHeight - y - 1))) {
                    probeResults[xi] = y;
                    break;
                }
            }
        }
        Arrays.sort(probeResults);
        if(!this.isSubArrayEqual(probeResults, probeResults.length / 2 - 1, probeResults.length / 2 + 1 + probeResults.length % 2))
            throw new RuntimeException("Unable to find maze image ending y offset!");
        this.offsetEndY = probeResults[probeResults.length / 2];
    }

    /**
     * Internal method for checking if an RGB value corresponds to the color
     * expected for "border pixels" in the maze image.
     *
     * @param rgb Color to check.
     *
     * @return Whether the color is a "border color".
     */
    private boolean isBorderColor(int rgb) {
        Color pixel = new Color(rgb);
        return pixel.getRed() < 32 && pixel.getGreen() < 32 && pixel.getBlue() < 32;
    }

    /**
     * Internal method for checking if the elements of a (sub)array are all
     * equal.
     *
     * @param array The backing array.
     * @param start The starting index (inclusive).
     * @param end The ending index (exclusive).
     *
     * @return Whether elements in (sub)array are all equal.
     *
     * @throws NullPointerException If array is null.
     * @throws ArrayIndexOutOfBoundsException If start < 0 or end > array.length - 1.</>
     */
    private boolean isSubArrayEqual(int[] array, int start, int end) {
        if(array == null) {
            throw new NullPointerException();
        } else if(start < 0 || end > array.length - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        for(int i = start; i < end - 1; i ++) {
            if (array[i] != array[i + 1])
                return false;
        }

        return true;
    }

    /**
     * Internal method for finding the actual "block" dimensions of the maze
     * image. The results are equivalent to the size of the backing maze matrix.
     */
    private void findMazeDimensions() {
        this.mazeWidth = this.imageWidth - this.offsetStartX - this.offsetEndX;
        this.mazeHeight = this.imageHeight - this.offsetStartY - this.offsetEndY;
    }

    /**
     * Internal method for reading the maze image and populating the backing
     * maze matrix with the correct values.
     */
    private void readMaze() {
        this.mazeMatrix = new int[this.mazeWidth][this.mazeHeight];

        for(int y = 0; y < this.mazeHeight; y++) {
            for(int x = 0; x < this.mazeWidth; x++) {
                int actualX = this.translateMatrixXToImageX(x);
                int actualY = this.translateMatrixYToImageY(y);

                if(!this.isBorderColor(this.backingImage.getRGB(actualX, actualY))) {
                    // 0 represents a normal "open cell".
                    this.mazeMatrix[x][y] = 0;
                } else {
                    // 1 represents a normal "wall".
                    this.mazeMatrix[x][y] = 1;
                }
            }
        }
    }
}
