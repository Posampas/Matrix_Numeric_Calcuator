package processor;

import java.awt.geom.Line2D;
import java.util.InputMismatchException;

public class Matrix {
    private double[][] matrix;
    private int rows;
    private int columns;

    public Matrix(int rows, int columns, double[][] matrix) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = matrix;

    }

    public Matrix mulitpyByNumber(double number) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] *= number;
            }
        }
        return this;
    }

    public static Matrix multipyMatrices(Matrix A, Matrix B) {
        if ((A.getColumns() == B.getRows())) {
            int resultRows = A.getRows();
            int resultColumns = B.getColumns();
            double[][] result = new double[resultRows][resultColumns];

            for (int i = 0; i < resultRows; i++) {
                for (int j = 0; j < resultColumns; j++) {
                    for (int k = 0; k < A.getColumns(); k++) {
                        result[i][j] += A.getMatrix()[i][k] * B.getMatrix()[k][j];
                    }
                }
            }
            return new Matrix(resultRows, resultColumns, result);
        } else
            return null;


    }

    public Matrix transpose(Main.TransposeType transposeType) {
        switch (transposeType) {
            case MAIN_DIAGONAL:
                mainDiagonalTranspose();
                break;
            case SIDE_DIAGONAL:
                sideDiagonalTranspose();
                break;
            case HORIZONTAL_LINE:
                horizontalLineTranspose();
                break;
            case VETICAL_LINE:
                verticalLineTranspose();
                break;
        }
        return this;
    }

    private void horizontalLineTranspose() {

        double[][] temporary = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                temporary[i][j] = matrix[rows - 1 - i][j];
            }
        }

        matrix = temporary;

    }

    private void verticalLineTranspose() {
        double[][] temporary = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                temporary[i][j] = matrix[i][columns - 1 - j];
            }
        }

        matrix = temporary;

    }

    private void mainDiagonalTranspose() {
        double[][] temporary = new double[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                temporary[j][i] = matrix[i][j];
            }
        }

        int tmp = rows;
        rows = columns;
        columns = tmp;
        matrix = temporary;
    }

    private void sideDiagonalTranspose() {
        double[][] temporary = new double[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                temporary[columns - 1 - j][rows - 1 - i] = matrix[i][j];
            }
        }
        int tmp = rows;
        rows = columns;
        columns = tmp;
        matrix = temporary;
    }

    public static Matrix add(Matrix A, Matrix B) {

        if (A.getRows() == B.getRows() && A.columns == B.columns) {
            double[][] result = new double[A.rows][A.columns];
            for (int i = 0; i < A.rows; i++) {
                for (int j = 0; j < A.columns; j++) {
                    result[i][j] = A.matrix[i][j] + B.matrix[i][j];
                }
            }
            return new Matrix(A.rows, A.getColumns(), result);
        } else
            return null;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                result += matrix[i][j];
                if (j < columns - 1) {
                    result += " ";
                }
            }
            if (i < rows - 1) {
                result += '\n';
            }

        }
        return result += '\n';
    }

    public double determinant() {
        if (rows == columns) {
            double det = 0;

            return calcuatedeterminant(rows, matrix);


        } else {
            return Double.NaN;
        }
    }

    private static double calcuatedeterminant(int rows, double[][] matrix) {
        if (rows == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        } else if (rows == 1) {
            return matrix[0][0];
        } else {
            double det = 0;
            for (int i = 0; i < rows; i++) {
                det += Math.pow(-1, i) * matrix[0][i] * calcuatedeterminant(rows - 1, subsetMatrix(0, i, matrix));
            }
            return det;
        }
    }

    private static double[][] subsetMatrix(int rowToCross, int colToCross, double[][] matrix) {
        double[][] subsetMatrix = new double[matrix.length - 1][matrix.length - 1];
        int rowCounter = 0;
        int columnCouter = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (i == rowToCross) {
                continue;
            } else {
                columnCouter = 0;
                for (int j = 0; j < matrix[i].length; j++) {
                    if (j == colToCross) {
                        continue;
                    } else {
                        subsetMatrix[rowCounter][columnCouter] = matrix[i][j];
                        columnCouter++;
                    }
                }
                rowCounter++;
            }
        }

        return subsetMatrix;
    }

    /*
     * Returns the inverse matrix of as a new matrix object
     * Matrix must be a square matrix
     */
    public Matrix inverse() {
        if (rows == columns || ! isSingular(matrix)
                || (determinant() != 0)) {

            double determinant = determinant();

            if ( determinant == 0) return null;

            Matrix inverseMatrix = new Matrix(rows,columns,new double[rows][columns]);
            for (int i = 0; i <rows ; i++) {
                for (int j = 0; j <columns ; j++) {
                    Matrix subset = new Matrix(rows-1,columns-1,subsetMatrix(i,j,matrix));
                    inverseMatrix.matrix[i][j] = Math.pow(-1,i+j) * subset.determinant();
                }
            }
            inverseMatrix.mainDiagonalTranspose();
            return inverseMatrix.mulitpyByNumber(1/determinant);


        } else
            return null;
    }

    private static boolean isSingular(double[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) {
                    if (matrix[i][j] != 1) {
                        return false;
                    }
                } else {
                    if (matrix[i][j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


}
