package com.dt183g.project;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MazeWindow extends JPanel {
    private final BufferedImage mazeImage;
    private JFrame window;
    private JPanel windowPanel;
    private JPanel menuPanel;
    private JButton selectStartPosition;
    private JButton selectEndPosition;
    private Vertex startPosition;
    private Vertex endPosition;
    private boolean selectStart = false;
    private boolean selectEnd = false;
    private ArrayList<Vertex> mazePath = new ArrayList<>();

    public MazeWindow(BufferedImage maze) {
        this.mazeImage = maze;
        initializeWindow();

        //addCurrentMazePosition(20, 390);
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

    private void initializeWindowPanel() {
        windowPanel = new JPanel();
        windowPanel.setLayout(new BorderLayout());
        //windowPanel.setPreferredSize(new Dimension(mazeImage.getWidth(), (int) (mazeImage.getHeight() * 1.1)));
        windowPanel.add(this, BorderLayout.SOUTH);
        windowPanel.add(menuPanel, BorderLayout.NORTH);
    }

    private void initializeImagePanel() {
        this.setPreferredSize(new Dimension(mazeImage.getWidth(), mazeImage.getHeight()));
        addImageClickListener();
    }

    private void initializeMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(new Color(164, 164, 164));
        menuPanel.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.WHITE));

        selectStartPosition = new JButton("Start position");
        initializeMenuButton(selectStartPosition);

        selectStartPosition.addActionListener(e -> {
            setSelectCursor();
            selectStart = !selectStart;
            selectEnd = false;
        });

        selectEndPosition = new JButton("End position");
        initializeMenuButton(selectEndPosition);

        selectEndPosition.addActionListener(e -> {
            setSelectCursor();
            selectEnd = !selectEnd;
            selectStart = false;
        });

        menuPanel.add(selectStartPosition);
        menuPanel.add(selectEndPosition);
    }

    private void initializeMenuButton(JButton menuButton) {
        menuButton.setBorder(BorderFactory.createBevelBorder(0));
        menuButton.setBackground(Color.LIGHT_GRAY);
        menuButton.setFont(new Font("Monaco", Font.BOLD, (int) (mazeImage.getWidth() * 0.025)));
        menuButton.setVisible(true);
    }

    public void addCurrentMazePosition(Vertex vertex) {
        mazePath.add(vertex);
    }

    private void addImageClickListener() {
        this.addMouseListener(new MouseAdapter() {
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
            resetCursor();
            selectStart = false;
            this.repaint();

        } else if (selectEnd) {
            if (endPosition != null) {
                endPosition.setXCoordinate(x);
                endPosition.setYCoordinate(y);

            } else {
                endPosition = new Vertex(x, y);
            }
            resetCursor();
            selectEnd = false;
            this.repaint();
        }
    }

    private void setSelectCursor() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void resetCursor() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawImage(mazeImage, 0, 0, null);

        if (startPosition != null) {
            graphics.setColor(Color.BLUE);
            graphics.fillOval(startPosition.getXCoordinate(), startPosition.getYCoordinate(), 10, 10);
        }
        if (endPosition != null) {
            graphics.setColor(Color.GREEN);
            graphics.fillOval(endPosition.getXCoordinate(), endPosition.getYCoordinate(), 10, 10);
        }
        if (mazePath != null) {
            graphics.setColor(Color.RED);
            mazePath.forEach(vertex -> graphics.fillRect(vertex.getXCoordinate(), vertex.getYCoordinate(), 5, 5));
        }
    }

    public Vertex getStartPosition() {
        return startPosition;
    }

    public Vertex getEndPosition() {
        return endPosition;
    }
}
