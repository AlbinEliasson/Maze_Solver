package com.dt183g.project.mvc.models;

import com.dt183g.project.utility.MazeReader;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

public class MazeModelOBS extends Model {
    private final MazeReader mazeReader;

    private Algorithm currentAlgorithm;
    private SelectState currentSelectState;
    private Point startLocation;
    private Point endLocation;

    public MazeModelOBS(BufferedImage mazeImage) {
        System.out.print("[MODEL] Initializing!\n");

        this.mazeReader = new MazeReader(mazeImage);
        this.currentSelectState = SelectState.SetStart;
        this.currentAlgorithm = Algorithm.values()[0];
    }

    @Override
    public void solve() {
        // TODO: Validate algorithm, start/end locations.

        System.out.printf("[MODEL] Solving maze!\n\tAlgorithm: %s\n\tStart X: %s\n\tStart Y: %s\n\tEnd X: %s\n\tEnd Y: %s\n",
                this.currentAlgorithm, this.startLocation.x, this.startLocation.y, this.endLocation.x, this.endLocation.y);

        List<Point> path = this.currentAlgorithm.solver.solve(
                this.mazeReader.getMazeMatrix(),
                new Point(this.mazeReader.translateImageXToMatrixX(this.startLocation.x), this.mazeReader.translateImageYToMatrixY(this.startLocation.y)), // TODO: Dont do this, make it better.
                new Point(this.mazeReader.translateImageXToMatrixX(this.endLocation.x), this.mazeReader.translateImageYToMatrixY(this.endLocation.y))); // TODO: Dont do this, make it better.

        if(path != null) {
            this.pushSolveCompleteEvent(path.stream().peek(p -> {
                p.x = this.mazeReader.translateMatrixXToImageX(p.x);
                p.y = this.mazeReader.translateMatrixYToImageY(p.y);
            }).collect(Collectors.toList()));
        } else {
            System.out.print("[MODEL] UNABLE TO FIND PATH!\n");
            // TODO: Handle solve failiure
        }
    }

    @Override
    public void reset() {
        System.out.print("[MODEL] RESET METHOD NOT IMPLEMENTED!\n");
        // TODO: Reset any temporary stuff.
    }

    @Override
    public BufferedImage getMazeImage() {
        return mazeReader.getBackingImage();
    }

    @Override
    public Algorithm getAlgorithm() {
        return this.currentAlgorithm;
    }

    @Override
    public void setAlgorithm(Algorithm algorithm) {
        System.out.printf("[MODEL] Setting algorithm! Algorithm: %s\n", algorithm);
        this.currentAlgorithm = algorithm;
    }

    @Override
    public SelectState getSelectState() {
        return this.currentSelectState;
    }

    @Override
    public void setSelectState(SelectState state) {
        System.out.printf("[MODEL] Setting select state! State: %s\n", state);
        this.currentSelectState = state;
    }

    @Override
    public Point getStartLocation() {
        return this.startLocation;
    }

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
        this.pushUpdateStartLocationEvent(new Point(this.mazeReader.normalizeImageX(location.x), this.mazeReader.normalizeImageY(location.y)));
    }

    @Override
    public Point getEndLocation() {
        return this.endLocation;
    }

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
        this.pushUpdateEndLocationEvent(new Point(this.mazeReader.normalizeImageX(location.x), this.mazeReader.normalizeImageY(location.y)));
    }
}
