package processor;

import java.util.Scanner;

public class GetData {
    private static Scanner scanner = new Scanner(System.in);

    public static Matrix getMatix(){
        // get number of rows and columns
        System.out.println("Enter size of matrix");
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        double[][] matrix = new double[row][col];
        System.out.println("Enter matrix");
        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        return new Matrix(row,col,matrix);
    }
    public static double getConstant(){
        return scanner.nextDouble();
    }
    public static int getUserInput(){
        return scanner.nextInt();
    }




}
