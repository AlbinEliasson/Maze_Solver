package maze_solver.mvc.views;

import maze_solver.mvc.models.Model;
import maze_solver.mvc.models.types.MazePoint;
import maze_solver.mvc.observer.Observed;

import java.awt.Point;
import java.util.List;

/**
 * Abstract class used as the top-level super class of all views.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
abstract public class View extends Observed {
    public static final String VIEW_SET_ALGORITHM_EVENT = "VIEW_SET_ALGORITHM_EVENT";
    public static final String VIEW_SET_SELECT_STATE_START_EVENT = "VIEW_SET_SELECT_STATE_START_EVENT";
    public static final String VIEW_SET_SELECT_STATE_END_EVENT = "VIEW_SET_SELECT_STATE_END_EVENT";
    public static final String VIEW_SELECT_EVENT = "VIEW_SELECT_EVENT";
    public static final String VIEW_SOLVE_EVENT = "VIEW_SOLVE_EVENT";
    public static final String VIEW_RESET_EVENT = "VIEW_RESET_EVENT";

    /**
     * Helper method for propagating a change to the selected algorithm.
     *
     * @param algorithm New algorithm.
     */
    public void pushSetAlgorithmEvent(Model.Algorithm algorithm) {
        this.pushEvent(VIEW_SET_ALGORITHM_EVENT, algorithm);
    }

    /**
     * Helper method for propagating a "select state" change, indicating that
     * any subsequent selects define the "starting point".
     */
    public void pushSetSelectStateStartEvent() {
        this.pushEvent(VIEW_SET_SELECT_STATE_START_EVENT);
    }

    /**
     * Helper method for propagating a "select state" change, indicating that
     * any subsequent selects define the "ending point".
     */
    public void pushSetSelectStateEndEvent() {
        this.pushEvent(VIEW_SET_SELECT_STATE_END_EVENT);
    }

    /**
     * Helper method for propagating a change to the current point type.
     *
     * @param imageLocation New location.
     */
    public void pushSelectEvent(Point imageLocation) {
        this.pushEvent(VIEW_SELECT_EVENT, imageLocation);
    }

    /**
     * Helper method for triggering a solve event.
     */
    public void pushSolveEvent() {
        this.pushEvent(VIEW_SOLVE_EVENT);
    }

    /**
     * Helper method for triggering a reset event.
     */
    public void pushResetEvent() {
        this.pushEvent(VIEW_RESET_EVENT);
    }

    /**
     * Method for setting the start location displayed upon the maze image.
     *
     * @param location The location on the image.
     */
    abstract public void setStartLocation(Point location);

    /**
     * Method for setting the end location displayed upon the maze image.
     *
     * @param location The location on the image.
     */
    abstract public void setEndLocation(Point location);

    /**
     * Method for setting the path displayed upon the maze image.
     *
     * @param path List of points in the path.
     */
    abstract public void setPath(List<MazePoint> path);

    /**
     * Method for resetting the maze image, nullifying the starting and ending
     * points, as well as the path.
     */
    abstract public void reset();
}
