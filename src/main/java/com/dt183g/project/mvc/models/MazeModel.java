package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.utility.MazeReader;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Class implementing the main model for the application.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
public class MazeModel extends Model {
    private final MazeReader mazeReader;

    private Algorithm currentAlgorithm;
    private SelectState currentSelectState;
    private Point startLocation;
    private Point endLocation;

    public MazeModel(BufferedImage mazeImage) {
        System.out.print("[MODEL] Initializing!\n");

        this.mazeReader = new MazeReader(mazeImage);
        this.currentSelectState = SelectState.SetStart;
        this.currentAlgorithm = Algorithm.values()[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void solve() {
        System.out.printf("[MODEL] Solving maze!\n\tAlgorithm: %s\n\tStart X: %s\n\tStart Y: %s\n\tEnd X: %s\n\tEnd Y: %s\n",
                this.currentAlgorithm, this.startLocation.x, this.startLocation.y, this.endLocation.x, this.endLocation.y);

        List<MazePoint> path = this.currentAlgorithm.solver.solve(
                this.mazeReader.getMazeMatrix(),
                MazePoint.fromImage(startLocation.x, startLocation.y, mazeReader),
                MazePoint.fromImage(endLocation.x, endLocation.y, mazeReader));

        if(path != null) {
            this.pushSolveCompleteEvent(path);
        } else {
            System.out.print("[MODEL] UNABLE TO FIND PATH!\n");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        System.out.print("[MODEL] Resetting state!\n");

        this.startLocation = null;
        this.endLocation = null;
        this.currentSelectState = SelectState.SetStart;
        this.currentAlgorithm = Algorithm.values()[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getMazeImage() {
        return mazeReader.getBackingImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Algorithm getAlgorithm() {
        return this.currentAlgorithm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlgorithm(Algorithm algorithm) {
        System.out.printf("[MODEL] Setting algorithm! Algorithm: %s\n", algorithm);
        this.currentAlgorithm = algorithm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectState getSelectState() {
        return this.currentSelectState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectState(SelectState state) {
        System.out.printf("[MODEL] Setting select state! State: %s\n", state);
        this.currentSelectState = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point getStartLocation() {
        return this.startLocation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartLocation(Point location) {
        System.out.printf("[MODEL] Setting start location! X: %s Y: %s\n", location.x, location.y);

        if(
                !this.mazeReader.isValidImageX(location.x) ||
                !this.mazeReader.isValidImageY(location.y) ||
                this.mazeReader.getCell(this.mazeReader.translateImageXToMatrixX(location.x), this.mazeReader.translateImageYToMatrixY(location.y)) != 0
        ) {
            System.out.printf("[MODEL] Invalid start location provided, skipping! X: %s Y: %s\n", location.x, location.y);
            return;
        }

        this.startLocation = location;
        this.pushUpdateStartLocationEvent(new Point(location.x, location.y));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point getEndLocation() {
        return this.endLocation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEndLocation(Point location) {
        System.out.printf("[MODEL] Setting end location! X: %s Y: %s\n", location.x, location.y);

        if(
                !this.mazeReader.isValidImageX(location.x) ||
                !this.mazeReader.isValidImageY(location.y) ||
                this.mazeReader.getCell(this.mazeReader.translateImageXToMatrixX(location.x), this.mazeReader.translateImageYToMatrixY(location.y)) != 0
        ) {
            System.out.printf("[MODEL] Invalid end location provided, skipping! X: %s Y: %s\n", location.x, location.y);
            return;
        }

        this.endLocation = location;

        this.pushUpdateEndLocationEvent(new Point(location.x, location.y));
    }
}
