package com.dt183g.project.mvc.views;

import com.dt183g.project.mvc.models.Model;
import com.dt183g.project.mvc.models.types.MazePoint;
import com.dt183g.project.mvc.views.gui.MazeMenuPanel;
import com.dt183g.project.mvc.views.gui.MazePanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * Class implementing the main view for the application.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
public class MazeView extends View {
    private final JFrame windowFrame;
    private final JPanel windowPanel;
    private final MazeMenuPanel menuPanel;
    private final MazePanel mazePanel;

    public MazeView(BufferedImage mazeImage, Model.Algorithm[] algorithms) {
        System.out.print("[VIEW] Initializing!\n");

        // INITIALIZE WINDOW FRAME
        this.windowFrame = new JFrame();
        this.windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.windowFrame.setResizable(false);
        this.windowFrame.setTitle("Maze solver");
        this.windowFrame.setLocationRelativeTo(null);

        // INITIALIZE WINDOW PANEL
        this.windowPanel = new JPanel();
        windowPanel.setLayout(new BorderLayout());
        this.windowFrame.add(windowPanel);

        // INITIALIZE MENU PANEL
        this.menuPanel = new MazeMenuPanel(algorithms);
        this.menuPanel.getAlgorithmMenu().addActionListener(event -> {
            System.out.printf("[VIEW] Algorithm menu change event! Command: %s\n", event.getActionCommand());
            this.pushSetAlgorithmEvent(this.menuPanel.getSelectedAlgorithm());
        });
        this.menuPanel.getSetStartButton().addActionListener(event -> {
            System.out.printf("[VIEW] Set start button click event! Command: %s\n", event.getActionCommand());
            this.pushSetSelectStateStartEvent();
        });
        this.menuPanel.getSetEndButton().addActionListener(event -> {
            System.out.printf("[VIEW] Set end button click event! Command: %s\n", event.getActionCommand());
            this.pushSetSelectStateEndEvent();
        });
        this.menuPanel.getSolveButton().addActionListener(event -> {
            System.out.printf("[VIEW] Solve button click event! Command: %s\n", event.getActionCommand());
            this.pushSolveEvent();
        });
        this.menuPanel.getResetButton().addActionListener(event -> {
            System.out.printf("[VIEW] Reset button click event! Command: %s\n", event.getActionCommand());
            this.pushResetEvent();
        });
        this.windowPanel.add(this.menuPanel, BorderLayout.PAGE_START);

        // INITIALIZE MAZE PANEL
        this.mazePanel = new MazePanel(mazeImage);
        this.mazePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                System.out.printf("[VIEW] Maze click event! X: %s Y: %s\n", event.getX(), event.getY());
                pushSelectEvent(new Point(event.getX(), event.getY()));
                // TODO: Handle solve button enable/disable
            }
        });
        this.windowPanel.add(this.mazePanel, BorderLayout.CENTER);

        // SHOW WINDOW
        this.windowFrame.setVisible(true);
        this.windowFrame.pack();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartLocation(Point location) {
        System.out.printf("[VIEW] Setting start location! X: %s Y: %s\n", location.x, location.y);
        this.mazePanel.setStartLocation(location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEndLocation(Point location) {
        System.out.printf("[VIEW] Setting end location! X: %s Y: %s\n", location.x, location.y);
        this.mazePanel.setEndLocation(location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPath(List<MazePoint> path) {
        System.out.printf("[VIEW] Setting path! Path: %s\n", Arrays.toString(path.toArray()));
        this.mazePanel.setPath(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        System.out.print("[VIEW] Resetting!\n");
        this.mazePanel.reset();
    }
}
