package com.dt183g.project;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MazeWindow {
    private BufferedImage mazeImage;
    private JFrame window;
    private JLabel imageLabel;
    private JPanel imagePanel;

    public MazeWindow(BufferedImage maze) {
        this.mazeImage = maze;
        initializeWindow();
        addMazePosition(50, 50);
    }

    private void initializeWindow() {
        initializeImagePanel();
        window = new JFrame();
        window.add(imagePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
        window.pack();
        window.setLocationRelativeTo(null);
    }

    private void initializeImagePanel() {
        imageLabel = new JLabel("", new ImageIcon(mazeImage), SwingConstants.CENTER);
        imagePanel = new JPanel();
        imagePanel.add(imageLabel);
    }

    private void addMazePosition(int x, int y) {
        Graphics g2d = mazeImage.getGraphics();
        g2d.setColor(Color.red);
        g2d.fillRect(x, y, 5, 5);
        g2d.dispose();

        imageLabel.repaint();
    }
}
