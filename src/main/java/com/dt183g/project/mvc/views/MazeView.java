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
    private JFrame window;
    private JPanel windowPanel;
    private JPanel menuPanel;
    private JButton selectStartPosition;
    private JButton selectEndPosition;
    private Maze mazePanel;

    public MazeView(BufferedImage mazeImage) {
        this.mazePanel = new Maze(mazeImage);
        initializeWindow();
    }

    private void initializeWindow() {
        initializeMenuPanel();
        initializeWindowPanel();
        window = new JFrame();
        window.add(windowPanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Maze solver");
        window.setVisible(true);
        window.pack();
        window.setLocationRelativeTo(null);
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

        selectStartPosition = new JButton(" Set start ");
        initializeMenuButton(selectStartPosition);
        addSetStartButtonListener(selectStartPosition);

        selectEndPosition = new JButton(" Set end ");
        initializeMenuButton(selectEndPosition);
        addSetEndButtonListener(selectEndPosition);

        menuPanel.add(selectStartPosition);
        menuPanel.add(selectEndPosition);
    }

    private void addSetStartButtonListener(JButton selectStartPosition) {
        selectStartPosition.addActionListener(e -> {
            mazePanel.setSelectCursor();
            mazePanel.setSelectStart(true);
        });
    }

    private void addSetEndButtonListener(JButton selectEndPosition) {
        selectEndPosition.addActionListener(e -> {
            mazePanel.setSelectCursor();
            mazePanel.setSelectEnd(true);
        });
    }

    private void initializeMenuButton(JButton menuButton) {
        menuButton.setBorder(BorderFactory.createBevelBorder(0));
        menuButton.setBackground(Color.LIGHT_GRAY);
        menuButton.setFont(new Font("Monaco", Font.BOLD, (int) (mazePanel.getMazeImageWidth() * 0.03)));
        menuButton.setVisible(true);
    }
}
