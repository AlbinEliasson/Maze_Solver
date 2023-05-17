package com.dt183g.project;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MazeWindow {
    private BufferedImage mazeImage;
    private JFrame window;
    private JLabel imageLabel;
    private JPanel imagePanel;
    private JPanel windowPanel;
    private JPanel menuPanel;
    private JButton selectStartPosition;
    private JButton selectEndPosition;
    private Vertex startPosition;
    private Vertex endPosition;
    private boolean selectStart = false;
    private boolean selectEnd = false;

    public MazeWindow(BufferedImage maze) {
        this.mazeImage = maze;
        initializeWindow();

        addCurrentMazePosition(20, 390);
    }

    private void initializeWindow() {
        initializeImagePanel();
        initializeMenuPanel();
        initializeWindowPanel();
        window = new JFrame();
        window.add(windowPanel);
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
        addImageClickListener();
    }

    private void initializeWindowPanel() {
        windowPanel = new JPanel();
        windowPanel.setLayout(new BorderLayout());
        windowPanel.setPreferredSize(new Dimension(mazeImage.getWidth(), (int) (mazeImage.getHeight() * 1.1)));
        windowPanel.add(imagePanel, BorderLayout.SOUTH);
        windowPanel.add(menuPanel, BorderLayout.NORTH);
    }

    private void initializeMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(Color.LIGHT_GRAY);
        menuPanel.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.WHITE));

        selectStartPosition = new JButton("Set start position");
        selectStartPosition.setBorder(BorderFactory.createBevelBorder(0));

        selectStartPosition.addActionListener(e -> {
            selectStart = !selectStart;
            selectEnd = false;
        });
        selectStartPosition.setVisible(true);

        selectEndPosition = new JButton("Set end Position");
        selectEndPosition.setBorder(BorderFactory.createBevelBorder(0));
        selectEndPosition.addActionListener(e -> {
            selectEnd = !selectEnd;
            selectStart = false;
        });
        selectEndPosition.setVisible(true);

        menuPanel.add(selectStartPosition);
        menuPanel.add(selectEndPosition);
    }

    public void addCurrentMazePosition(int x, int y) {
        Graphics g2d = mazeImage.getGraphics();
        g2d.setColor(Color.red);
        g2d.fillRect(x, y, 5, 5);
        g2d.dispose();

        imageLabel.repaint();
    }

    private void addImageClickListener() {
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent mouseEvent) {
                setClickedPosition(mouseEvent.getX(), mouseEvent.getY());
            }
        });
    }

    public void setClickedPosition(int x, int y) {
        if (selectStart) {
            if (startPosition != null) {
                startPosition.setXCoordinate(x);
                startPosition.setYCoordinate(y);

            } else {
                startPosition = new Vertex(x, y);
            }

            Graphics g2d = mazeImage.getGraphics();
            g2d.setColor(Color.BLUE);
            g2d.fillOval(x, y, 10, 10);
            g2d.dispose();
            imageLabel.repaint();
            selectStart = false;

        } else if (selectEnd) {
            if (endPosition != null) {
                endPosition.setXCoordinate(x);
                endPosition.setYCoordinate(y);

            } else {
                endPosition = new Vertex(x, y);
            }

            Graphics g2d = mazeImage.getGraphics();
            g2d.setColor(Color.GREEN);
            g2d.fillOval(x, y, 10, 10);
            g2d.dispose();
            imageLabel.repaint();
            selectEnd = false;
        }
    }

    public Vertex getStartPosition() {
        return startPosition;
    }

    public Vertex getEndPosition() {
        return endPosition;
    }
}
