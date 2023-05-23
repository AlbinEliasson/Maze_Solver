package com.dt183g.project.mvc.views;

import com.dt183g.project.mvc.controllers.MazeController;
import com.dt183g.project.mvc.views.gui.Maze;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MazeView {
    private JFrame windowFrame;
    private JPanel windowPanel;
    private JPanel menuPanel;
    private JButton setStartButton;
    private JButton setEndButton;
    private JButton resetButton;
    private JButton solveButton;
    private final Maze mazePanel;
    private final MazeController mazeController;

    public MazeView(BufferedImage mazeImage, MazeController controller) {
        this.mazePanel = new Maze(mazeImage);
        this.mazeController = controller;
        addMazeClickListener();
        initializeWindow();
    }

    private void initializeWindow() {
        initializeMenuPanel();
        initializeWindowPanel();
        windowFrame = new JFrame();
        windowFrame.add(windowPanel);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        windowFrame.setTitle("Maze solver");
        windowFrame.setVisible(true);
        windowFrame.pack();
        windowFrame.setLocationRelativeTo(null);
    }

    private void initializeWindowPanel() {
        windowPanel = new JPanel();
        windowPanel.setLayout(new BorderLayout());
        windowPanel.setPreferredSize(new Dimension(mazePanel.getMazeImageWidth(), (int) (mazePanel.getMazeImageHeight() * 1.1)));
        windowPanel.add(mazePanel, BorderLayout.CENTER);
        windowPanel.add(menuPanel, BorderLayout.NORTH);
    }

    private void initializeMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(new Color(164, 164, 164));
        menuPanel.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.WHITE));
        menuPanel.setPreferredSize(new Dimension(mazePanel.getMazeImageWidth(), (int) (mazePanel.getMazeImageHeight() * 0.1)));

        setStartButton = new JButton(" Set start ");
        initializeMenuButton(setStartButton);
        addSetStartButtonListener(setStartButton);

        setEndButton = new JButton(" Set end ");
        initializeMenuButton(setEndButton);
        addSetEndButtonListener(setEndButton);

        resetButton = new JButton(" Reset ");
        initializeMenuButton(resetButton);
        addResetButtonListener(resetButton);

        solveButton = new JButton(" Solve ");
        disableSolveButton();
        initializeMenuButton(solveButton);
        addSolveButtonListener(solveButton);

        menuPanel.add(setStartButton);
        menuPanel.add(setEndButton);
        menuPanel.add(resetButton);
        menuPanel.add(solveButton);
    }

    private void addSetStartButtonListener(JButton setStartButton) {
        setStartButton.addActionListener(e -> {
            mazePanel.setSelectCursor();
            mazePanel.setSelectStart(true);
            mazePanel.setSelectEnd(false);
        });
    }

    private void addSetEndButtonListener(JButton setEndButton) {
        setEndButton.addActionListener(e -> {
            mazePanel.setSelectCursor();
            mazePanel.setSelectEnd(true);
            mazePanel.setSelectStart(false);
        });
    }

    private void addResetButtonListener(JButton resetButton) {
        resetButton.addActionListener(e -> {
            mazeController.resetMaze();
        });
    }

    private void addSolveButtonListener(JButton solveButton) {
        solveButton.addActionListener(e -> {
            mazeController.solveMaze(mazePanel.getStartPosition(), mazePanel.getEndPosition());
        });
    }

    private void addMazeClickListener() {
        mazePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent mouseEvent) {
                mazePanel.setClickedPosition(mouseEvent.getX(), mouseEvent.getY());

                if (mazePanel.isPositionsSet()) {
                    enableSolveButton();
                }
            }
        });
    }

    public void enableSolveButton() {
        solveButton.setEnabled(true);
    }

    public void disableSolveButton() {
        solveButton.setEnabled(false);
    }

    private void initializeMenuButton(JButton menuButton) {
        menuButton.setBorder(BorderFactory.createBevelBorder(0));
        menuButton.setBackground(Color.LIGHT_GRAY);
        menuButton.setFont(new Font("Monaco", Font.BOLD, (int) (mazePanel.getMazeImageWidth() * 0.03)));
        menuButton.setVisible(true);
    }

    public void displayMazePath(Point position) {
        mazePanel.setMazePath(position);
        mazePanel.repaint();
    }

    public void resetImage() {
        mazePanel.resetImage();
        disableSolveButton();
    }
}
