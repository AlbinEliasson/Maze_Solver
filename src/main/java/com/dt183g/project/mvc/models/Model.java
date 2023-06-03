package com.dt183g.project.mvc.models;

import com.dt183g.project.mvc.models.algorithms.AStar;
import com.dt183g.project.mvc.models.algorithms.DijkstrasMinHeap;
import com.dt183g.project.mvc.models.algorithms.DijkstrasQueue;
import com.dt183g.project.mvc.models.algorithms.MazeAlgorithm;
import com.dt183g.project.mvc.observer.Observed;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

abstract public class Model extends Observed {
    public static final String MODEL_UPDATE_START_LOCATION_EVENT = "MODEL_UPDATE_START_LOCATION_EVENT";
    public static final String MODEL_UPDATE_END_LOCATION_EVENT = "MODEL_UPDATE_END_LOCATION_EVENT";
    public static final String MODEL_SOLVE_COMPLETE_EVENT = "MODEL_SOLVE_COMPLETE_EVENT";

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

    public enum SelectState {
        SetStart,
        SetEnd
    }

    public void pushUpdateStartLocationEvent(Point location) {
        this.pushEvent(MODEL_UPDATE_START_LOCATION_EVENT, location);
    }

    public void pushUpdateEndLocationEvent(Point location) {
        this.pushEvent(MODEL_UPDATE_END_LOCATION_EVENT, location);
    }

    public void pushSolveCompleteEvent(List<Point> path) {
        this.pushEvent(MODEL_SOLVE_COMPLETE_EVENT, path);
    }

    abstract public void solve();
    abstract public void reset();
    abstract public BufferedImage getMazeImage();
    abstract public Algorithm getAlgorithm();
    abstract public void setAlgorithm(Algorithm algorithm);
    abstract public SelectState getSelectState();
    abstract public void setSelectState(SelectState state);
    abstract public Point getStartLocation();
    abstract public void setStartLocation(Point location);
    abstract public Point getEndLocation();
    abstract public void setEndLocation(Point location);
}
