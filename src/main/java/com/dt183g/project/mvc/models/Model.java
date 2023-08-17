package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.models.algorithms.AStar;
import com.dt183g.project.mvc.models.algorithms.DijkstrasMinHeap;
import com.dt183g.project.mvc.models.algorithms.DijkstrasQueue;
import com.dt183g.project.mvc.models.algorithms.MazeAlgorithm;
import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.mvc.observer.Observed;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Abstract class used as the top-level super class of all models.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
abstract public class Model extends Observed {
    public static final String MODEL_UPDATE_START_LOCATION_EVENT = "MODEL_UPDATE_START_LOCATION_EVENT";
    public static final String MODEL_UPDATE_END_LOCATION_EVENT = "MODEL_UPDATE_END_LOCATION_EVENT";
    public static final String MODEL_SOLVE_COMPLETE_EVENT = "MODEL_SOLVE_COMPLETE_EVENT";

    /**
     * A type describing which maze-solving algorithm to use.
     */
    public enum Algorithm {
        DijkstrasQueue("Dijkstra's Algorithm: Queue", new DijkstrasQueue()),
        DijkstrasMinHeap("Dijkstra's Algorithm: Min Heap", new DijkstrasMinHeap()),
        AStar("A* Algorithm", new AStar());

        public final String label;
        public final MazeAlgorithm solver;

        Algorithm(String label, MazeAlgorithm solver) {
            this.label = label;
            this.solver = solver;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }

    /**
     * A type describing the "select state" which is the wanted action when
     * selecting a spot in the maze.
     */
    public enum SelectState {
        SetStart,
        SetEnd
    }

    /**
     * Helper method for propagating a "start location changed" event.
     *
     * @param location The location to set the start at.
     */
    public void pushUpdateStartLocationEvent(Point location) {
        this.pushEvent(MODEL_UPDATE_START_LOCATION_EVENT, location);
    }

    /**
     * Helper method for propagating an "end location changed" event.
     *
     * @param location The location the set the end at.
     */
    public void pushUpdateEndLocationEvent(Point location) {
        this.pushEvent(MODEL_UPDATE_END_LOCATION_EVENT, location);
    }

    /**
     * Helper method for propagating a "solve complete" event.
     *
     * @param path The found path from the start to the end location.
     */
    public void pushSolveCompleteEvent(List<MazePoint> path) {
        this.pushEvent(MODEL_SOLVE_COMPLETE_EVENT, path);
    }

    /**
     * Method for triggering the solving process.
     */
    abstract public void solve();

    /**
     * Method for resetting the maze.
     */
    abstract public void reset();

    /**
     * Method for getting the current maze image used.
     *
     * @return The current maze image.
     */
    abstract public BufferedImage getMazeImage();

    /**
     * Method for getting the currently selected solve algorithm.
     *
     * @return The current algorithm.
     */
    abstract public Algorithm getAlgorithm();

    /**
     * Method for setting the solve algorithm to be used.
     *
     * @param algorithm The algorithm to use.
     */
    abstract public void setAlgorithm(Algorithm algorithm);

    /**
     * Method for getting the select state.
     *
     * @return The select state.
     */
    abstract public SelectState getSelectState();

    /**
     * Method for setting the select state.
     *
     * @param state The select state.
     */
    abstract public void setSelectState(SelectState state);

    /**
     * Method for getting the currently set start location.
     *
     * @return The current start location.
     */
    abstract public Point getStartLocation();

    /**
     * Method for setting the start location.
     *
     * @param location The location to set.
     */
    abstract public void setStartLocation(Point location);

    /**
     * Method for getting the end location.
     *
     * @return The current end location.
     */
    abstract public Point getEndLocation();

    /**
     * Method for setting the end location.
     *
     * @param location The location to set.
     */
    abstract public void setEndLocation(Point location);
}
