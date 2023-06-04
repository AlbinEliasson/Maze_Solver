package com.dt183g.project.mvc.controllers;

import com.dt183g.project.mvc.models.Model;
import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.mvc.observer.Observer;
import com.dt183g.project.mvc.views.View;
import com.dt183g.project.mvc.views.gui.Maze;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

abstract public class Controller implements Observer {
    /**
     * Method which is triggered by observed objects when an event occurs.
     *
     * @param eventName The name of the event.
     * @param data      Optional parameter data.
     */
    @Override
    public void handleEvent(String eventName, Object... data) {
        switch(eventName) {
            case View.VIEW_SET_ALGORITHM_EVENT -> handleViewSetAlgorithmEvent((Model.Algorithm) data[0]); // TODO: Maybe validate type?
            case View.VIEW_SET_SELECT_STATE_START_EVENT -> handleViewSetSelectStateStartEvent();
            case View.VIEW_SET_SELECT_STATE_END_EVENT -> handleViewSetSelectStateEndEvent();
            case View.VIEW_SELECT_EVENT -> handleViewSelectEvent((Point) data[0]);// TODO: Maybe validate type?
            case View.VIEW_SOLVE_EVENT -> handleViewSolveEvent();
            case View.VIEW_RESET_EVENT -> handleViewResetEvent();
            case Model.MODEL_UPDATE_START_LOCATION_EVENT -> handleModelUpdateStartLocationEvent((Point) data[0]);
            case Model.MODEL_UPDATE_END_LOCATION_EVENT -> handleModelUpdateEndLocationEvent((Point) data[0]);
            case Model.MODEL_SOLVE_COMPLETE_EVENT -> handleModelSolveCompleteEvent((List<MazePoint>) data[0]);
            default -> System.out.printf("[CONTROLLER] Unhandled event \"%s\": %s\n", eventName, Arrays.toString(data));
        }
    }

    /**
     * Helper method for handling view set algorithm event.
     *
     * @param algorithm The new algorithm.
     */
    abstract public void handleViewSetAlgorithmEvent(Model.Algorithm algorithm);

    /**
     * Helper method for handling view set select state start event.
     */
    abstract public void handleViewSetSelectStateStartEvent();

    /**
     * Helper method for handling view set select state end event.
     */
    abstract public void handleViewSetSelectStateEndEvent();

    /**
     * Helper method for handling view select event.
     *
     * @param imageLocation Image location.
     */
    abstract public void handleViewSelectEvent(Point imageLocation);

    /**
     * Helper method for handling view start solve event.
     */
    abstract public void handleViewSolveEvent();

    /**
     * Helper method for handling view start reset event.
     */
    abstract public void handleViewResetEvent();

    /**
     * Helper method for handling model update start location event.
     *
     * @param location Image location to place marker.
     */
    abstract public void handleModelUpdateStartLocationEvent(Point location);

    /**
     * Helper method for handling model update end location event.
     *
     * @param location Image location to place marker.
     */
    abstract public void handleModelUpdateEndLocationEvent(Point location);

    /**
     * Helper method for handling model solve complete event.
     *
     * @param path The generated path.
     */
    abstract public void handleModelSolveCompleteEvent(List<MazePoint> path);
}
