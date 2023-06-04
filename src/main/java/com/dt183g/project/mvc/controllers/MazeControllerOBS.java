package com.dt183g.project.mvc.controllers;

import com.dt183g.project.mvc.models.MazeModelOBS;
import com.dt183g.project.mvc.models.Model;
import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.mvc.views.MazeViewOBS;
import com.dt183g.project.mvc.views.View;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class MazeControllerOBS extends Controller {
    private final MazeModelOBS model;
    private final MazeViewOBS view;

    public MazeControllerOBS(BufferedImage mazeImage) {
        System.out.print("[CONTROLLER] Initializing!\n");

        this.model = new MazeModelOBS(mazeImage);
        this.model.attachObserver(this);

        this.view = new MazeViewOBS(this.model.getMazeImage(), Model.Algorithm.values());
        this.view.attachObserver(this);
    }

    /**
     * Helper method for handling view set algorithm event.
     *
     * @param algorithm The new algorithm.
     */
    @Override
    public void handleViewSetAlgorithmEvent(Model.Algorithm algorithm) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SET_ALGORITHM_EVENT);
        this.model.setAlgorithm(algorithm);
    }

    /**
     * Helper method for handling view set select state start event.
     */
    @Override
    public void handleViewSetSelectStateStartEvent() {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SET_SELECT_STATE_START_EVENT);
        this.model.setSelectState(MazeModelOBS.SelectState.SetStart);
    }

    /**
     * Helper method for handling view set select state end event.
     */
    @Override
    public void handleViewSetSelectStateEndEvent() {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SET_SELECT_STATE_END_EVENT);
        this.model.setSelectState(MazeModelOBS.SelectState.SetEnd);
    }

    /**
     * Helper method for handling view select event.
     *
     * @param imageLocation Image location.
     */
    @Override
    public void handleViewSelectEvent(Point imageLocation) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SELECT_EVENT);
        // TODO: Redo this because it is stupid.
        if(this.model.getSelectState() == MazeModelOBS.SelectState.SetStart)
            this.model.setStartLocation(imageLocation);
        else
            this.model.setEndLocation(imageLocation);
    }

    /**
     * Helper method for handling view start solve event.
     */
    @Override
    public void handleViewSolveEvent() {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SOLVE_EVENT);
        this.model.solve();
    }

    /**
     * Helper method for handling view start reset event.
     */
    @Override
    public void handleViewResetEvent() {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_RESET_EVENT);
        this.model.reset();
        this.view.reset();
    }

    /**
     * Helper method for handling model update start location event.
     *
     * @param location Image location to place marker.
     */
    @Override
    public void handleModelUpdateStartLocationEvent(Point location) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", Model.MODEL_UPDATE_START_LOCATION_EVENT);
        this.view.setStartLocation(location);
    }

    /**
     * Helper method for handling model update end location event.
     *
     * @param location Image location to place marker.
     */
    @Override
    public void handleModelUpdateEndLocationEvent(Point location) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", Model.MODEL_UPDATE_END_LOCATION_EVENT);
        this.view.setEndLocation(location);
    }

    /**
     * Helper method for handling model solve complete event.
     *
     * @param path The generated path.
     */
    @Override
    public void handleModelSolveCompleteEvent(List<MazePoint> path) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", Model.MODEL_SOLVE_COMPLETE_EVENT);
        this.view.setPath(path);
    }
}
