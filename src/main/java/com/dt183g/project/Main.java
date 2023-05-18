package com.dt183g.project;

import com.dt183g.project.mvc.views.MazeView;
import com.dt183g.project.utility.FileReader;

import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String... args) {
        System.out.println("Maze!");

        FileReader fileReader = new FileReader();
        BufferedImage mazeImage = fileReader.readMazeImage("med7.jpg");
        SwingUtilities.invokeLater(() -> new MazeView(mazeImage));


//        int[][] test = fileReader.getMatrixFromImage(mazeImage);
        //System.out.println(test.length);

//        for (int i = 0; i < test.length; i++) {
//            for (int j = 0; j < test[i].length; j++) {
////                if (test[i][j] == 1) {
////                    System.out.printf("%d", test[i][j]);
////                } else {
////                    System.out.print(" ");
////                }
//                System.out.printf("%d", test[i][j]);
//            }
//            System.out.println();
//        }
    }
}
