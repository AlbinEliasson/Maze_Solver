package com.dt183g.project.utility;

import com.dt183g.project.mvc.models.types.MazePoint;

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
    private static final int PROBE_FACTOR = 8;

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

    // The border thickness is the size of the walls between cells in the maze.
    private int borderThickness;

    // A "block" is the size of a single cell in the image.
    private int blockWidth;
    private int blockHeight;

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
        this.findBorderSize();
        this.findBlockSize();
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
        // TODO: Return copy of matrix instead.
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
        // TODO: Validate to make safe.
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
     * A direction.
     */
    public enum Direction {
        North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest, Center
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
        // TODO: Optimize this.
        return ((imageX - this.offsetStartX) / (this.blockWidth + this.borderThickness)) * 2 + ((imageX - this.offsetStartX) % (this.blockWidth + this.borderThickness) == 0 ? 0 : 1);
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
        // TODO: Optimize this.
        return ((imageY - this.offsetStartY) / (this.blockHeight + this.borderThickness)) * 2 + ((imageY - this.offsetStartY) % (this.blockHeight + this.borderThickness) == 0 ? 0 : 1);
    }

    /**
     * Method for normalizing a (image) x-coordinate to a specific directing in
     * the closest cell.
     *
     * @param imageX The (image) x-coordinate.
     * @param direction The section of the cell to normalize to.
     * @return The normalized x-coordinate.
     */
    public int normalizeImageX(int imageX, Direction direction) {
        // TODO: Maybe redo this, it feels inefficient.
        return this.translateMatrixXToImageX(this.translateImageXToMatrixX(imageX), direction);
    }

    /**
     * Method for normalizing a (image) x-coordinate to the closest cell.
     *
     * @param imageX The (image) x-coordinate.
     * @return The normalized x-coordinate.
     */
    public int normalizeImageX(int imageX) {
        return this.normalizeImageX(imageX, Direction.Center);
    }

    /**
     * Method for normalizing a (image) y-coordinate to a specific directing in
     * the closest cell.
     *
     * @param imageY The (image) y-coordinate.
     * @param direction The section of the cell to normalize to.
     * @return The normalized y-coordinate.
     */
    public int normalizeImageY(int imageY, Direction direction) {
        // TODO: Maybe redo this, it feels inefficient.
        return this.translateMatrixYToImageY(this.translateImageYToMatrixY(imageY), direction);
    }

    /**
     * Method for normalizing a (image) y-coordinate to the closest cell.
     *
     * @param imageY The (image) y-coordinate.
     * @return The normalized y-coordinate.
     */
    public int normalizeImageY(int imageY) {
        return this.normalizeImageY(imageY, Direction.Center);
    }

    /**
     * Method for validating that an (image) x-coordinate in withing a valid
     * maze cell.
     *
     * @param imageX The (image) x-coordinate.
     * @return Whether the location is inside a cell.
     */
    public boolean isValidImageX(int imageX) {
        // TODO: Don't flipping do this, it is massively redundant since it
        //       requires that the matrixX be calculated just to validate. Which
        //       means that it will have to be done twice when actually
        //       performing the "real" action later.
        int matrixX = this.translateImageXToMatrixX(imageX);
        return matrixX >= 0 && matrixX < this.mazeWidth - 1;
    }

    /**
     * Method for validating that an (image) y-coordinate in withing a valid
     * maze cell.
     *
     * @param imageY The (image) y-coordinate.
     * @return Whether the location is inside a cell.
     */
    public boolean isValidImageY(int imageY) {
        // TODO: Don't flipping do this, it is massively redundant since it
        //       requires that the matrixY be calculated just to validate. Which
        //       means that it will have to be done twice when actually
        //       performing the "real" action later.
        int matrixY = this.translateImageYToMatrixY(imageY);
        return matrixY >= 0 && matrixY < this.mazeHeight - 1;
    }

    /**
     * Method for translating an X coordinate in the maze matrix to a
     * corresponding X coordinate on the backing image.
     *
     * @param matrixX Maze matrix X coordinate.
     * @param direction Section of cell on image to point to.
     *
     * @return Image X coordinate.
     */
    public int translateMatrixXToImageX(int matrixX, Direction direction) {
        int base = this.offsetStartX + ((this.blockWidth + this.borderThickness) * (matrixX / 2));
        return switch(direction) {
            case West, NorthWest, SouthWest -> base + (matrixX % 2 == 0 ? 0 : this.borderThickness);
            case Center, North, South -> base + (matrixX % 2 == 0 ? 0 : (this.blockWidth / 2) + this.borderThickness);
            case East, NorthEast, SouthEast -> base + (matrixX % 2 == 0 ? 0 : this.blockWidth + this.borderThickness);
        };
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
        return this.translateMatrixXToImageX(matrixX, Direction.Center);
    }

    /**
     * Method for translating a Y coordinate in the maze matrix to a
     * corresponding Y coordinate on the backing image.
     *
     * @param matrixY Maze matrix Y coordinate.
     * @param direction Section of cell on image to point to.
     *
     * @return Image Y coordinate.
     */
    public int translateMatrixYToImageY(int matrixY, Direction direction) {
        int base = this.offsetStartY + ((this.blockHeight + this.borderThickness) * (matrixY / 2));
        return switch(direction) {
            case North, NorthEast, NorthWest -> base + (matrixY % 2 == 0 ? 0 : this.borderThickness);
            case Center, East, West -> base + (matrixY % 2 == 0 ? 0 : (this.blockHeight / 2) + this.borderThickness);
            case South, SouthEast, SouthWest -> base + (matrixY % 2 == 0 ? 0 : this.blockHeight + this.borderThickness);
        };
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
        return this.translateMatrixYToImageY(matrixY, Direction.Center);
    }

    /**
     * Internal method for finding the maze offsets, AKA. the spacing between
     * the start of the image and the edge of the maze in all four directions.
     */
    private void findOffsets() {
        // TODO: Optimize this method to not require so many loops. At the very
        //       least the loops can be cut down to two. Then there are also
        //       many other optimizations which can be made.

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
     * Internal method for checking if a RGB value corresponds to the color
     * expected for "border pixels" in the maze image.
     *
     * @param rgb Color to check.
     *
     * @return Whether the color is a "border color".
     */
    private boolean isBorderColor(int rgb) {
        // TODO: Refactor this to not require the use of Color class and instead
        //       use raw "int rgb" parameter data.
        // TODO: Make the border color configurable.
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
     */
    private boolean isSubArrayEqual(int[] array, int start, int end) {
        // TODO: Add invalid parameter protection.

        for(int i = start; i < end - 1; i ++) {
            if (array[i] != array[i + 1])
                return false;
        }

        return true;
    }

    /**
     * Internal method for finding the thickness of the borders in the maze
     * image.
     */
    private void findBorderSize() {
        // TODO: Possibly also check for the border thickness in the Y direction
        //       as it may be different. It is not in the maze images used
        //       normally and therefore this is low priority.

        int[] probeResults = new int[MazeReader.PROBE_FACTOR];
        int[] probeYs = IntStream.range(0, MazeReader.PROBE_FACTOR).map(x -> (imageHeight / (MazeReader.PROBE_FACTOR + 1)) * (x + 1)).toArray();

        for(int yi = 0; yi < probeYs.length; yi++) {
            int y = probeYs[yi];
            int counter = 0;
            for(int x = this.offsetStartX; x < this.imageWidth; x++) {
                if(this.isBorderColor(this.backingImage.getRGB(x, y))) {
                    counter++;
                } else if(counter > 0) {
                    //tmpDraw.add(new DrawInfo(this.offsetStartX, y, x - this.offsetStartX, 1, Color.MAGENTA));
                    probeResults[yi] = counter;
                    break;
                }
            }
        }
        Arrays.sort(probeResults);
        if(!this.isSubArrayEqual(probeResults, probeResults.length / 2 - 1, probeResults.length / 2 + 1 + probeResults.length % 2))
            throw new RuntimeException("Unable to find maze image border thickness!");
        this.borderThickness = probeResults[probeResults.length / 2];
    }

    /**
     * Internal method for finding the dimensions of each "block" of the maze.
     */
    private void findBlockSize() {
        // TODO: Remove.
        // Due to personal preference, the block size has been disables in favor
        // of using cell-per-pixel, in order to better display the differences
        // in algorithms.
        if(true) {
            // Override the block size to make all pixels blocks
            this.blockWidth = 1;
            this.blockHeight = 1;

            return;
        }

        // TODO: Calculate block size instead of using hard-coded value.
        //this.blockWidth = 19;
        //this.blockHeight = 19;

        int[] probeResults = new int[MazeReader.PROBE_FACTOR];
        int[] probeYs = IntStream.range(0, MazeReader.PROBE_FACTOR).map(x -> (imageHeight / (MazeReader.PROBE_FACTOR + 1)) * (x + 1)).toArray();
        int[] probeXs = IntStream.range(0, MazeReader.PROBE_FACTOR).map(x -> (imageWidth / (MazeReader.PROBE_FACTOR + 1)) * (x + 1)).toArray();

        // STEP 1: Find block width
        for(int yi = 0; yi < probeYs.length; yi++) {
            int y = probeYs[yi];
            int counter = 0;
            for(int x = this.offsetStartX + this.borderThickness; x < this.imageWidth; x++) {
                if(!this.isBorderColor(this.backingImage.getRGB(x, y))) {
                    counter++;
                } else if(counter > 0) {
                    //tmpDraw.add(new DrawInfo(this.offsetStartX + this.borderThickness, y, x - this.offsetStartX - this.borderThickness, 1, Color.MAGENTA));
                    probeResults[yi] = counter;
                    break;
                }
            }
        }
        Arrays.sort(probeResults);
        if(!this.isSubArrayEqual(probeResults, probeResults.length / 2 - 1, probeResults.length / 2 + 1 + probeResults.length % 2))
            throw new RuntimeException("Unable to find maze image block width!");
        this.blockWidth = probeResults[probeResults.length / 2];

        // STEP 2: Find block height
        for(int xi = 0; xi < probeXs.length; xi++) {
            int x = probeXs[xi];
            int counter = 0;
            for(int y = this.offsetStartY + this.borderThickness; y < this.imageHeight; y++) {
                if(!this.isBorderColor(this.backingImage.getRGB(x, y))) {
                    counter++;
                } else if(counter > 0) {
                    //tmpDraw.add(new DrawInfo(x, this.offsetStartY + this.borderThickness, 1, y - this.offsetStartY - this.borderThickness, Color.MAGENTA));
                    probeResults[xi] = counter;
                    break;
                }
            }
        }
        Arrays.sort(probeResults);
        if(!this.isSubArrayEqual(probeResults, probeResults.length / 2 - 1, probeResults.length / 2 + 1 + probeResults.length % 2))
            throw new RuntimeException("Unable to find maze image block height!");
        this.blockHeight = probeResults[probeResults.length / 2];
    }

    /**
     * Internal method for finding the actual "block" dimensions of the maze
     * image. The results are equivalent to the size of the backing maze matrix.
     */
    private void findMazeDimensions() {
        // TODO: Don't assume start and end offsets are the same, and instead
        //       calculate the end offsets separately and use that instead.
        // NOTE: This makes an unsafe assumption that the offsets are the same
        //       at the start AND end of the maze. The offsetX and offsetY are
        //       only taken based on the 0 -> edge length, which means that the
        //       offset on the other side could be something completely
        //       different. If that is the case, it means that this formula
        //       could return a faulty value. To fix this separate offsets for
        //       the ends must be calculated and used instead.
        this.mazeWidth = ((this.imageWidth - this.offsetStartX - this.offsetEndX) / (this.blockWidth + this.borderThickness)) * 2 + 1;
        this.mazeHeight = ((this.imageHeight - this.offsetStartY - this.offsetEndY) / (this.blockHeight + this.borderThickness)) * 2 + 1;

        //this.mazeSizeX = (26 * 2) + 1;
        //this.mazeSizeY = (34 * 2) + 1;
    }

    /**
     * Internal method for reading the maze image and populating the backing
     * maze matrix with the correct values.
     */
    private void readMaze() {
        this.mazeMatrix = new int[this.mazeWidth][this.mazeHeight];

        for(int y = 0; y < this.mazeHeight; y++) {
            for(int x = 0; x < this.mazeWidth; x++) {
                // TODO: Move the coordinate translation functionality to
                //       another internal helper method.
                //int actualX = this.offsetStartX + (((this.blockWidth + 1) * (x / 2)) + (x % 2 == 0 ? 0 : (this.blockWidth / 2)));
                //int actualY = this.offsetStartY + (((this.blockHeight + 1) * (y / 2)) + (y % 2 == 0 ? 0 : (this.blockHeight / 2)));
                int actualX = this.translateMatrixXToImageX(x, Direction.Center);
                int actualY = this.translateMatrixYToImageY(y, Direction.Center);

                if(!this.isBorderColor(this.backingImage.getRGB(actualX, actualY))) {
                    // TODO: Remove this hack. This sub if-statement is used to ensure that certain edges are detected
                    //       correctly. Due to how the maze is rendered on the image, horizontal edges with an opening
                    //       on the right are one pixel shorter than they should be. This results in the code not being
                    //       able to detect the edge, thereby making the backing maze matrix incorrect. I have no idea
                    //       how to solve this properly, but for now this method works. On each detected "free" space,
                    //       it also checks the square to the left. If it is a wall, then the square is set to a wall,
                    //       which negates the problem, for now.
                    if(!this.isBorderColor(this.backingImage.getRGB(actualX - 1, actualY))) {
                        // 0 represents an open cell.
                        this.mazeMatrix[x][y] = 0;
                    } else {
                        // 1 represents a normal "wall".
                        //this.mazeMatrix[x][y] = 1;
                        this.mazeMatrix[x][y] = 0;
                    }
                } else {
                    // 1 represents a normal "wall".
                    this.mazeMatrix[x][y] = 1;
                }
            }
        }
    }
}
