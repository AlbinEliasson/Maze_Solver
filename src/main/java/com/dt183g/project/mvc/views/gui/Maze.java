package com.dt183g.project.mvc.views.gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Maze extends JPanel {
    private final BufferedImage mazeImage;
    private Point startPosition;
    private Point endPosition;
    private boolean selectStart = false;
    private boolean selectEnd = false;
    private ArrayList<Point> mazePath = new ArrayList<>();

    public Maze(BufferedImage maze) {
        this.mazeImage = maze;

        this.setPreferredSize(new Dimension(mazeImage.getWidth(), mazeImage.getHeight()));
        this.setBackground(new Color(164, 164, 164));
    }

    public void setClickedPosition(int x, int y) {
        if (isSelectStart()) {
            if (startPosition != null) {
                startPosition.x = x;
                startPosition.y = y;

            } else {
                startPosition = new Point(x, y);
            }
            resetCursor();
            setSelectStart(false);
            this.repaint();

        } else if (isSelectEnd()) {
            if (endPosition != null) {
                endPosition.x = x;
                endPosition.y = y;

            } else {
                endPosition = new Point(x, y);
            }
            resetCursor();
            setSelectEnd(false);
            this.repaint();
        }
    }

    public void setMazePath(Point location) {
        mazePath.add(location);
    }

    public void setSelectCursor() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void resetCursor() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void resetImage() {
        startPosition = null;
        endPosition = null;
        mazePath.clear();
        this.repaint();
    }

    public boolean isPositionsSet() {
        return startPosition != null && endPosition != null;
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public Point getEndPosition() {
        return endPosition;
    }

    public boolean isSelectStart() {
        return selectStart;
    }

    public void setSelectStart(boolean selectStart) {
        this.selectStart = selectStart;
    }

    public boolean isSelectEnd() {
        return selectEnd;
    }

    public void setSelectEnd(boolean selectEnd) {
        this.selectEnd = selectEnd;
    }

    public int getMazeImageWidth() {
        return mazeImage.getWidth();
    }

    public int getMazeImageHeight() {
        return mazeImage.getHeight();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawImage(mazeImage, 0, 0, null);

        if (startPosition != null) {
            graphics.setColor(Color.BLUE);
            graphics.fillOval(startPosition.x, startPosition.y, 10, 10);
        }
        if (endPosition != null) {
            graphics.setColor(Color.GREEN);
            graphics.fillOval(endPosition.x, endPosition.y, 10, 10);
        }
        if (!mazePath.isEmpty()) {
            graphics.setColor(Color.RED);
            mazePath.forEach(point -> graphics.fillRect(point.x, point.y, 3, 3));
        }
    }
}
