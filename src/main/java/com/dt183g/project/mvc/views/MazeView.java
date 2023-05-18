package com.dt183g.project.mvc.views;

import com.dt183g.project.mvc.views.gui.Maze;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class MazeView extends JPanel {
    private JFrame windowFrame;
    private JPanel windowPanel;
    private JPanel menuPanel;
    private JButton setStartButton;
    private JButton setEndButton;
    private JButton resetButton;
    private Maze mazePanel;

    public MazeView(BufferedImage mazeImage) {
        this.mazePanel = new Maze(mazeImage);
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

        menuPanel.add(setStartButton);
        menuPanel.add(setEndButton);
        menuPanel.add(resetButton);
    }

    private void addSetStartButtonListener(JButton setStartButton) {
        setStartButton.addActionListener(e -> {
            mazePanel.setSelectCursor();
            mazePanel.setSelectStart(true);
        });
    }

    private void addSetEndButtonListener(JButton setEndButton) {
        setEndButton.addActionListener(e -> {
            mazePanel.setSelectCursor();
            mazePanel.setSelectEnd(true);
        });
    }

    private void addResetButtonListener(JButton resetButton) {
        resetButton.addActionListener(e -> {
            mazePanel.resetImage();
        });
    }

    private void initializeMenuButton(JButton menuButton) {
        menuButton.setBorder(BorderFactory.createBevelBorder(0));
        menuButton.setBackground(Color.LIGHT_GRAY);
        menuButton.setFont(new Font("Monaco", Font.BOLD, (int) (mazePanel.getMazeImageWidth() * 0.03)));
        menuButton.setVisible(true);
    }
}
