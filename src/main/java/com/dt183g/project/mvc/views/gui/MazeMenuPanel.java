package com.dt183g.project.mvc.views.gui;

import com.dt183g.project.mvc.models.Model;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class MazeMenuPanel extends JPanel {
    private final JComboBox<Model.Algorithm> menuAlgorithms;

    private final JButton buttonStart;
    private final JButton buttonEnd;
    private final JButton buttonReset;
    private final JButton buttonSolve;

    public MazeMenuPanel(Model.Algorithm[] algorithms) {
        // INITIALIZE PANEL
        this.setBackground(new Color(164, 164, 164));
        this.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.WHITE));

        // INITIALIZE MENU
        this.menuAlgorithms = new JComboBox<>(algorithms);
        this.menuAlgorithms.setBorder(BorderFactory.createBevelBorder(0));
        this.menuAlgorithms.setBackground(Color.LIGHT_GRAY);
        this.menuAlgorithms.setFont(new Font("Monaco", Font.BOLD, 12));
        this.add(this.menuAlgorithms);

        // INITIALIZE SET START BUTTON
        this.buttonStart = new JButton("Set start");
        this.buttonStart.setBorder(BorderFactory.createBevelBorder(0));
        this.buttonStart.setBackground(Color.LIGHT_GRAY);
        this.buttonStart.setFont(new Font("Monaco", Font.BOLD, 12));
        this.buttonStart.setVisible(true);
        this.add(this.buttonStart);

        // INITIALIZE SET END BUTTON
        this.buttonEnd = new JButton("Set end");
        this.buttonEnd.setBorder(BorderFactory.createBevelBorder(0));
        this.buttonEnd.setBackground(Color.LIGHT_GRAY);
        this.buttonEnd.setFont(new Font("Monaco", Font.BOLD, 12));
        this.buttonEnd.setVisible(true);
        this.add(this.buttonEnd);

        // INITIALIZE RESET BUTTON
        this.buttonReset = new JButton("Reset");
        this.buttonReset.setBorder(BorderFactory.createBevelBorder(0));
        this.buttonReset.setBackground(Color.LIGHT_GRAY);
        this.buttonReset.setFont(new Font("Monaco", Font.BOLD, 12));
        this.buttonReset.setVisible(true);
        this.add(this.buttonReset);

        // INITIALIZE SOLVE BUTTON
        this.buttonSolve = new JButton("Solve");
        this.buttonSolve.setBorder(BorderFactory.createBevelBorder(0));
        this.buttonSolve.setBackground(Color.LIGHT_GRAY);
        this.buttonSolve.setFont(new Font("Monaco", Font.BOLD, 12));
        this.buttonSolve.setVisible(true);
        this.add(this.buttonSolve);
    }

    public JComboBox<Model.Algorithm> getAlgorithmMenu() {
        return this.menuAlgorithms;
    }

    public JButton getSetStartButton() {
        return this.buttonStart;
    }

    public JButton getSetEndButton() {
        return this.buttonEnd;
    }

    public JButton getResetButton() {
        return this.buttonReset;
    }

    public JButton getSolveButton() {
        return this.buttonSolve;
    }

    public Model.Algorithm getSelectedAlgorithm() {
        return (Model.Algorithm) this.menuAlgorithms.getSelectedItem();
    }
}
