package com.dt183g.project.mvc.views.gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class MazePanel extends JPanel {
    private final BufferedImage mazeImage;

    private List<Point> path;
    private Point startLocation;
    private Point endLocation;

    public MazePanel(BufferedImage mazeImage) {
        this.mazeImage = mazeImage;

        // INITIALIZE PANEL
        this.setPreferredSize(new Dimension(this.mazeImage.getWidth(), this.mazeImage.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawImage(this.mazeImage, 0, 0, null);

        if(this.path != null && !this.path.isEmpty()){
            graphics.setColor(Color.BLUE);
            for(int i = 0; i < this.path.size() - 1; i++) {
                Point current = this.path.get(i);
                Point next = this.path.get(i + 1);
                graphics.fillRect(current.x, current.y, next.x - current.x + 1, next.y - current.y + 1);
            }
        }
        if(this.startLocation != null) {
            graphics.setColor(Color.GREEN);
            graphics.fillOval(this.startLocation.x - 5, this.startLocation.y - 5, 10, 10);
        }
        if(this.endLocation != null) {
            graphics.setColor(Color.RED);
            graphics.fillOval(this.endLocation.x - 5, this.endLocation.y - 5, 10, 10);
        }
    }

    public void refresh() {
        this.repaint();
    }

    public void setStartLocation(Point location) {
        this.startLocation = location;
        this.refresh();
    }

    public void setEndLocation(Point location) {
        this.endLocation = location;
        this.refresh();
    }

    public void setPath(List<Point> path) {
        this.path = path;
        this.refresh();
    }


    public void reset() {
        this.startLocation = null;
        this.endLocation = null;
        this.path = null;
        this.refresh();
    }
}
