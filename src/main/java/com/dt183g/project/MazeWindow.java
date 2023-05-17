package com.dt183g.project;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.image.BufferedImage;

public class MazeWindow {
    private JFrame window;
    private JLabel imageLabel;
    private JPanel imagePanel;

    public MazeWindow(BufferedImage maze) {
        initializeWindow(maze);
    }

    private void initializeWindow(BufferedImage maze) {
        initializeImagePanel(maze);
        window = new JFrame();
        window.add(imagePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
        window.pack();
        window.setLocationRelativeTo(null);
    }

    private void initializeImagePanel(BufferedImage maze) {
        imageLabel = new JLabel("", new ImageIcon(maze), SwingConstants.CENTER);
        imagePanel = new JPanel();
        imagePanel.add(imageLabel);
    }
}
