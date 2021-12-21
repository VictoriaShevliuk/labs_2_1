package com.company;
import java.util.Scanner;
import java.lang.Math;

public class Main {

    public static void oneD() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n ► Input the number of dots, please: ");
        int dot = scan.nextInt();
        int []coordinates = new int[dot];
        int imin = 0, imax = 0, jmax = 0, jmin = 0;
        double vect, min = Double.MAX_VALUE, max = -1;

        System.out.println("\n ► Input your dots, please: ");
        for(int i=0; i< dot; i++){
            coordinates[i]= scan.nextInt();
        }

        System.out.println("\n ► Your dots: ");
        for(int i=0; i<coordinates.length;i++){
            System.out.println("(" + coordinates[i] + ")");
        }

        for( int i=0; i<dot-1; i++){
            for(int j = i+1; j < dot; j++){
                vect =  Math.abs(coordinates[j] - coordinates[i]);

                if (vect > max){
                    max = vect;
                    imax = i;
                    jmax = j;
                }

                if(vect < min){
                    min = vect;
                    imin = i;
                    jmin = j;
                }
            }
        }



        System.out.println("\n Minimal length:    " + min);
        System.out.println("\n Your dots:    ("+ (imin + 1) + ", " + (imin + 2) +")");

        System.out.println("\n Maximal length:    " + max);
        System.out.println("\n Your dots:    ("+ (imax + 1) + ", " + (imin + 2) +")");

        // одновимірний масив перетворюємо в двовимірний (і встановлюємо однаковий у)
        int[][] coordinates2D = new int[coordinates.length][2];
        for(int i=0; i<coordinates2D.length; i++){
            coordinates2D[i][0] = coordinates[i];
            coordinates2D[i][1] = 5;
        }

        new MyFrame("Min", coordinates2D, imin, jmin);
        new MyFrame("Max", coordinates2D, imax, jmax);

    }

    public static void twoD(){
        Scanner scan = new Scanner(System.in);

        System.out.println("\n ► Input the number of dots, please: ");
        int dot = scan.nextInt();
        int [][]coordinates = new int[dot][2];
        int  imin = 0, imax = 0, jmin = 0, jmax = 0;
        double vect, min = Double.MAX_VALUE, max = -1;

        System.out.println("\n ► Input dots' x and y, please: ");
        for(int i=0; i< dot; i++){
            for (int j = 0; j < 2; j++){
                coordinates[i][j] = scan.nextInt();
            }
        }

        System.out.println("\n ► Your dots: ");
        for(int i=0; i<coordinates.length;i++){
            System.out.println("(" + coordinates[i][0] + "; " + coordinates[i][1] +")");
        }


        for(int i = 0; i < dot -1; i++){
            for(int j = i+1; j < dot; j++){
                vect = Math.pow( coordinates[j][0]-coordinates[i][0], 2) + Math.pow(coordinates[j][1]-coordinates[i][1], 2);
                if(vect >= max){
                    max = vect;
                    imax = i;
                    jmax = j;
                }
                if(vect <= min){
                    min = vect;
                    imin = i;
                    jmin = j;
                }
            }
        }


        System.out.println("\n Minimal length:    " + Math.sqrt(min));
        System.out.println("\n Your dots:    ("+ (imin + 1) + ", " + (jmin + 1) +")");

        System.out.println("\n Maximal length:    " + Math.sqrt(max));
        System.out.println("\n Your dots:    ("+ (imax + 1) + ", " + (jmax + 1) +")");

        new MyFrame("Min", coordinates, imin, jmin);
        new MyFrame("Max", coordinates, imax, jmax);

    }

    public static void threeD() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n ► Input the number of dots, please: ");
        int dot = scan.nextInt();
        int[][] coordinates = new int[dot][3];
        int imin = 0, imax = 0, jmin = 0, jmax = 0;
        double vect, min = Double.MAX_VALUE, max = -1;

        System.out.println("\n ► Input dots' x, y and z, please: ");
        for (int i = 0; i < dot; i++) {

                for (int k = 0; k < 3; k++) {
                    coordinates[i][k] = scan.nextInt();
                }


        }

        System.out.println("\n ► Your dots: ");
        System.out.println(coordinates.length);
        for(int i=0; i<coordinates.length;i++){
            System.out.println("(" + coordinates[i][0] + "; " + coordinates[i][1] + "; " + coordinates[i][2]+ ")");
        }

        for( int i=0; i<dot-1; i++){
            for(int j = i+1; j < dot; j++){
                vect =  Math.pow(coordinates[j][0] - coordinates[i][0], 2) +
                        Math.pow(coordinates[j][1] - coordinates[i][1], 2) +
                        Math.pow(coordinates[j][2] - coordinates[i][2], 2);

                if (vect > max){
                    max = vect;
                    imax = i;
                    jmax = j;
                }

                if(vect < min){
                    min = vect;
                    imin = i;
                    jmin = j;
                }
            }
        }



        System.out.println("\n Minimal length:    " + Math.sqrt(min));
        System.out.printf("\n Your dots: (%d, %d, %d), (%d, %d, %d)\n",
                coordinates[imin][0],
                coordinates[imin][1],
                coordinates[imin][2],
                coordinates[jmin][0],
                coordinates[jmin][1],
                coordinates[jmin][2]);

        System.out.println("\n Maximal length:    " + Math.sqrt(max));
        System.out.printf("\n Your dots: (%d, %d, %d), (%d, %d, %d)\n",
                coordinates[imax][0],
                coordinates[imax][1],
                coordinates[imax][2],
                coordinates[jmax][0],
                coordinates[jmax][1],
                coordinates[jmax][2]);

        new MyFrame("Min", coordinates, imin, jmin);
        new MyFrame("Max", coordinates, imax, jmax);

    }


    public static void main(String[] args) {
        System.out.println("\nHow much dimensions you want to work with?\n" +
                "\n► 1D - input 1" +
                "\n► 2D - input 2" +
                "\n► 3D - input 3" +
                "\n► exit - input 4 ");
        Scanner input = new Scanner(System.in);
        int repl = input.nextInt();
        switch (repl){
            case 1:
                System.out.println("You chose 1 dimension");
                oneD();
                break;
            case 2:
                System.out.println("You chose 2 dimension");
                twoD();
                break;
            case 3:
                System.out.println("You chose 3 dimension");
                threeD();
                break;
            case 4:
                System.out.println("You exited the program!");
                break;
            default:
                System.out.println("Please, choose one of the following options!");
                break;

        }
    }
}
