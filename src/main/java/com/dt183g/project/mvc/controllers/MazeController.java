package com.dt183g.project.mvc.controllers;

import com.dt183g.project.mvc.models.MazeModel;
import com.dt183g.project.mvc.models.Model;
import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.mvc.views.MazeView;
import com.dt183g.project.mvc.views.View;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Class implementing the main controller for the application.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
public class MazeController extends Controller {
    private final MazeModel model;
    private final MazeView view;

    public MazeController(BufferedImage mazeImage) {
        System.out.print("[CONTROLLER] Initializing!\n");

        this.model = new MazeModel(mazeImage);
        this.model.attachObserver(this);

        this.view = new MazeView(this.model.getMazeImage(), Model.Algorithm.values());
        this.view.attachObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleViewSetAlgorithmEvent(Model.Algorithm algorithm) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SET_ALGORITHM_EVENT);
        this.model.setAlgorithm(algorithm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleViewSetSelectStateStartEvent() {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SET_SELECT_STATE_START_EVENT);
        this.model.setSelectState(MazeModel.SelectState.SetStart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleViewSetSelectStateEndEvent() {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SET_SELECT_STATE_END_EVENT);
        this.model.setSelectState(MazeModel.SelectState.SetEnd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleViewSelectEvent(Point imageLocation) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SELECT_EVENT);

        if(this.model.getSelectState() == MazeModel.SelectState.SetStart)
            this.model.setStartLocation(imageLocation);
        else
            this.model.setEndLocation(imageLocation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleViewSolveEvent() {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_SOLVE_EVENT);
        this.model.solve();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleViewResetEvent() {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", View.VIEW_RESET_EVENT);
        this.model.reset();
        this.view.reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleModelUpdateStartLocationEvent(Point location) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", Model.MODEL_UPDATE_START_LOCATION_EVENT);
        this.view.setStartLocation(location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleModelUpdateEndLocationEvent(Point location) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", Model.MODEL_UPDATE_END_LOCATION_EVENT);
        this.view.setEndLocation(location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleModelSolveCompleteEvent(List<MazePoint> path) {
        System.out.printf("[CONTROLLER] %s handler triggered!\n", Model.MODEL_SOLVE_COMPLETE_EVENT);
        this.view.setPath(path);
    }
}
