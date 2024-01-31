package maze_solver.mvc.views.gui;

import maze_solver.mvc.models.types.MazePoint;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class MazePanel extends JPanel {
    private final BufferedImage mazeImage;

    private List<MazePoint> path;
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
                MazePoint current = this.path.get(i);

                graphics.fillRect(current.getImageX(), current.getImageY(), 1, 1);
            }
        }
        if(this.startLocation != null) {
            graphics.setColor(Color.GREEN);
            graphics.fillOval(this.startLocation.x - 6, this.startLocation.y - 6, 11, 11);
        }
        if(this.endLocation != null) {
            graphics.setColor(Color.RED);
            graphics.fillOval(this.endLocation.x - 6, this.endLocation.y - 6, 11, 11);
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

    public void setPath(List<MazePoint> path) {
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
