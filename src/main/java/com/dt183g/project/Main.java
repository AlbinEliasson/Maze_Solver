package com.dt183g.project;

import com.dt183g.project.utility.FileReader;

public class Main {

    public static void main(String... args) {
        System.out.println("Maze!");
        FileReader fileReader = new FileReader();
        int[][] test = fileReader.getMatrixFromImage("med7.jpg");
        //System.out.println(test.length);

        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[i].length; j++) {
//                if (test[i][j] == 1) {
//                    System.out.printf("%d", test[i][j]);
//                } else {
//                    System.out.print(" ");
//                }
                System.out.printf("%d", test[i][j]);
            }
            System.out.println();
        }
    }
}
